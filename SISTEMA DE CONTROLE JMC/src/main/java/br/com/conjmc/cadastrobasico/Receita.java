package br.com.conjmc.cadastrobasico;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.ObejctSession;

@Entity
public class Receita {
	
	@Id @GeneratedValue
	private long id;
	
	private Double valor;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	@ManyToOne
	private Usuarios usuarioLogado;
	
	@ManyToOne
	private Lojas loja;
	
	@Enumerated
	private TipoPagamento tipoPagamento;
	
	private ControleValoresPendentes controleValores;
	
	private ContasFuncionario contaFuncionarios;

	private DespesasGastos depesas;
	
	private Contas conta;

	//Generate Getters and Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Usuarios getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuarios usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Lojas getLoja() {
		return loja;
	}

	public void setLoja(Lojas loja) {
		this.loja = loja;
	}

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public ControleValoresPendentes getControleValores() {
		return controleValores;
	}

	public void setControleValores(ControleValoresPendentes controleValores) {
		this.controleValores = controleValores;
	}

	public ContasFuncionario getContaFuncionarios() {
		return contaFuncionarios;
	}

	public void setContaFuncionarios(ContasFuncionario contaFuncionarios) {
		this.contaFuncionarios = contaFuncionarios;
	}

	public DespesasGastos getDepesas() {
		return depesas;
	}

	public void setDepesas(DespesasGastos depesas) {
		this.depesas = depesas;
	}

	public Contas getConta() {
		return conta;
	}

	public void setConta(Contas conta) {
		this.conta = conta;
	}

	// DAO
	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("nome", "controleValoresPendentes");

	public static final EntityManager entityManager() {
        EntityManager em = new ControleValoresPendentes().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
	
	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), this.id, this.getClass().getSimpleName());
    }
	
}
