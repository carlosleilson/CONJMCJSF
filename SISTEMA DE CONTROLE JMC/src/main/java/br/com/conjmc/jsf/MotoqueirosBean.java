package br.com.conjmc.jsf;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Configurable;

import br.com.conjmc.cadastrobasico.Motoqueiros;
import br.com.conjmc.jsf.util.MessageFactory;

@ManagedBean
@SessionScoped
public class MotoqueirosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Motoqueiros motoqueiro;
	private List<Motoqueiros> motoqueiros;
	
	@PostConstruct
    public void init() {
       motoqueiro = new Motoqueiros();
       motoqueiro.setSituacao(true);
       carregarMomotoquieros();
    }
	
	private void carregarMomotoquieros() {
		motoqueiros = motoqueiro.findAllMotoqueiroses();
	}
	
	public String persist() {
        String message = "";
        if (motoqueiro.getId() != null) {
            motoqueiro.merge();
            message = "message_successfully_updated";
        } else {
            motoqueiro.persist();
            message = "message_successfully_created";
        }
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Motoqueiros");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "motoquiros.xhtml";
    }

	public String delete() {
		motoqueiro.setSituacao(false);
		motoqueiro.merge();
		FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Lojas");
	    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	    init();
        return "motoquiros.xhtml";
    }
	
	//Getters and Setters
	public Motoqueiros getMotoqueiro() {
		return motoqueiro;
	}

	public void setMotoqueiro(Motoqueiros motoqueiro) {
		this.motoqueiro = motoqueiro;
	}

	public List<Motoqueiros> getMotoqueiros() {
		return motoqueiros;
	}

	public void setMotoqueiros(List<Motoqueiros> motoqueiros) {
		this.motoqueiros = motoqueiros;
	}
	
}
