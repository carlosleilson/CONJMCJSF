package br.com.conjmc.jsf;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.CloseEvent;

import br.com.conjmc.cadastrobasico.ContasFuncionario;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.cadastrobasico.Perfil;
import br.com.conjmc.cadastrobasico.Usuarios;
import br.com.conjmc.jsf.util.DataUltil;
import br.com.conjmc.valueobject.FuncionarioVO;
import br.com.conjmc.valueobject.ItensFuncionarioVO;

@ManagedBean(name = "contaUsuarioBean")
@ViewScoped
public class ContaUsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date dataInicial;
	private Date dataFinal;
	private Funcionarios funcionario;
	private FuncionarioVO funcionarioVo;
	private List<FuncionarioVO> todosFuncionarios;
	private List<ItensFuncionarioVO> todosItensContasFuncionario;
	private List<DespesasGastos> itens;
	private List<ContasFuncionario> contaFuncionarios;
	private ContasFuncionario contaFuncionario;
	private List<Funcionarios> allFuncionariosAtivos;
	private String dataLabel;
	private Date dataTemp;
	private int mesTemp;
	private Integer parcelas;
	private SimpleDateFormat sdf;
	private NumberFormat df;

	@PostConstruct
	public void init() {
		df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		iniciarFuncionarioVO();
		iniciarData();
	}

	public void iniciarData() {
		setSdf(new SimpleDateFormat("MM/yyyy"));
		Calendar c = Calendar.getInstance();
		dataTemp = c.getTime();
		if (mesTemp == 0)
			mesTemp = dataTemp.getMonth();
		setDataLabel(getSdf().format(dataTemp).toString());
		todosFuncionarios(dataTemp, dataTemp);
	}

	private void iniciarFuncionarioVO() {
		if (funcionarioVo == null) {
			funcionarioVo = new FuncionarioVO();
		}
	}

	/**
	 * Método que pagina por mes
	 */
	public String anterior() {
		Calendar c = Calendar.getInstance();
		dataTemp.setMonth(mesTemp - 1);
		dataLabel = getSdf().format(dataTemp).toString();
		mesTemp = dataTemp.getMonth();
		c.setTime(dataTemp);
		todosFuncionarios(dataTemp, dataTemp);
		return "/page/contaFuncionario.xhtml";
	}

	/**
	 * Método que pagina por mes
	 */
	public String proximo() {
		Calendar c = Calendar.getInstance();
		dataTemp.setMonth(mesTemp + 1);
		dataLabel = getSdf().format(dataTemp).toString();
		mesTemp = dataTemp.getMonth();
		c.setTime(dataTemp);
		todosFuncionarios(dataTemp, dataTemp);
		return "/page/contaFuncionario.xhtml";
	}

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

	public void buscaFuncionario(Funcionarios empregado) {
		funcionarioVo = getFuncionarioVO(empregado, new Date(), new Date());
	}

	private void todosFuncionarios(Date dataInicial, Date dataFinal) {
		List<FuncionarioVO> todosFuncionariosTmp = new ArrayList<FuncionarioVO>();
		List<Funcionarios> Funcionarios = new ContasFuncionario()
				.encontraTodasFuncionarios();
		for (Funcionarios funcionarioTemp : Funcionarios) {
			if (tirarAdiministradores(funcionarioTemp)) {
				todosFuncionariosTmp.add(getFuncionarioVO(funcionarioTemp,
						dataTemp, dataTemp));
			}
		}
		todosFuncionarios = todosFuncionariosTmp;
	}

	private FuncionarioVO getFuncionarioVO(Funcionarios empregado,
			Date dataInicial, Date dataFinal) {
		List<ItensFuncionarioVO> todosFuncionariosTmp = new ArrayList<ItensFuncionarioVO>();
		FuncionarioVO funcionarioVoTmp = new FuncionarioVO();
		Double salario = 0.0;
		Double totalDesconto = 0.0;
		Double credito = 0.0;
		Double saldoDevedor = 0.0;
		Boolean quitado = false;
		salario = empregado.getSalario();
		funcionarioVoTmp.setFuncionario(empregado);
		List<ContasFuncionario> listContaFuncionarios = ContasFuncionario
				.encontraContaFuncionarios(dataInicial, dataFinal, empregado);
		for (ContasFuncionario funcionarioTemp : listContaFuncionarios) {
			if(funcionarioTemp.getItem().getSalario()){
				salario = funcionarioTemp.getValor();
			}else{	
				ItensFuncionarioVO umFuncionario = new ItensFuncionarioVO();
				umFuncionario.setId(funcionarioTemp);
				umFuncionario.setItem(funcionarioTemp.getItem());
				umFuncionario.setPeriodo(funcionarioTemp.getPeriodo());
				umFuncionario.setValor(df.format(funcionarioTemp.getValor()));
				if (funcionarioTemp.isQuitado()) {
					quitado = true;
				}
				// umFuncionario.setVencidasColor(vencidas(umFuncionario,
				// funcionarioTemp.getPeriodo(), funcionarioTemp.getOrigem()));
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
		}
		funcionarioVoTmp.setSalario(df.format(salario));
		funcionarioVoTmp.setTotalDesconto(df.format(totalDesconto));
		funcionarioVoTmp.setCredito(df.format(salario + credito));
		funcionarioVoTmp.setSaldoDevedor(df.format(saldoDevedor));
		if (quitado) {
			funcionarioVoTmp.setValorReceber(df.format(0.0));
		} else {
			funcionarioVoTmp.setValorReceber(df.format((salario + credito)
					- Math.abs(totalDesconto + saldoDevedor)));
		}
		funcionarioVoTmp.setItem(todosFuncionariosTmp);
		todosItensContasFuncionario = todosFuncionariosTmp;
		return funcionarioVoTmp;
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

	private boolean tirarAdiministradores(Funcionarios empregado) {
		for (Usuarios usuario : Usuarios.findUsuariosPorFuncionario(empregado)) {
			if (usuario.getPerfil().equals(Perfil.ADMIN))
				return false;
		}
		return true;
	}

	private void findAllItensPessoalAtivos() {
		itens = DespesasGastos.findAllDespesasGastosAtivos();
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
		// if (funcionario == null) {
		// funcionario = new Funcionarios();
		// }
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

	public void reset() {
		contaFuncionario = null;
	}

	public String handleDialogClose(CloseEvent event) {
		reset();
		return "/page/contaFuncionario.xhtml";
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

	public Date getDataTemp() {
		return dataTemp;
	}

	public void setDataTemp(Date dataTemp) {
		this.dataTemp = dataTemp;
	}

	public String getDataLabel() {
		return dataLabel;
	}

	public void setDataLabel(String dataLabel) {
		this.dataLabel = dataLabel;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
}
