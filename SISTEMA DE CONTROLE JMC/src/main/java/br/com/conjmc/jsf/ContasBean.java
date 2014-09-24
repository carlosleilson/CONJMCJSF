package br.com.conjmc.jsf;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.Contas;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.MessageFactory;

@ManagedBean
public class ContasBean {

	private Contas conta;
	private Sangria sangria;
	
	@PostConstruct
	public void init() {
		conta = new Contas();
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

	public String persist() {
        String message = "";
        if (conta.getId() != null) {
            conta.merge();
            message = "message_successfully_updated";
        } else {
            conta.persist();
            message = "message_successfully_created";
        }
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Contas");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
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
		conta.getSangria().add(sangria);
		sangria = new Sangria();
	}
	
		
}
