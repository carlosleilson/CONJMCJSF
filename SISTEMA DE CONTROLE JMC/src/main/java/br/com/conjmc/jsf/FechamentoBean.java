package br.com.conjmc.jsf;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.ControleValoresPendentes;
import br.com.conjmc.cadastrobasico.Fechamento;
import br.com.conjmc.cadastrobasico.ItemFaturamento;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean
@SessionScoped
public class FechamentoBean {

	private Fechamento fechamento;
	private Double totalCaixaInical;
	
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
	
	public void calcularTotalCaixaInicial() {
		this.totalCaixaInical= 0.0;
		this.totalCaixaInical += this.fechamento.getCaixaInicial();
		this.totalCaixaInical += this.fechamento.getTrocadoDinheiro();
		this.totalCaixaInical += this.fechamento.getTrocadoMoeda();
	}
	
	
	public void calcularTotal() {
		double totalDiferenca = new ItemFaturamento().valorTotal(fechamento.getData(), fechamento.getData(), fechamento.getTurno(), "");
		totalDiferenca = totalDiferenca - (totalDiferenca * 2);
		double total = 0;
		total += fechamento.getSangriaCaixa();
		total += fechamento.getSangriaGastos();
		total += fechamento.getDinheiro();
		total += fechamento.getDebito();
		total += fechamento.getCredito();
		total += fechamento.getTicket();
		total += fechamento.getCheque();
		//total += fechamento.getReceber();
		//total += fechamento.getCobrar();
		fechamento.setTotalFechamento(total);
		fechamento.setDiferenca(totalDiferenca + total);
	}
	
	public void calcularContas(){
		totalContasCobrar();
		totalContasReceber();
		totalDespespas();
		totalSangria();
		calcularTotal();
	}
	
	private void totalSangria() {
		// TODO Auto-generated method stub
		
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

	public Double getTotalCaixaInical() {
		return totalCaixaInical;
	}

	public void setTotalCaixaInical(Double totalCaixaInical) {
		this.totalCaixaInical = totalCaixaInical;
	}

	
}
