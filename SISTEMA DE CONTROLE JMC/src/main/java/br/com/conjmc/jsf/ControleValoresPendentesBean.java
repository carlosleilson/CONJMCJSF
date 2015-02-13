package br.com.conjmc.jsf;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.ControleValoresPendentes;
import br.com.conjmc.jsf.util.MessageFactory;

public class ControleValoresPendentesBean implements Serializable {

private static final long serialVersionUID = 1L;
	
	private ControleValoresPendentes controle;
	private List<ControleValoresPendentes> controles;
	
	@PostConstruct
    public void init() {
		controle= new ControleValoresPendentes();
		controle.setAtivo(true);
		carregarControles();
    }
	
	private void carregarControles() {
		controles = controle.findAllControleValoresPendenteses();
	}
	
	public String persist() {
        String message = "";
        if (controle.getId() != null) {
        	controle.merge();
            message = "message_successfully_updated";
        } else {
        	controle.persist();
            message = "message_successfully_created";
        }
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "ControleValoresPendentes");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "controleValores.xhtml";
    }

	public String delete() {
		controle.setAtivo(false);
		controle.merge();
		FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "ControleValoresPendentes");
	    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	    init();
        return "controleValores.xhtml";
    }

	public ControleValoresPendentes getControle() {
		return controle;
	}

	public void setControle(ControleValoresPendentes controle) {
		this.controle = controle;
	}

	public List<ControleValoresPendentes> getControles() {
		return controles;
	}

	public void setControles(List<ControleValoresPendentes> controles) {
		this.controles = controles;
	}
	
}
