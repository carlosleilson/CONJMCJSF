package br.com.conjmc.valueobject;

import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.cadastrobasico.Lojas;

public class FuncionarioVO {
    private Long id;
	
	/**
     */
    private Date periodo;
    
	/**
     */
	private Funcionarios funcionario;

    /**
     */
    private DespesasGastos item;
    
	/**
     */
	private Double Salario;

	/**
     */
	private Double valor;	
	
	/**
     */
	private Double despesas;
	
	/**
     */
	private Double Crédito;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

	public Funcionarios getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}

	public DespesasGastos getItem() {
		return item;
	}

	public void setItem(DespesasGastos item) {
		this.item = item;
	}

	public Double getSalario() {
		return Salario;
	}

	public void setSalario(Double salario) {
		Salario = salario;
	}

	public Double getDespesas() {
		return despesas;
	}

	public void setDespesas(Double despesas) {
		this.despesas = despesas;
	}

	public Double getCrédito() {
		return Crédito;
	}

	public void setCrédito(Double crédito) {
		Crédito = crédito;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}
