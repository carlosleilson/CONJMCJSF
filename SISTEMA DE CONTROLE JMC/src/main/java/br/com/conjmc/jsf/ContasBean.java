package br.com.conjmc.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
	
	@PostConstruct
	public void init() {
		conta = new Contas();
		sangria = new Sangria();
		sangrias = new ArrayList<Sangria>();
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

	public String persist() {
		if(sangrias.size() > 0) {
	        String message = "";
	        if (conta.getId() != null) {
	            conta.merge();
	            message = "message_successfully_updated";
	        } else {
	        	conta.setLoja(new Lojas().findLojas(ObejctSession.idLoja()));
	        	conta.persist();
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
	        	
	            message = "message_successfully_created";
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

	public String delete() {
		try {
			conta.remove();
			FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Contas");
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        init();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O item não pode ser deletado porque possui dependências em outros módulos", "O item não pode ser deletado porque possui dependências em outros módulos"));
		}
        return "contas.xhtml";
    }
	 
	public void reset(){
		init();
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
	
		
}
