package br.com.conjmc.cadastrobasico;

import java.util.Date;
import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.ObejctSession;

@Entity
@SessionScoped
public class Fechamento {
	
	@Id @GeneratedValue
	private Long id;
	private Double caixaInicial;
	private Double trocado;
	private Double sangriaCaixa;
	private Double sangriaGastos;
	private Double dinheiro;
	private Double debito;
	private Double credito;
	private Double ticket;
	private Double cheque;
	private String justificativa;
	private boolean fechado;
	private Double receber;
	private Double cobrar;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@Enumerated
	private Turno turno;	
	
	@NotNull
	@ManyToOne
	private Lojas loja;
	
	@NotNull
	@ManyToOne
	private Usuarios usuarios;
	
	//Generate getters and setters
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Double getCaixaInicial() {
		return caixaInicial;
	}
	
	public void setCaixaInicial(Double caixaInicial) {
		this.caixaInicial = caixaInicial;
	}
	
	public Double getTrocado() {
		return trocado;
	}
	
	public void setTrocado(Double trocado) {
		this.trocado = trocado;
	}
	
	public Double getSangriaCaixa() {
		return sangriaCaixa;
	}
	
	public void setSangriaCaixa(Double sangriaCaixa) {
		this.sangriaCaixa = sangriaCaixa;
	}
	
	public Double getSangriaGastos() {
		return sangriaGastos;
	}
	
	public void setSangriaGastos(Double sangriaGastos) {
		this.sangriaGastos = sangriaGastos;
	}
	
	public Double getDinheiro() {
		return dinheiro;
	}
	
	public void setDinheiro(Double dinheiro) {
		this.dinheiro = dinheiro;
	}
	
	public Double getDebito() {
		return debito;
	}
	
	public void setDebito(Double debito) {
		this.debito = debito;
	}
	
	public Double getCredito() {
		return credito;
	}
	
	public void setCredito(Double credito) {
		this.credito = credito;
	}
	
	public Double getTicket() {
		return ticket;
	}
	
	public void setTicket(Double ticket) {
		this.ticket = ticket;
	}
	
	public Double getCheque() {
		return cheque;
	}
	
	public void setCheque(Double cheque) {
		this.cheque = cheque;
	}
	
	public String getJustificativa() {
		return justificativa;
	}
	
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	
	public Lojas getLoja() {
		return loja;
	}

	public void setLoja(Lojas loja) {
		this.loja = loja;
	}

	public Double getReceber() {
		return receber;
	}

	public void setReceber(Double receber) {
		this.receber = receber;
	}

	public Double getCobrar() {
		return cobrar;
	}

	public void setCobrar(Double cobrar) {
		this.cobrar = cobrar;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public boolean isFechado() {
		return fechado;
	}

	public void setFechado(boolean fechado) {
		this.fechado = fechado;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	//DAO
	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("nome", "fechamento");

	public static final EntityManager entityManager() {
        EntityManager em = new Cargos().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
	
	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), this.id, this.getClass().getSimpleName());
    }
	
	@Transactional
    public Fechamento merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Fechamento merged = this.entityManager.merge(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), merged.getId(), ControleValoresPendentes.class.getSimpleName());
        this.entityManager.flush();
        return merged;
    }
	
	@Transactional
	public static long qtdCaixaAberto() {
		Query query = entityManager().createQuery("SELECT count(o.id) FROM Fechamento o where o.fechado=false and o.loja=:loja", Long.class);
		query.setParameter("loja", ObejctSession.loja());
		return (long) query.getSingleResult();
	}
	
	@Transactional
	public static Fechamento caixaAberto() {
		Query query = entityManager().createQuery("SELECT o FROM Fechamento o where o.fechado=false and o.loja=:loja", Fechamento.class);
		query.setParameter("loja", ObejctSession.loja());
		return (Fechamento) query.getSingleResult();
	}
	
}
