package br.com.conjmc.jsf;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.Fechamento;
import br.com.conjmc.cadastrobasico.ValoresExtra;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean
@SessionScoped
public class ValoresExtraBean implements Serializable {

	private ValoresExtra valorExtra;
	private List<ValoresExtra> valoresExtra;
	
	@PostConstruct
	public void init() {
		valorExtra = new ValoresExtra();
		carregarValores();
	}

	private void carregarValores() {
		valoresExtra = new ValoresExtra().findAllValores();
	}
	
	public String persit(){
		long fechamento = Fechamento.countFechamento(valorExtra.getData());
        if(fechamento == 0){
			if(valorExtra.getId() != null){
				valorExtra.merge();
				FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_created", "Valor Extra");
		        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
				
			} else {
				valorExtra.setLoja(ObejctSession.loja());
				valorExtra.persist();
				FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_created", "Valor Extra");
		        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			}
	        init();
        }  else {
        	String message="Esse período já foi fechado";
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, message));
        }
		return "valoreExtra.xhml";
	}
	
	public void remove(){
		long fechamento = Fechamento.countFechamento(valorExtra.getData());
        if(fechamento == 0){
			valorExtra.remove();
			FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Valor Extra");
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        init();
        }  else {
        	String message="Esse período já foi fechado";
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, message));
        }
	}
	

	// Getters and Setters
	public ValoresExtra getValorExtra() {
		return valorExtra;
	}

	public void setValorExtra(ValoresExtra valorExtra) {
		this.valorExtra = valorExtra;
	}

	public List<ValoresExtra> getValoresExtra() {
		return valoresExtra;
	}

	public void setValoresExtra(List<ValoresExtra> valoresExtra) {
		this.valoresExtra = valoresExtra;
	}
	
	
}
