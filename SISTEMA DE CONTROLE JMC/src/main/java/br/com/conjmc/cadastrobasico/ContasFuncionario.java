package br.com.conjmc.cadastrobasico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.DataUltil;
import br.com.conjmc.jsf.util.ObejctSession;

@Entity
@Configurable
public class ContasFuncionario implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
     */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	/**
     */
	@NotNull
	@ManyToOne
	private Lojas loja;

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	private Date periodo;

	/**
     */
	@NotNull
	@ManyToOne
	private Funcionarios funcionario;

	/**
     */
	@ManyToOne
	private DespesasGastos item;

	/**
     */
	@NotNull
	private Boolean origem;

	/**
     */
	@NotNull
	private Integer parcela;

	/**
     */
	private String descricao;

	/**
     */
	@NotNull
	private boolean quitado;

	/**
     */
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataQuitado;

	/**
     */
	@NotNull
	private Double valor;

	// Pagamento
	@Enumerated
	private Turno turno;	
	
	private String detalhamentoBanco;
	
	private String tipoPagamento;
	
	private String banco;
	
	private String origemPagamento;
	
	//Generate Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lojas getLoja() {
		return loja;
	}

	public void setLoja(Lojas loja) {
		this.loja = loja;
	}

	public Date getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

	public Funcionarios getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}

	public DespesasGastos getItem() {
		return item;
	}

	public void setItem(DespesasGastos item) {
		this.item = item;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getParcela() {
		return parcela;
	}

	public void setParcela(Integer parcela) {
		this.parcela = parcela;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public String getDetalhamentoBanco() {
		return detalhamentoBanco;
	}

	public void setDetalhamentoBanco(String detalhamentoBanco) {
		this.detalhamentoBanco = detalhamentoBanco;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getOrigemPagamento() {
		return origemPagamento;
	}

	public void setOrigemPagamento(String origemPagamento) {
		this.origemPagamento = origemPagamento;
	}



	@PersistenceContext
	transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays
			.asList("loja", "periodo", "funcionario", "item", "descricao",
					"valor");

	public static final EntityManager entityManager() {
		EntityManager em = new ContasFuncionario().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
		MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), this.id, this
				.getClass().getSimpleName());
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			ContasFuncionario attached = ContasFuncionario
					.findContasFuncionario(this.id);
			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public ContasFuncionario merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		ContasFuncionario merged = this.entityManager.merge(this);
		MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(),
				merged.getId(), ContasFuncionario.class.getSimpleName());
		this.entityManager.flush();
		return merged;
	}

	public static ContasFuncionario findContasFuncionario(Long id) {
		if (id == null)
			return null;
		return entityManager().find(ContasFuncionario.class, id);
	}

	public static List<Funcionarios> encontraTodasFuncionarios() {
		EntityManager em = entityManager();
		Query query = entityManager()
				.createQuery(
						"SELECT o FROM Funcionarios o where o.situacao = true and o.loja.id = :loja",
						Funcionarios.class);
		query.setParameter("loja", ObejctSession.idLoja());
		return query.getResultList();
	}

	public static List<ContasFuncionario> encontraTodasContaFuncionario() {
		EntityManager em = entityManager();
		Query query = em
				.createQuery(
						"SELECT o FROM ContasFuncionario o WHERE o.loja.id = :loja and o.funcionario is not null",
						ContasFuncionario.class);
		query.setParameter("loja", ObejctSession.idLoja());
		return query.getResultList();
	}

	public static List<ContasFuncionario> encontraContaFuncionarios(
			Date dataInicial, Date dataFinal, Funcionarios funcionario) {
		Query query = entityManager()
				.createQuery(
						"SELECT o FROM ContasFuncionario o WHERE o.funcionario is not null and o.funcionario = :funcionario and o.loja.id = :loja and o.periodo between :dataInicial and :dataFinal",
						ContasFuncionario.class);
		query.setParameter("dataInicial",
				DataUltil.primeiroDiaMesTemp(dataInicial));
		query.setParameter("dataFinal", DataUltil.ultimoDiaMes(dataFinal));
		query.setParameter("funcionario", funcionario);
		query.setParameter("loja", ObejctSession.idLoja());
		return query.getResultList();
	}

	public static List<ContasFuncionario> encontraContaFuncionariosMesAnterior(
			Date dataInicial, Date dataFinal, Funcionarios funcionario) {
		Query query = entityManager()
				.createQuery(
						"SELECT o FROM ContasFuncionario o WHERE o.funcionario is not null and o.funcionario = :funcionario and o.loja.id = :loja and o.periodo between :dataInicial and :dataFinal",
						ContasFuncionario.class);
		query.setParameter("dataInicial",
				DataUltil.primeiroDiaMesAnterior(dataInicial));
		query.setParameter("dataFinal", DataUltil.ultimoDiaMesAnterior(dataInicial));
		query.setParameter("funcionario", funcionario);
		query.setParameter("loja", ObejctSession.idLoja());
		return query.getResultList();
	}	
	
	public static Double encontraFuncionariosSaldoDevedor(Date dataInicial,
			Funcionarios funcionario) {
		List<ContasFuncionario> ContaList = new ArrayList<ContasFuncionario>();
		Double resultado = 0.0;
		Query query = entityManager()
				.createQuery(
						"SELECT o FROM ContasFuncionario o WHERE o.item.codigo = 527 and o.quitado = false and o.funcionario is not null and o.funcionario = :funcionario and o.loja.id = :loja and o.periodo between :dataInicial and :dataFinal",
						ContasFuncionario.class);
		query.setParameter("dataInicial",
				DataUltil.primeiroDiaMesAnterior(dataInicial));
		query.setParameter("dataFinal", DataUltil.ultimoDiaMesAnterior(dataInicial));
		query.setParameter("funcionario", funcionario);
		query.setParameter("loja", ObejctSession.idLoja());
		ContaList = query.getResultList();
		
		for (ContasFuncionario contas : ContaList) {
			resultado = resultado + contas.getValor();
		}
		
		return resultado + funcionario.getSalario();
	}	
	
	public static Double encontraFuncionariosSaldoDevedorMes(Date dataInicial,
			Funcionarios funcionario) {
		List<ContasFuncionario> ContaList = new ArrayList<ContasFuncionario>();
		Double resultado = 0.0;
		Query query = entityManager()
				.createQuery(
						"SELECT o FROM ContasFuncionario o WHERE o.quitado = false and o.funcionario is not null and o.funcionario = :funcionario and o.loja.id = :loja and o.periodo between :dataInicial and :dataFinal",
						ContasFuncionario.class);
		query.setParameter("dataInicial",
				DataUltil.primeiroDiaMesTemp(dataInicial));
		query.setParameter("dataFinal", DataUltil.ultimoDiaMes(dataInicial));
		query.setParameter("funcionario", funcionario);
		query.setParameter("loja", ObejctSession.idLoja());
		ContaList = query.getResultList();
		
		for (ContasFuncionario contas : ContaList) {
			resultado = resultado + contas.getValor();
		}
		
		return resultado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	public List<ContasFuncionario> encontraContaFuncionariosDevedor(
			Date dataInicialTmp, Funcionarios empregado) {
		EntityManager em = entityManager();
		Query query = entityManager()
				.createQuery(
						"SELECT o FROM ContasFuncionario o WHERE o.funcionario is not null and o.origem = true and o.quitado = false and o.funcionario = :funcionario and o.loja.id = :loja",
						ContasFuncionario.class);
		query.setParameter("funcionario", empregado);
		query.setParameter("loja", ObejctSession.idLoja());
		return query.getResultList();
	}

	public List<ContasFuncionario> encontrarSalarioFuncionario(
			Date dataInicialTmp, Funcionarios empregado) {
		EntityManager em = entityManager();
		Query query = entityManager()
				.createQuery(
						"SELECT o FROM ContasFuncionario o WHERE o.funcionario is not null and o.funcionario = :funcionario and o.loja.id = :loja and o.periodo between :dataInicial and :dataFinal",
						ContasFuncionario.class);
		query.setParameter("dataInicial", DataUltil
				.primeiroDiaMesTemp(DataUltil.alterarMes(dataInicialTmp, -1)));
		query.setParameter("dataFinal", DataUltil.ultimoDiaMes(DataUltil
				.alterarMes(dataInicialTmp, -1)));
		query.setParameter("funcionario", empregado);
		query.setParameter("loja", ObejctSession.idLoja());
		return query.getResultList();
	}
	
	public Double TotalDespesa(Date data,Turno turno, String origem){
		Query query = entityManager().createQuery("SELECT SUM(o.valor) FROM ContasFuncionario o WHERE periodo=:data and origem=:origem and loja=:loja and turno="+turno.ordinal(), Double.class);
		query.setParameter("data", data);
		query.setParameter("origem", origem);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContasFuncionario other = (ContasFuncionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	public Boolean getOrigem() {
		return origem;
	}

	public void setOrigem(Boolean origem) {
		this.origem = origem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isQuitado() {
		return quitado;
	}

	public void setQuitado(boolean quitado) {
		this.quitado = quitado;
	}

	public Date getDataQuitado() {
		return dataQuitado;
	}

	public void setDataQuitado(Date dataQuitado) {
		this.dataQuitado = dataQuitado;
	}
}
