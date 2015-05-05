package br.com.conjmc.jsf;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.conjmc.cadastrobasico.ControleValoresPendentes;
import br.com.conjmc.cadastrobasico.Entregas;
import br.com.conjmc.cadastrobasico.Fechamento;
import br.com.conjmc.cadastrobasico.Status;
import br.com.conjmc.cadastrobasico.Turno;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean
@SessionScoped
public class EntregasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Entregas controle;
	private List<Entregas> controles;
	private List<Status> status;
	private List<Turno> turno;
	
	@PostConstruct
    public void init() {
		controle= new Entregas();
		carregarControles();
    }
	
	private void carregarControles() {
		controles = controle.findAllEntregasByCaixa();
	}
	
	public void persist() {
        String message = "";
        if (controle.getId() != null) {
    		controle.merge();
        	message = "message_successfully_created";
        } else {
        	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();    
        	Fechamento fechamento = (Fechamento) request.getSession(true).getAttribute("fechamento");    
        	controle.setLoja(ObejctSession.loja());
        	controle.setStatus(Status.COBRAR);
    		controle.setData(new Date());
    		controle.setFechamento(fechamento);
        	if(new ControleValoresPendentes().validarValoresPendentes(controle.getData(), controle.getTurno(), controle.getNumeroPedido()) == 0) {
        		controle.persist();
        		message = "message_successfully_created";        		
        	} else {
        		message = "Não foi poissivel cadastrar o item porque ele já esta cadastrado";
        	}
        }
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Entregas");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
    }
	
	public Entregas getControle() {
		return controle;
	}

	public void setControle(Entregas controle) {
		this.controle = controle;
	}

	public List<Entregas> getControles() {
		return controles;
	}

	public void setControles(List<Entregas> controles) {
		this.controles = controles;
	}

	public List<Status> getStatus() {
		return Arrays.asList(Status.values());
	}

	public void setStatus(List<Status> status) {
		this.status = status;
	}

	public List<Turno> getTurno() {
		return Arrays.asList(Turno.values());
	}

	public void setTurno(List<Turno> turno) {
		this.turno = turno;
	}
	
}
