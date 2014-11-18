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
import br.com.conjmc.valueobject.ItensFuncionario;

@Configurable
@ManagedBean(name = "contaUsuarioRegistroBean")
@SessionScoped
public class ContaUsuarioRegistroBean implements Serializable   {
	private static final long serialVersionUID = 1L;
	private Date dataInicial;
	private Date dataFinal;
	private Funcionarios funcionario;
	private FuncionarioVO funcionarioVo;
	private List<FuncionarioVO> todosFuncionarios;
	private List<ItensFuncionario> todosItensContasFuncionario;
	private List<DespesasGastos> itens;
	private List<ContasFuncionario> contaFuncionarios;
	private ContasFuncionario contaFuncionario;
	private List<Funcionarios> allFuncionariosAtivos;
	private Integer parcelas; 
	
	@PostConstruct
    public void init() {
		iniciarFuncionarioVO();
		todosFuncionarios();
		findAllItensPessoalAtivos();
		findAllFuncionariosAtivos();
	}
	
	private void iniciarFuncionarioVO() {
		if(funcionarioVo == null){
			funcionarioVo = new FuncionarioVO();
		}
	}

	public String findAllFuncionariosAtivos() {
		List<Funcionarios> funcionariosAtivosTemp = new ArrayList<Funcionarios>();
		for(Funcionarios funcionarioTemp :Funcionarios.findAllFuncionariosAtivos()){
			if(tirarAdiministradores(funcionarioTemp)){
				funcionariosAtivosTemp.add(funcionarioTemp);
			}
		}
		allFuncionariosAtivos = funcionariosAtivosTemp;
		return null;
    }
	
	private void contasFuncionario( Funcionarios funcionario ){
		contaFuncionarios = ContasFuncionario.encontraContaFuncionario(null, null, funcionario);
	}

	public void buscaFuncionario( Funcionarios empregado ){
		funcionarioVo = getFuncionarioVO(empregado);
	}
	
	private void todosFuncionarios() {
		List<FuncionarioVO> todosFuncionariosTmp = new ArrayList<FuncionarioVO>();
		List<Funcionarios> Funcionarios = new ContasFuncionario().encontraTodasFuncionarios();
		for (Funcionarios  funcionarioTemp : Funcionarios) {
			if(tirarAdiministradores(funcionarioTemp)){
				todosFuncionariosTmp.add(getFuncionarioVO( funcionarioTemp));
			}
		}
		todosFuncionarios = todosFuncionariosTmp; 
	}
	
	private FuncionarioVO getFuncionarioVO(Funcionarios empregado) {
		List<ItensFuncionario> todosFuncionariosTmp = new ArrayList<ItensFuncionario>();
		FuncionarioVO funcionarioVoTmp = new FuncionarioVO();
		Double totalDesconto = 0.0;
		funcionarioVoTmp.setFuncionario(empregado);
		List<ContasFuncionario> Funcionarios = new ContasFuncionario().encontraContaFuncionario(null, null, empregado);
		for (ContasFuncionario funcionarioTemp : Funcionarios) {
			ItensFuncionario umFuncionario = new ItensFuncionario();
			if(funcionarioTemp.getItem().getClassificacao().getCodigo().equals("I1") 
					||funcionarioTemp.getItem().getClassificacao().getCodigo().equals("I2")
					||funcionarioTemp.getItem().getClassificacao().getCodigo().equals("I5")){
				if(funcionarioTemp.getValor()!= null){
					funcionarioVoTmp.setSalario(funcionarioTemp.getValor());
				}else {
					funcionarioVoTmp.setSalario(funcionarioTemp.getFuncionario().getSalario());
				}
			}else {
				umFuncionario.setId(funcionarioTemp);
				umFuncionario.setItem(funcionarioTemp.getItem());
				umFuncionario.setPeriodo(funcionarioTemp.getPeriodo());
				umFuncionario.setValor(funcionarioTemp.getValor());
				funcionarioVoTmp.setSalario(funcionarioTemp.getFuncionario().getSalario());
				totalDesconto = totalDesconto + funcionarioTemp.getValor();
				todosFuncionariosTmp.add(umFuncionario);
			}
		}
		funcionarioVoTmp.setTotalDesconto(totalDesconto);
		funcionarioVoTmp.setValorReceber(empregado.getSalario() - totalDesconto);
		funcionarioVoTmp.setItem(todosFuncionariosTmp);
		todosItensContasFuncionario = todosFuncionariosTmp;
		return funcionarioVoTmp;
	}	

	private boolean tirarAdiministradores(Funcionarios empregado){
		for(Usuarios usuario : Usuarios.findUsuariosPorFuncionario(empregado)){
			if(usuario.getPerfil().equals(Perfil.ADMIN))
				return false;
		}
		return true;
	}
	
	public void buscaFuncionarios(){
		if(dataInicial!=null || dataFinal!=null || funcionario!=null){
			List<FuncionarioVO> todosFuncionariosTmp = new ArrayList<FuncionarioVO>();
			List<ContasFuncionario> Funcionarios = new ContasFuncionario().encontraContaFuncionario(dataInicial, dataFinal, funcionario);
			for (ContasFuncionario funcionario : Funcionarios) {
				FuncionarioVO umFuncionario = new FuncionarioVO();
				umFuncionario.setFuncionario(funcionario.getFuncionario());
				todosFuncionariosTmp.add(umFuncionario);
			}
			todosFuncionarios = todosFuncionariosTmp; 		
		}else{
			todosFuncionarios();
		}
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
	
	public String delete() {
		contaFuncionario.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Receber");
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
		contaFuncionario.setPeriodo(new Date());
		contaFuncionario.setLoja(new Lojas().findLojas(ObejctSession.idLoja()));
		contaFuncionario.setFuncionario(funcionarioVo.getFuncionario());
		contaFuncionario.setParcela(1);
		buscaFuncionario(contaFuncionario.getFuncionario());
        return "/page/contaFuncionarioRegistro.xhtml";
    }

	public List<Funcionarios> getAllFuncionariosAtivos() {
		return allFuncionariosAtivos;
	}

	public void setAllFuncionariosAtivos(List<Funcionarios> allFuncionariosAtivos) {
		this.allFuncionariosAtivos = allFuncionariosAtivos;
	}

	public List<ItensFuncionario> getTodosItensContasFuncionario() {
		return todosItensContasFuncionario;
	}

	public void setTodosItensContasFuncionario(
			List<ItensFuncionario> todosItensContasFuncionario) {
		this.todosItensContasFuncionario = todosItensContasFuncionario;
	}
}
