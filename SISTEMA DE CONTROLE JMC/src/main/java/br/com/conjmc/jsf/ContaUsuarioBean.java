package br.com.conjmc.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Configurable;

import br.com.conjmc.cadastrobasico.ContasFuncionario;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.cadastrobasico.LancamentosFuncionarios;
import br.com.conjmc.valueobject.FuncionarioVO;

@Configurable
@ManagedBean(name = "contaUsuarioBean")
@RequestScoped
public class ContaUsuarioBean {

	private Date dataInicial;
	private Date dataFinal;
	private Funcionarios funcionario;
	private List<FuncionarioVO> todosFuncionarios;
	private List<DespesasGastos> itens;
	private List<ContasFuncionario> contaFuncionarios;
	private ContasFuncionario contaFuncionario;
	private Integer parcelas; 
	
	@PostConstruct
    public void init() {
		buscaFuncionarios();
		findAllItensPessoalAtivos();
		todosFuncionarios();
		parcelas = 1;
	}
	
	private void todosFuncionarios() {
		List<FuncionarioVO> todosFuncionariosTmp = new ArrayList<FuncionarioVO>();
		List<Funcionarios> Funcionarios = new ContasFuncionario().encontraTodasFuncionarios();
		for (Funcionarios funcionario : Funcionarios) {
			FuncionarioVO umFuncionario = new FuncionarioVO();
			umFuncionario.setFuncionario(funcionario);
			todosFuncionariosTmp.add(umFuncionario);
		}
		todosFuncionarios = todosFuncionariosTmp; 
	}

	public void buscaFuncionarios(){
		List<FuncionarioVO> todosFuncionariosTmp = new ArrayList<FuncionarioVO>();
		List<ContasFuncionario> Funcionarios = new ContasFuncionario().encontraContaFuncionario(dataInicial, dataFinal, funcionario);
		for (ContasFuncionario funcionario : Funcionarios) {
			FuncionarioVO umFuncionario = new FuncionarioVO();
			umFuncionario.setFuncionario(funcionario.getFuncionario());
			todosFuncionariosTmp.add(umFuncionario);
		}
		todosFuncionarios = todosFuncionariosTmp; 		
	}

	private void findAllItensPessoalAtivos() {
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

	public List<FuncionarioVO> getTodosFuncionarios() {
		return todosFuncionarios;
	}

	public void setTodosFuncionarios(List<FuncionarioVO> funcionario) {
		this.todosFuncionarios = funcionario;
	}

	public ContasFuncionario getContaFuncionario() {
		return contaFuncionario;
	}

	public void setContaFuncionario(ContasFuncionario contaFuncionario) {
		this.contaFuncionario = contaFuncionario;
	}

	public Funcionarios getFuncionario() {
//        if (funcionario == null) {
//        	funcionario = new Funcionarios();
//        }
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

	public List<ContasFuncionario> getContaFuncionarios() {
		return contaFuncionarios;
	}

	public void setContaFuncionarios(List<ContasFuncionario> contaFuncionarios) {
		this.contaFuncionarios = contaFuncionarios;
	}

	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}
	
}
