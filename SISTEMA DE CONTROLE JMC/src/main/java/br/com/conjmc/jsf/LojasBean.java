package br.com.conjmc.jsf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.jsf.util.MessageFactory;

@ManagedBean
@SessionScoped
public class LojasBean {
	
	private Lojas loja; 
	private List<Lojas> lojas;
	
	@PostConstruct
	private void init() {
		loja = new Lojas();
		carregaLojas();
	}

	private void carregaLojas() {
		lojas = new Lojas().findAllLojases();
	}
	

	public String persist() {
        String message = "";
        if (loja.getId() != null) {
            loja.merge();
            message = "message_successfully_updated";
        } else {
            loja.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Lojas");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "lojas.xhtml";
    }

	public String delete() {
		try {
			loja.remove();
			FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Lojas");
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        init();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O item não pode ser deletado porque possui dependências em outros módulos", "O item não pode ser deletado porque possui dependências em outros módulos"));
		}
        return "lojas.xhtml";
    }

	public Lojas getLoja() {
		return loja;
	}

	public void setLoja(Lojas loja) {
		this.loja = loja;
	}

	public List<Lojas> getLojas() {
		return lojas;
	}

	public void setLojas(List<Lojas> lojas) {
		this.lojas = lojas;
	}
	
	public String reset() {
        loja = new Lojas();
        return "lojas.xhtml";
    }

}
