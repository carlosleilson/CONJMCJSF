package br.com.conjmc.valueobject;

import java.util.Date;

import br.com.conjmc.cadastrobasico.ContasFuncionario;
import br.com.conjmc.cadastrobasico.DespesasGastos;

public class ItensFuncionario {

	/**
     */
	private ContasFuncionario id;
	
	/**
     */
    private Date periodo;
    
    /**
     */
    private DespesasGastos item;
    
	/**
     */
	private Double valor;

	public DespesasGastos getItem() {
		return item;
	}

	public void setItem(DespesasGastos item) {
		this.item = item;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

	public ContasFuncionario getId() {
		return id;
	}

	public void setId(ContasFuncionario id) {
		this.id = id;
	} 
}