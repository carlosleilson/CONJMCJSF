package br.com.conjmc.valueobject;

import java.util.List;

import br.com.conjmc.cadastrobasico.Funcionarios;

public class FuncionarioVO {
    
	/**
     */
	private Funcionarios funcionario;

    /**
     */
    private List<ItensFuncionario> item;
    
	/**
     */
	private Double Salario;
	
	/**
     */
	private Double totalDesconto;
	
	/**
     */
	private Double Crédito;
	
	/**
     */	
	private Double valorReceber;

	public Funcionarios getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}

	public List<ItensFuncionario> getItem() {
		return item;
	}

	public void setItem(List<ItensFuncionario> item) {
		this.item = item;
	}

	public Double getSalario() {
		return Salario;
	}

	public void setSalario(Double salario) {
		Salario = salario;
	}

	public Double getDespesas() {
		return totalDesconto;
	}

	public void setDespesas(Double despesas) {
		this.totalDesconto = despesas;
	}

	public Double getCrédito() {
		return Crédito;
	}

	public void setCrédito(Double crédito) {
		Crédito = crédito;
	}

	public Double getValorReceber() {
		return valorReceber;
	}

	public void setValorReceber(Double valorReceber) {
		this.valorReceber = valorReceber;
	}

	public Double getTotalDesconto() {
		return totalDesconto;
	}

	public void setTotalDesconto(Double totalDesconto) {
		this.totalDesconto = totalDesconto;
	}
}