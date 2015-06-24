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
	private double caixaInicial;
	private double trocadoDinheiro;
	private double trocadoMoeda;
	private double sangriaCaixa;
	private double sangriaGastos;
	private double dinheiro;
	private double trocado;
	private double moeda;
	private double porcentagem;
	private double cartao;
	private double web;
	private double cheque;
	private String justificativa;
	private double receber;
	private double cobrar;
	private double caixaFinal;
	private double totalFechamento;
	private double diferenca;
	
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
	
	public double getCaixaInicial() {
		return caixaInicial;
	}
	
	public void setCaixaInicial(double caixaInicial) {
		this.caixaInicial = caixaInicial;
	}
	
	public double getTrocadoDinheiro() {
		return trocadoDinheiro;
	}

	public void setTrocadoDinheiro(double trocadoDinheiro) {
		this.trocadoDinheiro = trocadoDinheiro;
	}

	public double getTrocadoMoeda() {
		return trocadoMoeda;
	}

	public void setTrocadoMoeda(double trocadoMoeda) {
		this.trocadoMoeda = trocadoMoeda;
	}

	public double getSangriaCaixa() {
		return sangriaCaixa;
	}
	
	public void setSangriaCaixa(double sangriaCaixa) {
		this.sangriaCaixa = sangriaCaixa;
	}
	
	public double getSangriaGastos() {
		return sangriaGastos;
	}
	
	public void setSangriaGastos(double sangriaGastos) {
		this.sangriaGastos = sangriaGastos;
	}
	
	public double getDinheiro() {
		return dinheiro;
	}
	
	public void setDinheiro(double dinheiro) {
		this.dinheiro = dinheiro;
	}
	
	public double getCheque() {
		return cheque;
	}
	
	public void setCheque(double cheque) {
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

	public double getReceber() {
		return receber;
	}

	public void setReceber(double receber) {
		this.receber = receber;
	}

	public double getCobrar() {
		return cobrar;
	}

	public void setCobrar(double cobrar) {
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

	public double getCaixaFinal() {
		return caixaFinal;
	}

	public void setCaixaFinal(double caixaFinal) {
		this.caixaFinal = caixaFinal;
	}

	public double getTotalFechamento() {
		return totalFechamento;
	}

	public void setTotalFechamento(double totalFechamento) {
		this.totalFechamento = totalFechamento;
	}

	public double getDiferenca() {
		return diferenca;
	}

	public void setDiferenca(double diferenca) {
		this.diferenca = diferenca;
	}

	public double getTrocado() {
		return trocado;
	}

	public void setTrocado(double trocado) {
		this.trocado = trocado;
	}

	public double getMoeda() {
		return moeda;
	}

	public void setMoeda(double moeda) {
		this.moeda = moeda;
	}

	public double getCartao() {
		return cartao;
	}

	public void setCartao(double cartao) {
		this.cartao = cartao;
	}

	public double getWeb() {
		return web;
	}

	public void setWeb(double web) {
		this.web = web;
	}	

	public double getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(double porcentagem) {
		this.porcentagem = porcentagem;
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
	
	public Double TotalDinheiro(){
		Query query = entityManager().createQuery("SELECT SUM(o.dinheiro) FROM Fechamento o WHERE loja=:loja", Double.class);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public Double TotalTrocado(){
		Query query = entityManager().createQuery("SELECT SUM(o.trocado) FROM Fechamento o WHERE loja=:loja", Double.class);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public Double TotalMoeda(){
		Query query = entityManager().createQuery("SELECT SUM(o.moeda) FROM Fechamento o WHERE loja=:loja", Double.class);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public Double ultimoCaixaFinal(){
		Query query = entityManager().createQuery("Select o.caixaFinal from Fechamento o where o.loja=:loja order by id desc", Double.class);
		query.setParameter("loja", ObejctSession.loja());
		query.setMaxResults(1);
		return (Double) query.getSingleResult();
	}
}
