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
import br.com.conjmc.cadastrobasico.Status;
import br.com.conjmc.cadastrobasico.TipoPagamento;
import br.com.conjmc.cadastrobasico.Turno;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean
@SessionScoped
public class ControleValoresPendentesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date dataFinal;
	private ControleValoresPendentes controle;
	private ControleValoresPendentes controleFilter = new ControleValoresPendentes();
	private List<ControleValoresPendentes> controles;
	private List<TipoPagamento> tipoPagamento;
	private List<Status> status;
	private List<Turno> turno;
	private double totalDinheiro;
	private double totalTrocado;
	private double totalMoeda;
	
	@PostConstruct
    public void init() {
		controle= new ControleValoresPendentes();
		controle.setAtivo(true);
		//carregarControles();
    }
	
	private void carregarControles() {
		controles = controle.findAllControleValoresPendenteses();
	}
	
	public void carregarTotalTrocado() {
		totalTrocado = new ControleValoresPendentes().totalBaixadoTrocado(controle.getData(), dataFinal);
	}
	
	public void persist() {
        String message = "";
        long fechamento = Fechamento.countFechamento(controle.getData(), controle.getTurno());
        if(fechamento == 0){
        	if (controle.getId() != null) {
            	controle.merge();
            	message = "message_successfully_created";
            	FacesMessage facesMessage = MessageFactory.getMessage(message, "ControleValoresPendentes");
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            } else {
            	controle.setLoja(ObejctSession.loja());
            	/*controle.setData(new Date());*/
            	if(new ControleValoresPendentes().validarValoresPendentes(controle.getData(), controle.getTurno(), controle.getNumeroPedido()) == 0) {
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
        }  else {
        	message="Esse período já foi fechado";
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, message));
        }
        
        /*return "controleValores.xhtml";*/
    }

	public String delete() {
		String message = "";
        long fechamento = Fechamento.countFechamento(controle.getData(), controle.getTurno());
        if(fechamento == 0){
			controle.remove();
			FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "ControleValoresPendentes");
		    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		    init();
		}  else {
	    	message="Esse período já foi fechado";
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, message));
		}
        return "controleValores.xhtml";
    }
	
	public void baixarPedido(){
		controle.setStatus(Status.BAIXADO);
		controle.merge();
		init();
	}
	public String filtrar() {
		controles = controle.findByControleValores(controleFilter, dataFinal);
		//carregarTotalTrocado();
		return "controleValores.xhtml";
	}
	
	public String reset() {
		controleFilter = new ControleValoresPendentes();
		controles = new ArrayList<ControleValoresPendentes>();
		dataFinal = null;
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

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public double getTotalDinheiro() {
		return totalDinheiro;
	}

	public void setTotalDinheiro(double totalDinheiro) {
		this.totalDinheiro = totalDinheiro;
	}

	public double getTotalTrocado() {
		return totalTrocado;
	}

	public void setTotalTrocado(double totalTrocado) {
		this.totalTrocado = totalTrocado;
	}

	public double getTotalMoeda() {
		return totalMoeda;
	}

	public void setTotalMoeda(double totalMoeda) {
		this.totalMoeda = totalMoeda;
	}
	
}
