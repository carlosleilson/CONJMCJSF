package br.com.conjmc.jsf;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.conjmc.cadastrobasico.ControleValoresPendentes;
import br.com.conjmc.cadastrobasico.Faturamento;
import br.com.conjmc.cadastrobasico.Fechamento;
import br.com.conjmc.cadastrobasico.Turno;
import br.com.conjmc.controlediario.controlesaida.Sangria;

@ManagedBean
@ViewScoped
public class ControleDiarioBean {
	
	private Fechamento fechamento;
	private List<Fechamento> fechamentos;
	private List<Sangria> sangrias;
	private List<ControleValoresPendentes> valoresPendentes;
	
	//Filtros
	private Date dataInicial;
	private Date dataFinal;
	private Turno turno;
	
	@PostConstruct
	public void init(){
		fechamento = new Fechamento();
		//carregarFechamentos();
	}
	
	public void searchFaturamento() {
		fechamentos =  Fechamento.findByDate(dataInicial, dataFinal, turno);
	}
	
	private void carregarFechamentos() {
		fechamentos = new Fechamento().fechamentos();
	}
	
	public void carregarDespesas() {
		//sangrias = new Sangria().findSangrias(fechamento.getData(), fechamento.getTurno(), "SANGRIA CAIXA"); 
	}
	
	public void carregarControleValoresCobrar() {
		valoresPendentes = new ControleValoresPendentes().ContasPendentes(fechamento.getData(), fechamento.getTurno(), 1);
	}
	
	public void carregarControleValoresReceber() {
		valoresPendentes = new ControleValoresPendentes().ContasPendentes(fechamento.getData(), fechamento.getTurno(), 0);
	}

	public List<Fechamento> getFechamentos() {
		return fechamentos;
	}

	public void setFechamentos(List<Fechamento> fechamentos) {
		this.fechamentos = fechamentos;
	}

	public Fechamento getFechamento() {
		return fechamento;
	}

	public void setFechamento(Fechamento fechamento) {
		this.fechamento = fechamento;
	}

	public List<Sangria> getSangrias() {
		return sangrias;
	}

	public void setSangrias(List<Sangria> sangrias) {
		this.sangrias = sangrias;
	}

	public List<ControleValoresPendentes> getValoresPendentes() {
		return valoresPendentes;
	}

	public void setValoresPendentes(List<ControleValoresPendentes> valoresPendentes) {
		this.valoresPendentes = valoresPendentes;
	}
	
	public String redirectFechamento() {
		return "../pages/fechamento.xhtml";
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
}
