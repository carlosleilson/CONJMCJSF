package br.com.conjmc.jsf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.controlediario.controlesaida.Sangria2015;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean
@SessionScoped
public class Sangria2015Bean {
	
	private Sangria2015 sangria; 
	private List<Sangria2015> sangrias;
	
	@PostConstruct
	private void init() {
		sangria = new Sangria2015();
		carregarSangrias();
	}

	private void carregarSangrias() {
		sangrias = new Sangria2015().findAllSangrias();
	}
	

	public String persist() {
        String message = "";
        if (sangria.getId() != null) {
        	sangria.merge();
            message = "message_successfully_updated";
        } else {
        	sangria.setLoja(ObejctSession.loja());
        	sangria.persist();
            message = "message_successfully_created";
        }
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Sangria");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "sangria.xhtml";
    }

	public String delete() {
		try {
			sangria.remove();
			FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Sangria");
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        init();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O item não pode ser deletado porque possui dependências em outros m�dulos", "O item n�o pode ser deletado porque possui depend�ncias em outros m�dulos"));
		}
        return "lojas.xhtml";
    }

	public Sangria2015 getSangria() {
		return sangria;
	}

	public void setSangria(Sangria2015 sangria) {
		this.sangria = sangria;
	}

	public List<Sangria2015> getSangrias() {
		return sangrias;
	}

	public void setSangrias(List<Sangria2015> sangrias) {
		this.sangrias = sangrias;
	}

}
