package br.com.conjmc.jsf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.conjmc.cadastrobasico.Contas;
import br.com.conjmc.controlediario.controlesaida.Sangria;

@ManagedBean
@SessionScoped
public class ContasPendentesBean {

	private Contas conta;
	private String origem;
	private List<Contas> contas;
	
	@PostConstruct
	public void init() {
		conta = new Contas();
		carregarContas();
	}
	
	private void carregarContas() {
		contas = new Contas().findAllContas();
	}
	
	public String persist() {
		conta.merge();
		List<Sangria> sangria = Sangria.findSangriaConta(conta);
		
		
		for (Sangria sangria2 : sangria) {
			sangria2.setOrigem(origem);
			sangria2.setPeriodo(conta.getDataPagamento());
			sangria2.setSangria(true);
			sangria2.persist();
		}
		return "contasPendentes.xhtml";
	}

	/*public String persist() {
		if(sangrias.size() > 0) {
	        String message = "";
	        if (conta.getId() != null) {
	            conta.merge();
	            message = "message_successfully_updated";
	        } else {
	        	conta.persist();
	        	for (int i = 0; i < sangrias.size(); i++) {
					sangrias.get(i).setPeriodo(conta.getDataPagamento());
					sangria = sangrias.get(i);
					sangria.setLoja(new Lojas().findLojas(ObejctSession.idLoja()));
					sangria.setConta(conta);
					sangria.persist();
				}
	        	
	            message = "message_successfully_created";
	        }
	        
	        FacesMessage facesMessage = MessageFactory.getMessage(message, "Contas");
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        this.origem = null;
	        init();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O boleto teve ter pelo menos um item", "O boleto teve ter pelo menos um item"));
		}
	        return "contas.xhtml";
	        
    }*/

	//Generate getters and setters
	public Contas getConta() {
		return conta;
	}

	public void setConta(Contas conta) {
		this.conta = conta;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
	public List<Contas> getContas() {
		return contas;
	}

	public void setContas(List<Contas> contas) {
		this.contas = contas;
	}
	
}
