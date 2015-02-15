package br.com.conjmc.jsf;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CloseEvent;

import br.com.conjmc.cadastrobasico.ContasFuncionario;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.cadastrobasico.Perfil;
import br.com.conjmc.cadastrobasico.Setor;
import br.com.conjmc.cadastrobasico.Usuarios;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.DataUltil;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;
import br.com.conjmc.valueobject.FuncionarioVO;
import br.com.conjmc.valueobject.ItensFuncionarioVO;

@ManagedBean(name = "contaUsuarioRegistroBean")
@ViewScoped
public class ContaUsuarioRegistroBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date dataInicial;
	private Date dataFinal;
	private NumberFormat df;
	private Sangria despesa;
	private Funcionarios funcionario;
	private FuncionarioVO funcionarioVo;
	private List<FuncionarioVO> todosFuncionarios;
	private List<ItensFuncionarioVO> todosItensContasFuncionario;
	private List<ItensFuncionarioVO> selectedItensContasFuncionario;
	private List<DespesasGastos> itens;
	private ContasFuncionario contaFuncionario;
	private List<Funcionarios> allFuncionariosAtivos;
	private Integer parcelas;
	private String dataLabelR;
	private String dataMesRecebe;
	private Date dataTemp;
	private int mesTemp;
	private SimpleDateFormat sdf;

	@PostConstruct
	public void init() {
		df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		iniciarFuncionarioVO();
		iniciarData();
		findAllItensPessoalAtivos();
		findAllFuncionariosAtivos();
	}

	private void iniciarData() {
		sdf = new SimpleDateFormat("MM/yyyy");
		Calendar c = Calendar.getInstance();
		dataTemp = c.getTime();
		if (mesTemp == 0)
			mesTemp = dataTemp.getMonth();
	}

	/**
	 * Método que pagina por mes
	 */
	public String anterior() {
		Calendar c = Calendar.getInstance();
		dataTemp.setMonth(mesTemp - 1);
		mesTemp = dataTemp.getMonth();
		c.setTime(dataTemp);
		todosFuncionarios(dataTemp, dataTemp);
		return contaFuncionarioRedict(funcionarioVo, dataTemp);
	}

	/**
	 * Metodo que marca o que foi pago.
	 */
	public String pagamento() {
		gavarPagamentos(getSelectedItensContasFuncionario());
		return contaFuncionarioRedict(funcionarioVo, dataTemp);
	}

	/**
	 * Método que efetua o pagamento.
	 * 
	 * @param List
	 *            <ItensFuncionarioVO> -- selectedItensContasFuncionarioTmp.
	 */
	private void gavarPagamentos(
			List<ItensFuncionarioVO> selectedItensContasFuncionarioTmp) {
		for (ItensFuncionarioVO itemTmp : selectedItensContasFuncionarioTmp) {
			ContasFuncionario cadastro = new ContasFuncionario();
			itemTmp.getId().setQuitado(true);
			itemTmp.getId().setDataQuitado(new Date());
			cadastro = itemTmp.getId();
			cadastro.merge();
			cadastro.flush();
		}
	}

	/**
	 * Método que pagina por mes
	 */
	public String proximo() {
		Calendar c = Calendar.getInstance();
		dataTemp.setMonth(mesTemp + 1);
		mesTemp = dataTemp.getMonth();
		c.setTime(dataTemp);
		todosFuncionarios(dataTemp, dataTemp);
		return contaFuncionarioRedict(funcionarioVo, dataTemp);
	}

	/**
	 * Método que inicia todos os funcionariosVO.
	 */
	private void iniciarFuncionarioVO() {
		if (funcionarioVo == null) {
			funcionarioVo = new FuncionarioVO();
		}
	}

	/**
	 * Método que procurar todos os funcionarios ativos.
	 */
	public String findAllFuncionariosAtivos() {
		List<Funcionarios> funcionariosAtivosTemp = new ArrayList<Funcionarios>();
		for (Funcionarios funcionarioTemp : Funcionarios
				.findAllFuncionariosAtivos()) {
			if (tirarAdiministradores(funcionarioTemp)) {
				funcionariosAtivosTemp.add(funcionarioTemp);
			}
		}
		allFuncionariosAtivos = funcionariosAtivosTemp;
		return null;
	}

	/**
	 * Método que busca funcionarioVO por funcionario.
	 */
	public void buscaFuncionario(Funcionarios empregado) {
		funcionarioVo = getFuncionarioVO(empregado, dataTemp, dataTemp);
	}

	private void todosFuncionarios(Date dataInicial, Date dataFinal) {
		List<FuncionarioVO> todosFuncionariosTmp = new ArrayList<FuncionarioVO>();
		new ContasFuncionario();
		List<Funcionarios> Funcionarios = ContasFuncionario
				.encontraTodasFuncionarios();
		for (Funcionarios funcionarioTemp : Funcionarios) {
			if (tirarAdiministradores(funcionarioTemp)) {
				todosFuncionariosTmp.add(getFuncionarioVO(funcionarioTemp,
						dataInicial, dataFinal));
			}
		}
		todosFuncionarios = todosFuncionariosTmp;
	}

	/**
	 * Método que registro de funcionario.
	 * 
	 * @param Funcionarios
	 *            -- todos funcionarios.
	 * @return FuncionarioVO Retorna object value funcionario
	 */
	private FuncionarioVO getFuncionarioVO(Funcionarios empregado,
			Date dataInicial, Date dataFinal) {
		List<ItensFuncionarioVO> todosFuncionariosTmp = new ArrayList<ItensFuncionarioVO>();
		FuncionarioVO funcionarioVoTmp = new FuncionarioVO();
		Double salario = 0.0;
		Double totalDesconto = 0.0;
		Double credito = 0.0;
		Double saldoDevedor = 0.0;
		saldoDevedor = saldoDevedorMesAnterior(dataInicial, empregado);
		salario = salario(dataInicial, empregado);
		funcionarioVoTmp.setFuncionario(empregado);
		List<ContasFuncionario> listContaFuncionarios = ContasFuncionario
				.encontraContaFuncionarios(dataInicial, dataFinal, empregado);
		for (ContasFuncionario funcionarioTemp : listContaFuncionarios) {
			ItensFuncionarioVO umFuncionario = new ItensFuncionarioVO();
			umFuncionario.setId(funcionarioTemp);
			umFuncionario.setItem(funcionarioTemp.getItem());
			umFuncionario.setPeriodo(funcionarioTemp.getPeriodo());
			umFuncionario.setValor(df.format(funcionarioTemp.getValor()));
			umFuncionario.setVencidasColor(vencidas(umFuncionario,
					funcionarioTemp.getPeriodo(), funcionarioTemp.getOrigem()));
			if (funcionarioTemp.getOrigem()) {
				if (!funcionarioTemp.isQuitado()) {
					totalDesconto = Math.abs(totalDesconto
							+ funcionarioTemp.getValor());
				}
			} else {
				credito = Math.abs(credito + funcionarioTemp.getValor());
			}
			todosFuncionariosTmp.add(umFuncionario);
		}
		funcionarioVoTmp.setSalario(df.format(salario));
		funcionarioVoTmp.setTotalDesconto(df.format(totalDesconto));
		funcionarioVoTmp.setSaldoDevedor(df.format(saldoDevedor));
		funcionarioVoTmp.setValorReceber(df.format((salario + credito)
				- Math.abs(totalDesconto + saldoDevedor)));
		funcionarioVoTmp.setItem(todosFuncionariosTmp);
		todosItensContasFuncionario = todosFuncionariosTmp;
		return funcionarioVoTmp;
	}

	private Double saldoDevedorMesAnterior(Date dataInicialTmp,
			Funcionarios empregado) {
		Double saldoDevedor = 0.0;
		List<ContasFuncionario> listContaFuncionarios2 = new ContasFuncionario()
				.encontraContaFuncionariosDevedor(dataInicialTmp, empregado);
		for (ContasFuncionario funcionarioTemp : listContaFuncionarios2) {
			if (funcionarioTemp.getOrigem() && !funcionarioTemp.isQuitado()) {
				saldoDevedor = Math.abs(saldoDevedor
						+ funcionarioTemp.getValor());
			}
		}
		return saldoDevedor;
	}
	

	private Double salario(Date dataInicialTmp, Funcionarios empregado) {
		List<ContasFuncionario> listContaFuncionarios2 = new ContasFuncionario()
				.encontrarSalarioFuncionario(dataInicialTmp, empregado);
		for (ContasFuncionario funcionarioTemp : listContaFuncionarios2) {
			if(funcionarioTemp.getDespesa()!=null)
				return funcionarioTemp.getDespesa().getValor();
		}
		return empregado.getSalario();
	}	

	/**
	 * Método que desabilita os itens dos mes que o funcionario já recebeu.
	 * 
	 * @param ItensFuncionarioVO
	 *            -- umFuncionario
	 * @param Date
	 *            -- periodo
	 * @param Boolean
	 *            -- origem
	 */
	private String vencidas(ItensFuncionarioVO umFuncionario, Date periodo,
			Boolean origem) {
		new DataUltil();
		Date dataTmp = DataUltil.quintoDiaUtil(new Date());
		if (periodo.before(dataTmp)) {
			if (origem) {
				umFuncionario.getId().setQuitado(true);
			} else {
				return "(Estou devendo)";
			}
		}
		return "";
	}

	/**
	 * Método que valida se é item do salario.
	 * 
	 * @param DespesasGastos
	 *            -- itens
	 */
	private boolean validarSeESalario(DespesasGastos item) {
		return (item.getCodigo().equals(Long.parseLong("489"))
				|| item.getCodigo().equals(Long.parseLong("490"))
				|| item.getCodigo().equals(Long.parseLong("166")) || item
				.getCodigo().equals(Long.parseLong("168")));
	}

	/**
	 * Método que tira administradores do sistema da combox.
	 * 
	 * @param Funcionarios
	 *            -- Funicionarios da empresa
	 */
	private boolean tirarAdiministradores(Funcionarios empregado) {
		for (Usuarios usuario : Usuarios.findUsuariosPorFuncionario(empregado)) {
			if (usuario.getPerfil().equals(Perfil.ADMIN))
				return false;
		}
		return true;
	}

	/**
	 * Método que busca todos itens ativos.
	 */
	private void findAllItensPessoalAtivos() {
		itens = DespesasGastos.findAllDespesasGastosAtivos();
	}

	/**
	 * Método que grava no banco conta funcionario e salario com descontos em
	 * despesas.
	 */
	void salario(String message) {
		Double valorTmp = Math.abs(contaFuncionario.getValor()
				/ contaFuncionario.getParcela());
		for (int i = 0; i < contaFuncionario.getParcela(); i++) {
			cadastrarContasFuncionario(contaFuncionario, i, valorTmp);
		}
	}

	private void cadastrarContasFuncionario(
			ContasFuncionario contaFuncionarioTmp, int i, Double valorTmp) {
		ContasFuncionario cadastro = new ContasFuncionario();
		cadastro.setDescricao(contaFuncionarioTmp.getDescricao());
		cadastro.setFuncionario(contaFuncionarioTmp.getFuncionario());
		cadastro.setItem(contaFuncionarioTmp.getItem());
		cadastro.setLoja(contaFuncionarioTmp.getLoja());
		cadastro.setOrigem(contaFuncionarioTmp.getOrigem());
		cadastro.setParcela(contaFuncionarioTmp.getParcela());
		cadastro.setPeriodo(DataUltil.alterarMes(contaFuncionario.getPeriodo(),
				i));
		cadastro.setValor(valorTmp);
		cadastro.setDespesa(despesaSalario(new Sangria(), contaFuncionarioTmp));
		try {
			if (cadastro.getId() != null) {
				cadastro.merge();
				cadastro.flush();
			} else {
				cadastro.setQuitado(false);
				cadastro.persist();
				cadastro.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Sangria despesaSalario(Sangria despesaTmp,
			ContasFuncionario contaFuncionarioTmp) {
		salarioPorPerfil(despesaTmp, contaFuncionarioTmp);
		despesaTmp.setFuncionario(contaFuncionarioTmp.getFuncionario());
		despesaTmp.setSangria(true);
		// Alterar para 5 dia util.
		despesaTmp.setPeriodo(DataUltil.quintoDiaUtil(DataUltil.alterarMes(
				contaFuncionarioTmp.getPeriodo(), 1)));
		despesaTmp.setLoja(ObejctSession.loja());
		if (funcionarioVo.getValorReceber() != null) {
			try {
				despesaTmp.setValor(df.parse(funcionarioVo.getValorReceber())
						.doubleValue());
				if (despesaTmp.getId() != null) {
					despesaTmp.merge();
					despesaTmp.flush();
				} else {
					despesaTmp.persist();
					despesaTmp.flush();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return despesaTmp;
	}

	private void salarioPorPerfil(Sangria despesa2,
			ContasFuncionario contaFuncionarioTmp) {
		if (contaFuncionarioTmp.getFuncionario().getCargo().getSetor()
				.equals(Setor.COZINHA)) {
			despesa2.setItem(DespesasGastos.findDespesasGastos(Long
					.parseLong("166")));
			despesa2.setClassificacao(DespesasGastos.findDespesasGastos(
					Long.parseLong("166")).getClassificacao());
		}

		if (contaFuncionarioTmp.getFuncionario().getCargo().getSetor()
				.equals(Setor.ATENDIMENTO)) {
			despesa2.setItem(DespesasGastos.findDespesasGastos(Long
					.parseLong("168")));
			despesa2.setClassificacao(DespesasGastos.findDespesasGastos(
					Long.parseLong("168")).getClassificacao());
		}

		if (contaFuncionarioTmp.getFuncionario().getCargo().getSetor()
				.equals(Setor.CENTRALDECORTE)) {
			despesa2.setItem(DespesasGastos.findDespesasGastos(Long
					.parseLong("489")));
			despesa2.setClassificacao(DespesasGastos.findDespesasGastos(
					Long.parseLong("489")).getClassificacao());
		}

		if (contaFuncionarioTmp.getFuncionario().getCargo().getSetor()
				.equals(Setor.CALLCENTER)) {
			despesa2.setItem(DespesasGastos.findDespesasGastos(Long
					.parseLong("490")));
			despesa2.setClassificacao(DespesasGastos.findDespesasGastos(
					Long.parseLong("490")).getClassificacao());
		}
	}

	public String handleDialogClose(CloseEvent event) {
		reset();
		return "/page/contaFuncionario.xhtml";
	}

	/**
	 * Método que redireçona o link.
	 * 
	 * @param FuncionarioVO
	 *            -- Funcionario temporario.
	 * @return String --Retorna caminho de pagina.
	 */
	public String contaFuncionarioRedict(FuncionarioVO funcionarioVoTmp,
			Date dtTmp) {
		contaFuncionario = new ContasFuncionario();
		dataTemp = dtTmp;
		contaFuncionario.setPeriodo(dataTemp);
		new Lojas();
		contaFuncionario.setLoja(Lojas.findLojas(ObejctSession.idLoja()));
		contaFuncionario.setFuncionario(funcionarioVoTmp.getFuncionario());
		contaFuncionario.setParcela(1);
		buscaFuncionario(contaFuncionario.getFuncionario());
		dataMesRecebe = getSdf().format(DataUltil.alterarMes(dtTmp, 1))
				.toString();
		dataLabelR = getSdf().format(dataTemp).toString();
		return "/page/contaFuncionarioRegistro.xhtml";
	}

	public String persist() {
		String message = "";
		salario(message);
		FacesMessage facesMessage = MessageFactory.getMessage(message,
				"Conta Funcionario");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		return contaFuncionarioRedict(funcionarioVo, dataTemp);
	}

	public String delete() {
		String item = contaFuncionario.getDescricao();
		despesaSalario(contaFuncionario.getDespesa(), contaFuncionario);
		contaFuncionario.remove();
		FacesMessage facesMessage = MessageFactory.getMessage(
				"message_successfully_deleted", item + " do Conta Funcionairo");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		return contaFuncionarioRedict(funcionarioVo, dataTemp);
	}

	public void reset() {
		contaFuncionario = null;
	}

	// Generate getters and setters
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

	public void setAllFuncionariosAtivos(
			List<Funcionarios> allFuncionariosAtivos) {
		this.allFuncionariosAtivos = allFuncionariosAtivos;
	}

	public List<ItensFuncionarioVO> getTodosItensContasFuncionario() {
		return todosItensContasFuncionario;
	}

	public void setTodosItensContasFuncionario(
			List<ItensFuncionarioVO> todosItensContasFuncionario) {
		this.todosItensContasFuncionario = todosItensContasFuncionario;
	}

	public Sangria getDespesa() {
		return despesa;
	}

	public void setDespesa(Sangria despesa) {
		this.despesa = despesa;
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

	public String getDataMesRecebe() {
		return dataMesRecebe;
	}

	public void setDataMesRecebe(String dataMesRecebe) {
		this.dataMesRecebe = dataMesRecebe;
	}

	public List<ItensFuncionarioVO> getSelectedItensContasFuncionario() {
		return selectedItensContasFuncionario;
	}

	public void setSelectedItensContasFuncionario(
			List<ItensFuncionarioVO> selectedItensContasFuncionario) {
		this.selectedItensContasFuncionario = selectedItensContasFuncionario;
	}

	public String getDataLabelR() {
		return dataLabelR;
	}

	public void setDataLabelR(String dataLabelR) {
		this.dataLabelR = dataLabelR;
	}
}
