package br.com.conjmc.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.junit.experimental.theories.DataPoint;

import br.com.conjmc.cadastrobasico.Contas;
import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean
@SessionScoped
public class ContasBean {

	private Contas conta;
	private Sangria sangria;
	private List<Sangria> sangrias;
	private Date periodo;
	private String origem;
	private boolean pagarAgora;
	private long validation;
	
	@PostConstruct
	public void init() {
		conta = new Contas();
		sangria = new Sangria();
		sangrias = new ArrayList<Sangria>();
	}
	
	public String persist() {
		if(sangrias.size() > 0) {
	        String message = "";
	        if (conta.getId() != null) {
	            conta.merge();
	            List<Sangria> sangriasRemove = sangria.findSangriaByConta(conta.getId());
            	for (Sangria sangriaRemove : sangriasRemove) { 
            		sangriaRemove.remove();
            	}
	            message = "message_successfully_updated";
	        } else {
	        	conta.setLoja(new Lojas().findLojas(ObejctSession.idLoja()));
	        	conta.persist();        	
	            message = "message_successfully_created";
	        }
	        if(pagarAgora == false) {
	        	for (int i = 0; i < sangrias.size(); i++) {
					sangria = sangrias.get(i);
					sangria.setLoja(new Lojas().findLojas(ObejctSession.idLoja()));
					sangria.setConta(conta);
					sangria.persist();
				}
        	} else {
        		for (Sangria sangria2 : sangrias) {
        			sangria2.setOrigem(origem);
        			sangria2.setPeriodo(conta.getDataPagamento());
        			sangria2.setSangria(true);
        			sangria2.setLoja(new Lojas().findLojas(ObejctSession.idLoja()));
					sangria2.setConta(conta);
        			sangria2.persist();
        		}
        	}
	        
	        FacesMessage facesMessage = MessageFactory.getMessage(message, "Contas");
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        this.origem = null;
	        this.pagarAgora = false;
	        init();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O boleto teve ter pelo menos um item", "O boleto teve ter pelo menos um item"));
		}
	        return "contas.xhtml";
	        
    }
	 
	public String reset(){
		conta = new Contas();
		sangria = null;
		sangrias = null;
		periodo = null;
		origem = null;
		pagarAgora = false;
		return "contas.xhtml";
	}
	
	public void adicionar(){
		sangrias.add(sangria);
		sangria = new Sangria();
		calcularValor();
	}
	
	public void remove(){
		sangrias.remove(sangria);
		sangria = new Sangria();
		calcularValor();
	}
	
	private void calcularValor(){
		double total = 0;
		for (Sangria sang : sangrias) {
			total += sang.getValor();
		}
		conta.setValor(total);
	}
	
	public String editarConta(){
		if(conta.getDataPagamento() !=null) {
			this.pagarAgora = true;
		} 
		sangrias = sangria.findSangriaByConta(conta.getId());
		this.origem = sangrias.get(0) .getOrigem();
		for (int i = 0; i < sangrias.size(); i++) {
			sangrias.get(i).setId(null);
		}
		
		return "contas.xhtml";
	}
	
	public void validarConta(){
		if(conta.getId() == null){
			this.validation = conta.countContasValidation(conta);			
		}
	}
	
	public void persistCofirmation() {
		if(conta.getId() == null){
			if(validation > 0 ) {
				org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('duplicate').show();");
	    	} else {
	    		persist();        		
	    	}
		} else {
			persist();
		}
	}

	//Generate getters and setters
	public Contas getConta() {
		return conta;
	}

	public void setConta(Contas conta) {
		this.conta = conta;
	}
	
	public Sangria getSangria() {
		return sangria;
	}

	public void setSangria(Sangria sangria) {
		this.sangria = sangria;
	}

	public List<Sangria> getSangrias() {
		return sangrias;
	}

	public void setSangrias(List<Sangria> sangrias) {
		this.sangrias = sangrias;
	}
	
	public Date getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public boolean isPagarAgora() {
		return pagarAgora;
	}

	public void setPagarAgora(boolean pagarAgora) {
		this.pagarAgora = pagarAgora;
	}

	public long getValidation() {
		return validation;
	}

	public void setValidation(long validation) {
		this.validation = validation;
	}
		
}
