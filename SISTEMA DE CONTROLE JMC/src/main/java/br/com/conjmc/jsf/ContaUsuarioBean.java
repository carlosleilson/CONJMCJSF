package br.com.conjmc.jsf;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.controlediario.controlesaida.Sangria;

@ManagedBean
@RequestScoped
public class ContaUsuarioBean {

	private Date dataInicial;
	private Date dataFinal;
	private Funcionarios funcionario;
	private List<Sangria> contaFuncionario;
	
	private double totalDesconto;
	private double valorReceber;
	
	public void buscaContaFuncionario(){
		contaFuncionario = new Sangria().encontraContaFuncionario(dataInicial, dataFinal, funcionario);
		desconto();
	}
	
	private void desconto() {
		for (Sangria desconto: contaFuncionario) {
			totalDesconto += desconto.getValor();
		}
		valorReceber = funcionario.getSalario() - totalDesconto;
	}

	//Generate getters and setters
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

	public Funcionarios getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}

	public List<Sangria> getContaFuncionario() {
		return contaFuncionario;
	}

	public void setContaFuncionario(List<Sangria> contaFuncionario) {
		this.contaFuncionario = contaFuncionario;
	}

	public double getTotalDesconto() {
		return totalDesconto;
	}

	public void setTotalDesconto(double totalDesconto) {
		this.totalDesconto = totalDesconto;
	}

	public double getValorReceber() {
		return valorReceber;
	}

	public void setValorReceber(double valorReceber) {
		this.valorReceber = valorReceber;
	}
	
}
