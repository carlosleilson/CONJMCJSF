package br.com.conjmc.relatorios;

import java.util.Date;

import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.DespesasGastos;

public class Itens {
	 private final int QTD_CAMPOS = 33; 
	 private Despesas classificacao;
	 private DespesasGastos item;
	 private Date periodo;
	 private String valor;
	 private double[] campos;
	 
	public Itens() {
		this.campos = new double[QTD_CAMPOS];
	}
	
	public Despesas getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(Despesas classificacao) {
		this.classificacao = classificacao;
	}
	public DespesasGastos getItem() {
		return item;
	}
	public void setItem(DespesasGastos item) {
		this.item = item;
	}
	public Date getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

	public double[] getCampos() {
		return campos;
	}

	public void setCampos(double[] campos) {
		this.campos = campos;
	}
}
