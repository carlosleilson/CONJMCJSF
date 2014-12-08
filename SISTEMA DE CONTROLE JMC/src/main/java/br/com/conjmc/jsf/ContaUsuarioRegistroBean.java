package br.com.conjmc.jsf;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CloseEvent;

import br.com.conjmc.cadastrobasico.ContasFuncionario;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.cadastrobasico.Perfil;
import br.com.conjmc.cadastrobasico.Usuarios;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.DataUltil;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;
import br.com.conjmc.valueobject.FuncionarioVO;
import br.com.conjmc.valueobject.ItensFuncionario;

@ManagedBean(name = "contaUsuarioRegistroBean")
@SessionScoped
public class ContaUsuarioRegistroBean implements Serializable   {
	private static final long serialVersionUID = 1L;
	private Date dataInicial;
	private Date dataFinal;
	private Sangria despesa;
	private Funcionarios funcionario;
	private FuncionarioVO funcionarioVo;
	private List<FuncionarioVO> todosFuncionarios;
	private List<ItensFuncionario> todosItensContasFuncionario;
	private List<DespesasGastos> itens;
	private List<ContasFuncionario> contaFuncionarios;
	private ContasFuncionario contaFuncionario;
	private List<Funcionarios> allFuncionariosAtivos;
	private Integer parcelas; 
	private String dataLabel;
	private Date dataTemp;
	private int mesTemp;
	private SimpleDateFormat sdf;	
	
	@PostConstruct
    public void init() {
		iniciarFuncionarioVO();
		iniciarData();
		todosFuncionarios();
		findAllItensPessoalAtivos();
		findAllFuncionariosAtivos();
	}

	private void iniciarData(){
		sdf = new SimpleDateFormat("MM/yyyy");
		Calendar c = Calendar.getInstance();
		dataTemp =c.getTime();
    	if(mesTemp==0)
    		mesTemp=dataTemp.getMonth();
    	setDataLabel(sdf.format(dataTemp).toString());		
	}	
	
	/**
	 * Método que inicia todos os funcionariosVO.
	 */		
	private void iniciarFuncionarioVO() {
		if(funcionarioVo == null){
			funcionarioVo = new FuncionarioVO();
		}
	}
	
	/**
	 * Método que procurar todos os funcionarios ativos.
	 */	
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
	
	/**
	 * Método que busca conta funcionario por funcionario.
	 */		
	private void contasFuncionario( Funcionarios funcionario ){
		contaFuncionarios = ContasFuncionario.encontraContaFuncionarios(null, null, funcionario);
	}

	/**
	 * Método que busca funcionarioVO por funcionario.
	 */
	public void buscaFuncionario( Funcionarios empregado ){
		funcionarioVo = getFuncionarioVO(empregado, dataTemp, dataTemp);
	}
	/**
	 * Método que procurar todos os funcionarios.
	 */		
	private void todosFuncionarios() {
		List<FuncionarioVO> todosFuncionariosTmp = new ArrayList<FuncionarioVO>();
		List<Funcionarios> Funcionarios = new ContasFuncionario().encontraTodasFuncionarios();
		for (Funcionarios  funcionarioTemp : Funcionarios) {
			if(tirarAdiministradores(funcionarioTemp)){
				todosFuncionariosTmp.add(getFuncionarioVO( funcionarioTemp, dataTemp, dataTemp));
			}
		}
		todosFuncionarios = todosFuncionariosTmp; 
	}

	private void todosFuncionarios(Date dataInicial, Date dataFinal) {
		List<FuncionarioVO> todosFuncionariosTmp = new ArrayList<FuncionarioVO>();
		List<Funcionarios> Funcionarios = new ContasFuncionario().encontraTodasFuncionarios();
		for (Funcionarios  funcionarioTemp : Funcionarios) {
			if(tirarAdiministradores(funcionarioTemp)){
				todosFuncionariosTmp.add(getFuncionarioVO( funcionarioTemp,dataInicial,dataFinal));
			}
		}
		todosFuncionarios = todosFuncionariosTmp; 
	}	
	
	/**
	 * Método que procurar funcionario.
	 * @param Funcionarios -- todos funcionarios. 
	 * @return FuncionarioVO Retorna object value funcionario
	 */	
	private FuncionarioVO getFuncionarioVO(Funcionarios empregado, Date dataInicial, Date dataFinal) {
		List<ItensFuncionario> todosFuncionariosTmp = new ArrayList<ItensFuncionario>();
		FuncionarioVO funcionarioVoTmp = new FuncionarioVO();
		Double totalDesconto = 0.0;
		Double credito = 0.0;
		funcionarioVoTmp.setFuncionario(empregado);
		funcionarioVoTmp.setSalario(empregado.getSalario());
		List<ContasFuncionario> listContaFuncionarios = new ContasFuncionario().encontraContaFuncionarios(dataInicial, dataFinal, empregado);
		for (ContasFuncionario funcionarioTemp : listContaFuncionarios) {
			ItensFuncionario umFuncionario = new ItensFuncionario();
			if(validarSeESalario(funcionarioTemp.getItem())){
				funcionarioVoTmp.setSalario(funcionarioTemp.getValor());
				if(funcionarioTemp.getValor()!= null){
					funcionarioVoTmp.setSalario(funcionarioTemp.getValor());
				}
			}else {
				umFuncionario.setId(funcionarioTemp);
				umFuncionario.setItem(funcionarioTemp.getItem());
				umFuncionario.setPeriodo(funcionarioTemp.getPeriodo());
				umFuncionario.setValor(funcionarioTemp.getValor());
				if(funcionarioTemp.getOrigem()){
					totalDesconto = Math.abs(totalDesconto + funcionarioTemp.getValor());
				}else{
					credito = Math.abs(credito + funcionarioTemp.getValor());
				}
				todosFuncionariosTmp.add(umFuncionario);
			}
		}
		funcionarioVoTmp.setTotalDesconto(totalDesconto);
		funcionarioVoTmp.setValorReceber((empregado.getSalario() + credito ) - totalDesconto);
		funcionarioVoTmp.setItem(todosFuncionariosTmp);
		todosItensContasFuncionario = todosFuncionariosTmp;
		return funcionarioVoTmp;
	}	

	/**
	 * Método que valida se é item do salario.
	 * @param DespesasGastos -- itens 
	 */
	private boolean validarSeESalario(DespesasGastos item) {
		return (item.getCodigo().equals(Long.parseLong("489")) 
				||item.getCodigo().equals(Long.parseLong("490"))
				||item.getCodigo().equals(Long.parseLong("166")) 
				||item.getCodigo().equals(Long.parseLong("168")));
	}

	/**
	 * Método que tira administradores do sistema da combox.
	 * @param Funcionarios -- Funicionarios da empresa
	 */
	private boolean tirarAdiministradores(Funcionarios empregado){
		for(Usuarios usuario : Usuarios.findUsuariosPorFuncionario(empregado)){
			if(usuario.getPerfil().equals(Perfil.ADMIN))
				return false;
		}
		return true;
	}

	/**
	 * Método que busca funcionario por data e funcionario.
	 */
	public void buscaFuncionarios(){
		if(dataInicial!=null || dataFinal!=null || funcionario!=null){
			List<FuncionarioVO> todosFuncionariosTmp = new ArrayList<FuncionarioVO>();
			List<ContasFuncionario> Funcionarios = new ContasFuncionario().encontraContaFuncionarios(dataInicial, dataFinal, funcionario);
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

	/**
	 * Método que busca todos itens ativos.
	 */
	private void findAllItensPessoalAtivos() {
		itens = DespesasGastos.findAllDespesasGastosAtivos();		
	}	

	/**
	 * Método que grava no banco conta funcionario e 
	 * salario com descontos em despesas.
	 */		
	void salario(String message) {
		Double valorTmp = Math.abs(contaFuncionario.getValor()/contaFuncionario.getParcela());
		for(int i = 0; i > contaFuncionario.getParcela();i++){
			parcelas(valorTmp, i);
	        if (contaFuncionario.getId() != null) {
	        	contaFuncionario.merge();
	        }else{
	        	contaFuncionario.persist();
	        }
	        despesaSalario("message_successfully_created",i);
		}
	}

	private void parcelas(Double valorTmp, int i) {
		Date dataTmp = contaFuncionario.getPeriodo();
		dataTmp.setMonth(dataTemp.getMonth()+i);
		contaFuncionario.setPeriodo(dataTmp);
		contaFuncionario.setValor(valorTmp);
	}

	private void despesaSalario(String message, int i) {
		Date dataTmp = despesa.getPeriodo();
		dataTmp.setMonth(dataTemp.getMonth()+i);
		despesa.setPeriodo(dataTmp);
		if(funcionarioVo.getValorReceber()!=null){
			despesa = salarioFuncionario();
//			if(contaFuncionario.getOrigem()){
//				despesa.setValor(funcionarioVo.getValorReceber());
//			}else{
//				despesa.setValor(funcionarioVo.getValorReceber()-contaFuncionario.getValor());
//			}
			despesa.setValor(funcionarioVo.getValorReceber());
			
			if (despesa.getId() != null) {
				despesa.merge();
			} else {
				despesa.persist();
			}
		}
	}

	private void despesaSalario() {
		if(funcionarioVo.getValorReceber()!=null){
			despesa = salarioFuncionario();
//			if(contaFuncionario.getOrigem()){
//				despesa.setValor(funcionarioVo.getValorReceber());
//			}else{
//				despesa.setValor(funcionarioVo.getValorReceber()-contaFuncionario.getValor());
//			}
			despesa.setValor(funcionarioVo.getValorReceber());
			
			if (despesa.getId() != null) {
				despesa.merge();
			} else {
				despesa.persist();
			}
		}
	}

	/**
	 * Método que prepara dados de funcionario para o banco.
	 * @return Sangria --Retorna despesa salarial. 
	 */		
	private Sangria salarioFuncionario() {
		Sangria despesaTmp = Sangria.encontarFuncionarioPorItens(contaFuncionario.getFuncionario(), contaFuncionario.getItem());
		if(despesaTmp!=null && despesaTmp.getId()!=null){
			return despesaTmp;
		}
		despesa = new Sangria();
		despesa.setFuncionario(contaFuncionario.getFuncionario());
		despesa.setClassificacao(contaFuncionario.getItem().getClassificacao());
		despesa.setItem(contaFuncionario.getItem());
		//Colocar o salario para o mes seguinte.
		Date dataTmp = contaFuncionario.getPeriodo();
		dataTmp.setMonth(dataTmp.getMonth()+1);
		despesa.setPeriodo(dataTmp);
		despesa.setLoja(new Lojas().findLojas(ObejctSession.idLoja()));		
		return despesa;
	}

	public String handleDialogClose(CloseEvent event) {
        reset();
        return "/page/contaFuncionario.xhtml";
    }

	/**
	 * Método que redireçona o link.
	 * @param FuncionarioVO -- Funcionario temporario.
	 * @return String --Retorna caminho de pagina. 
	 */		
	public String contaFuncionarioRedict(FuncionarioVO funcionarioVoTmp) {
		contaFuncionario = new ContasFuncionario();
		contaFuncionario.setPeriodo(dataTemp);
		contaFuncionario.setLoja(new Lojas().findLojas(ObejctSession.idLoja()));
		contaFuncionario.setFuncionario(funcionarioVoTmp.getFuncionario());
		contaFuncionario.setParcela(1);
		buscaFuncionario(contaFuncionario.getFuncionario());
        return "/page/contaFuncionarioRegistro.xhtml";
    }
	
	public String persist() {
		String message = "";
		salario(message);
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Conta Funcionario");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        return contaFuncionarioRedict(funcionarioVo);
    }
	
	public String delete() {
		String item = contaFuncionario.getDescricao();
		contaFuncionario.remove();
		despesaSalario();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", item+" do Conta Funcionairo");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        return contaFuncionarioRedict(funcionarioVo);
    }
	
	public void reset() {
		contaFuncionario = null;
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

	public Sangria getDespesa() {
		return despesa;
	}

	public void setDespesa(Sangria despesa) {
		this.despesa = despesa;
	}

	public String getDataLabel() {
		return dataLabel;
	}

	public void setDataLabel(String dataLabel) {
		this.dataLabel = dataLabel;
	}

	public Date getDataTemp() {
		return dataTemp;
	}

	public void setDataTemp(Date dataTemp) {
		this.dataTemp = dataTemp;
	}

	public int getMesTemp() {
		return mesTemp;
	}

	public void setMesTemp(int mesTemp) {
		this.mesTemp = mesTemp;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
}
