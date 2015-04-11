package br.com.conjmc.jsf;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.ControleValoresPendentes;
import br.com.conjmc.cadastrobasico.Fechamento;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean
@SessionScoped
public class FechamentoBean {

	private Fechamento fechamento;
	private Double total;
	
	@PostConstruct
	public void init() {
		fechamento = new Fechamento();
		calcularTotal();
	}
	
	public String persist() {
		long validar = new Fechamento().validarfechamento(fechamento.getData(), fechamento.getTurno());
		if(validar == 0) {
			fechamento.setLoja(ObejctSession.loja());
			fechamento.setUsuarios(ObejctSession.getUsuarioLogado());
			fechamento.persist();
			String message = "message_successfully_created";        
			FacesMessage facesMessage = MessageFactory.getMessage(message, "Fechamento");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			init();			
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O fechamento não pode ser incluindo porque já existe fechamento com a mesma data e período", "O fechamento não pode ser incluindo porque já existe fechamento com a mesma data e período"));
		}
        return "fechamento.xhtml";
    }
	
	public void calcularTotal() {
		this.total = 0.0;
		this.total += fechamento.getCaixaInicial();
		this.total += fechamento.getTrocado();
		this.total += fechamento.getSangriaCaixa();
		this.total += fechamento.getSangriaGastos();
		this.total += fechamento.getDinheiro();
		this.total += fechamento.getDebito();
		this.total += fechamento.getCredito();
		this.total += fechamento.getTicket();
		this.total += fechamento.getCheque();
		this.total += fechamento.getReceber();
		this.total += fechamento.getCobrar();
	}
	
	public void calcularContas(){
		totalContasCobrar();
		totalContasReceber();
		totalDespespas();
		calcularTotal();
	}
	
	private void totalContasReceber(){
		fechamento.setReceber(new ControleValoresPendentes().TotalContasPendentes(fechamento.getData(), fechamento.getTurno(), 0));
	}
	
	private void totalContasCobrar(){
		fechamento.setCobrar(new ControleValoresPendentes().TotalContasPendentes(fechamento.getData(), fechamento.getTurno(), 1));
	}
	
	private void totalDespespas() {
		fechamento.setSangriaGastos(new Sangria().TotalDespesa(fechamento.getData(), fechamento.getTurno(), "SANGRIA CAIXA"));
	}

	//Getters and Setters
	public Fechamento getFechamento() {
		return fechamento;
	}

	public void setFechamento(Fechamento fechamento) {
		this.fechamento = fechamento;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
