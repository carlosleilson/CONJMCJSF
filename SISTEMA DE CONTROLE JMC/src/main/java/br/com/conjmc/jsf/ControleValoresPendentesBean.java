package br.com.conjmc.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.ControleValoresPendentes;
import br.com.conjmc.cadastrobasico.Fechamento;
import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.cadastrobasico.Status;
import br.com.conjmc.cadastrobasico.TipoPagamento;
import br.com.conjmc.cadastrobasico.Turno;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean
@SessionScoped
public class ControleValoresPendentesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ControleValoresPendentes controle;
	private ControleValoresPendentes controleFilter = new ControleValoresPendentes();
	private List<ControleValoresPendentes> controles;
	private List<TipoPagamento> tipoPagamento;
	private List<Status> status;
	private List<Turno> turno;
	
	@PostConstruct
    public void init() {
		controle= new ControleValoresPendentes();
		controle.setAtivo(true);
		//carregarControles();
    }
	
	private void carregarControles() {
		controles = controle.findAllControleValoresPendenteses();
	}
	
	public void persist() {
        String message = "";
        if (controle.getId() != null) {
        	controle.merge();
        	message = "message_successfully_created";
        	FacesMessage facesMessage = MessageFactory.getMessage(message, "ControleValoresPendentes");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } else {
        	controle.setLoja(ObejctSession.loja());
        	/*controle.setData(new Date());*/
        	if(new ControleValoresPendentes().validarValoresPendentes(controle.getData(), controle.getTurno(), controle.getNumeroPedido()) == 0) {
        		controle.setBaixado(false);
        		controle.persist();
        		message = "message_successfully_created";
        		FacesMessage facesMessage = MessageFactory.getMessage(message, "ControleValoresPendentes");
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        	} else {
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi poissivel cadastrar o item porque ele já esta cadastrado", "Não foi poissivel cadastrar o item porque ele já esta cadastrado"));
        		message = "";
        	}
        }
        init();
        /*return "controleValores.xhtml";*/
    }

	public String delete() {
		controle.remove();
		FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "ControleValoresPendentes");
	    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	    init();
        return "controleValores.xhtml";
    }
	
	public void baixarPedido(){
		controle.setStatus(Status.BAIXADO);
		controle.merge();
		init();
	}
	
	public void filtrar() {
		controles = controle.findByControleValores(controleFilter);
	}
	
	public String reset() {
		controleFilter = new ControleValoresPendentes();
		controles = new ArrayList<ControleValoresPendentes>();
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

	public ControleValoresPendentes getControleFilter() {
		return controleFilter;
	}

	public void setControleFilter(ControleValoresPendentes controleFilter) {
		this.controleFilter = controleFilter;
	}

	public List<TipoPagamento> getTipoPagamento() {
		return Arrays.asList(TipoPagamento.values());
	}

	public void setTipoPagamento(List<TipoPagamento> tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
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
