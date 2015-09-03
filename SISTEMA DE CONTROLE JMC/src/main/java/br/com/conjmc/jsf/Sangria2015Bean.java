package br.com.conjmc.jsf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.Fechamento;
import br.com.conjmc.controlediario.controlesaida.Sangria2015;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean
@ViewScoped
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
		long fechamento = Fechamento.countFechamento(sangria.getData(), sangria.getTurno());
		String message = "";
		if(fechamento == 0){
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
		} else {
			message="Esse período já foi fechado";
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, message));
		}
        return "sangria.xhtml";
    }

	public String delete() {
		long fechamento = Fechamento.countFechamento(sangria.getData(), sangria.getTurno());
		String message = "";
		if(fechamento == 0){
			try {
				sangria.remove();
				FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Sangria");
		        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		        init();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O item não pode ser deletado porque possui dependências em outros m�dulos", "O item n�o pode ser deletado porque possui depend�ncias em outros m�dulos"));
			}
		} else {
			message="Esse período já foi fechado";
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, message));
		}
        return "sangria.xhtml";
    }
	
	public String reset(){
		init();
		return "sangria.xhtml";
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
