package br.com.conjmc.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.conjmc.cadastrobasico.ContasFuncionario;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.cadastrobasico.Perfil;
import br.com.conjmc.cadastrobasico.Usuarios;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;
import br.com.conjmc.valueobject.FuncionarioVO;

@Configurable
@ManagedBean(name = "contaUsuarioBean")
@SessionScoped
public class ContaUsuarioBean implements Serializable   {
	private static final long serialVersionUID = 1L;
	private Date dataInicial;
	private Date dataFinal;
	private Funcionarios funcionario;
	private FuncionarioVO funcionarioVo;
	private List<FuncionarioVO> todosFuncionarios;
	private List<FuncionarioVO> todasContasFuncionario;
	private List<DespesasGastos> itens;
	private List<ContasFuncionario> contaFuncionarios;
	private ContasFuncionario contaFuncionario;
	private Integer parcelas; 
	
	@PostConstruct
    public void init() {
		todosFuncionarios();
		findAllItensPessoalAtivos();
		parcelas = 1;
	}

	private void contasFuncionario( Funcionarios funcionario ){
		contaFuncionarios = ContasFuncionario.encontraContaFuncionario(null, null, funcionario);
	}

	public void buscaFuncionario( Funcionarios empregado ){
		List<FuncionarioVO> todosFuncionariosTmp = new ArrayList<FuncionarioVO>();
		List<ContasFuncionario> Funcionarios = new ContasFuncionario().encontraContaFuncionario(null, null, empregado);
		for (ContasFuncionario funcionarioTemp : Funcionarios) {
			FuncionarioVO umFuncionario = new FuncionarioVO();
			if(funcionario==null)
				funcionario=funcionarioTemp.getFuncionario();
			umFuncionario.setFuncionario(funcionarioTemp.getFuncionario());
			umFuncionario.setItem(funcionarioTemp.getItem());
			umFuncionario.setPeriodo(funcionarioTemp.getPeriodo());
			umFuncionario.setSalario(funcionarioTemp.getFuncionario().getSalario());
			umFuncionario.setValor(funcionarioTemp.getValor());
			todosFuncionariosTmp.add(umFuncionario);
		}
		todasContasFuncionario = todosFuncionariosTmp; 		
	}	
	
	private void todosFuncionarios() {
		List<FuncionarioVO> todosFuncionariosTmp = new ArrayList<FuncionarioVO>();
		List<Funcionarios> Funcionarios = new ContasFuncionario().encontraTodasFuncionarios();
		for (Funcionarios funcionario : Funcionarios) {
			if(tirarAdiministradores(funcionario)){
				FuncionarioVO umFuncionario = new FuncionarioVO();
				umFuncionario.setFuncionario(funcionario);
				todosFuncionariosTmp.add(umFuncionario);
			}
		}
		todosFuncionarios = todosFuncionariosTmp; 
	}

	private boolean tirarAdiministradores(Funcionarios empregado){
		for(Usuarios usuario : Usuarios.findUsuariosPorFuncionario(empregado)){
			if(usuario.getPerfil().equals(Perfil.ADMIN))
				return false;
		}
		return true;
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

	public FuncionarioVO getFuncionarioVo() {
		return funcionarioVo;
	}

	public void setFuncionarioVo(FuncionarioVO funcionarioVo) {
		this.funcionarioVo = funcionarioVo;
	}
	
	public String persist() {
        String message = "";
        if (contaFuncionario.getId() != null) {
        	contaFuncionario.merge();
        	message = "message_successfully_created";
        }else{
        	contaFuncionario.persist();
        	message = "message_successfully_created";
        }
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Conta Funcionario");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "/page/contaFuncionarioRegistro.xhtml";
    }
	
	public void reset() {
		contaFuncionario = null;
    }

	public String handleDialogClose(CloseEvent event) {
        reset();
        return "/page/contaFuncionario.xhtml";
    }
	
	public String contaFuncionarioRedict(FuncionarioVO funcionarioVo) {
		contaFuncionario = new ContasFuncionario();
		contaFuncionario.setLoja(new Lojas().findLojas(ObejctSession.idLoja()));
		contaFuncionario.setFuncionario(funcionarioVo.getFuncionario());
		buscaFuncionario(contaFuncionario.getFuncionario());
        return "/page/contaFuncionario.xhtml";
    }

	public List<FuncionarioVO> getTodasContasFuncionario() {
		return todasContasFuncionario;
	}

	public void setTodasContasFuncionario(List<FuncionarioVO> todasContasFuncionario) {
		this.todasContasFuncionario = todasContasFuncionario;
	}
}
