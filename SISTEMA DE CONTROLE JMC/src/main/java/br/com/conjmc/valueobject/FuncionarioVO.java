package br.com.conjmc.valueobject;

import java.util.List;

import br.com.conjmc.cadastrobasico.Funcionarios;

public class FuncionarioVO {
    
	/**
     */
	private Funcionarios funcionario;

    /**
     */
    private List<ItensFuncionarioVO> item;
    
	/**
     */
	private String Salario;
	
	/**
     */
	private String totalDesconto;
	
	/**
     */
	private String saldoDevedor;
	
	/**
     */
	private String credito;
	
	/**
     */	
	private String valorReceber;

	public Funcionarios getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}

	public List<ItensFuncionarioVO> getItem() {
		return item;
	}

	public void setItem(List<ItensFuncionarioVO> item) {
		this.item = item;
	}

	public String getSalario() {
		return Salario;
	}

	public void setSalario(String salario) {
		Salario = salario;
	}

	public String getDespesas() {
		return totalDesconto;
	}

	public void setDespesas(String despesas) {
		this.totalDesconto = despesas;
	}

	
	public String getCredito() {
		return credito;
	}

	public void setCredito(String credito) {
		this.credito = credito;
	}

	public String getValorReceber() {
		return valorReceber;
	}

	public void setValorReceber(String valorReceber) {
		this.valorReceber = valorReceber;
	}

	public String getTotalDesconto() {
		return totalDesconto;
	}

	public void setTotalDesconto(String totalDesconto) {
		this.totalDesconto = totalDesconto;
	}

	public String getSaldoDevedor() {
		return saldoDevedor;
	}

	public void setSaldoDevedor(String saldoDevedor) {
		this.saldoDevedor = saldoDevedor;
	}
}