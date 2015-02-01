package br.com.conjmc.valueobject;

import java.util.Date;

import br.com.conjmc.cadastrobasico.ContasFuncionario;
import br.com.conjmc.cadastrobasico.DespesasGastos;

public class ItensFuncionarioVO {

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
	private String vencidasColor;
    
	/**
     */
	private String valor;

	public DespesasGastos getItem() {
		return item;
	}

	public void setItem(DespesasGastos item) {
		this.item = item;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
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

	public String getVencidasColor() {
		return vencidasColor;
	}

	public void setVencidasColor(String vencidasColor) {
		this.vencidasColor = vencidasColor;
	} 
}
