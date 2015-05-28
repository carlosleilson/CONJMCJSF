package br.com.conjmc.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.conjmc.cadastrobasico.ContasFuncionario;
import br.com.conjmc.cadastrobasico.ControleValoresPendentes;
import br.com.conjmc.cadastrobasico.Fechamento;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.controlediario.controlesaida.Sangria2015;

@ManagedBean
@RequestScoped
public class CofreBean {
	
	private double dinheiro;
	private double moeda;
	private double trocado;
	
	public CofreBean(){
		calcularCofre();
	}
	
	public void calcularCofre() {
		calcularDinheiro();
		calcularTrocado();
		calcularMoeda();
	}
	
	public void calcularDinheiro() {
		this.dinheiro = 0.0;
		this.dinheiro += new Fechamento().TotalDinheiro();
		this.dinheiro += new ControleValoresPendentes().TotalBaixadoDinheiro();
		this.dinheiro += new Sangria2015().TotalSangira2015Cofre("SANGRIA CAIXA", "Dinheiro");
		this.dinheiro -= new Sangria2015().TotalSangira2015Cofre("SANGRIA COFRE", "Dinheiro");
		this.dinheiro -= new Sangria().TotalSangiraCofre();
		this.dinheiro -= new ContasFuncionario().TotalDespesaCofre();
	}
	
	public void calcularTrocado() {
		this.trocado = 0.0;
		this.trocado += new Fechamento().TotalTrocado();
		this.trocado -= new Sangria2015().TotalSangira2015Cofre("SANGRIA COFRE", "Trocado");
	}
	
	public void calcularMoeda() {
		this.moeda =0.0;
		this.moeda += new Fechamento().TotalMoeda();
		this.moeda -= new Sangria2015().TotalSangira2015Cofre("SANGRIA COFRE", "Moeda");
	} 

	//Getters and Setters
	public double getDinheiro() {
		return dinheiro;
	}

	public void setDinheiro(double dinheiro) {
		this.dinheiro = dinheiro;
	}

	public double getMoeda() {
		return moeda;
	}

	public void setMoeda(double moeda) {
		this.moeda = moeda;
	}

	public double getTrocado() {
		return trocado;
	}

	public void setTrocado(double trocado) {
		this.trocado = trocado;
	}
	
}
