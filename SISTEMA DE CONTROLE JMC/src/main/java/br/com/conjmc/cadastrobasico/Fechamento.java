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
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.ObejctSession;

@Entity
public class Fechamento {
	
	@Id @GeneratedValue
	private Long id;
	private Double caixaInicial;
	private Double trocadoDinheiro;
	private Double trocadoMoeda;
	private Double sangriaCaixa;
	private Double sangriaGastos;
	private Double dinheiro;
	private Double trocado;
	private Double moeda;
	private Double cartao;
	private Double web;
	private Double cheque;
	private String justificativa;
	private Double receber;
	private Double cobrar;
	private Double caixaFinal;
	private Double totalFechamento;
	private Double diferenca;
	
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
	
	public Double getTrocadoDinheiro() {
		return trocadoDinheiro;
	}

	public void setTrocadoDinheiro(Double trocadoDinheiro) {
		this.trocadoDinheiro = trocadoDinheiro;
	}

	public Double getTrocadoMoeda() {
		return trocadoMoeda;
	}

	public void setTrocadoMoeda(Double trocadoMoeda) {
		this.trocadoMoeda = trocadoMoeda;
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

	public Double getCaixaFinal() {
		return caixaFinal;
	}

	public void setCaixaFinal(Double caixaFinal) {
		this.caixaFinal = caixaFinal;
	}

	public Double getTotalFechamento() {
		return totalFechamento;
	}

	public void setTotalFechamento(Double totalFechamento) {
		this.totalFechamento = totalFechamento;
	}

	public Double getDiferenca() {
		return diferenca;
	}

	public void setDiferenca(Double diferenca) {
		this.diferenca = diferenca;
	}

	public Double getTrocado() {
		return trocado;
	}

	public void setTrocado(Double trocado) {
		this.trocado = trocado;
	}

	public Double getMoeda() {
		return moeda;
	}

	public void setMoeda(Double moeda) {
		this.moeda = moeda;
	}

	public Double getCartao() {
		return cartao;
	}

	public void setCartao(Double cartao) {
		this.cartao = cartao;
	}

	public Double getWeb() {
		return web;
	}

	public void setWeb(Double web) {
		this.web = web;
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
	public long validarfechamento(Date data, Turno turno) {
		String sql="SELECT count(o.id) FROM Fechamento o where o.data=:data and o.loja=:loja and turno="+turno.ordinal();
		Query query = entityManager().createQuery(sql, Long.class);
		query.setParameter("data", data);
		query.setParameter("loja", ObejctSession.loja());
       	return (long) query.getSingleResult();
	}
	
	
	@Transactional
	public List<Fechamento> fechamentos() {
		Query query = entityManager().createQuery("SELECT o FROM Fechamento o where o.loja=:loja", Fechamento.class);
		query.setParameter("loja", ObejctSession.loja());
		return query.getResultList();
	}
	
}
