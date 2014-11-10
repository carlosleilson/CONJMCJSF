package br.com.conjmc.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.MessageFactory;

@ManagedBean
@SessionScoped
public class ExclusaoDepesaBean {
	
	private Sangria sangria;

	public Sangria getSangria() {
		return sangria;
	}

	public void setSangria(Sangria sangria) {
		this.sangria = sangria;
	}
	
	public String delete() {
		sangria.remove();
		FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Sangria");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        return "ExclusaoDespesasLoja.xhtml";
	}
}
