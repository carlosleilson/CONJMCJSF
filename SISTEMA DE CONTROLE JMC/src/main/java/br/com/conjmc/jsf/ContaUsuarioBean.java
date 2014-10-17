package br.com.conjmc.jsf;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Configurable;

import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.controlediario.controlesaida.Sangria;

@Configurable
@ManagedBean(name = "contaUsuarioBean")
@RequestScoped
public class ContaUsuarioBean {

	private Date dataInicial;
	private Date dataFinal;
	private Funcionarios funcionario;
	private List<Funcionarios> funcionarios;
	private List<DespesasGastos> itens;
	private Sangria contaFuncionario;
	
	@PostConstruct
    public void init() {
		funcionarios = buscaFuncionarios();
		
	}
	
	public List<Funcionarios> buscaFuncionarios(){
		return new Funcionarios().findAllFuncionariosAtivos();
	}

	private void findAllDespesasGastosAtivos() {
		itens = DespesasGastos.findAllDespesasGastosAtivos();		
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

	public List<Funcionarios> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionarios> funcionario) {
		this.funcionarios = funcionario;
	}

	public Sangria getContaFuncionario() {
		return contaFuncionario;
	}

	public void setContaFuncionario(Sangria contaFuncionario) {
		this.contaFuncionario = contaFuncionario;
	}

	public Funcionarios getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}

	public List<DespesasGastos> getItens() {
		return itens;
	}

	public void setItens(List<DespesasGastos> itens) {
		this.itens = itens;
	}
	
}
