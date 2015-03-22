package br.com.conjmc.cadastrobasico;

import java.util.Date;
import java.util.List;

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

import br.com.conjmc.jsf.util.ObejctSession;

@Entity
@Configurable
public class Entregas {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer numeroPedido;
	
	private String telefone;
	
	private Double valor;
	
	@Enumerated
	private Status status;
	
	@ManyToOne
	private Motoqueiros motoqueiro;
	
	@Enumerated
	private Turno turno;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@NotNull
	@ManyToOne
	private Lojas loja;
	
	@NotNull
	@ManyToOne
	private Fechamento fechamento;

	
	//Generate Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Integer numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Motoqueiros getMotoqueiro() {
		return motoqueiro;
	}

	public void setMotoqueiro(Motoqueiros motoqueiro) {
		this.motoqueiro = motoqueiro;
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

	public Lojas getLoja() {
		return loja;
	}

	public void setLoja(Lojas loja) {
		this.loja = loja;
	}
	
	public Fechamento getFechamento() {
		return fechamento;
	}

	public void setFechamento(Fechamento fechamento) {
		this.fechamento = fechamento;
	}

	// DAO
	@PersistenceContext
    transient EntityManager entityManager;

	//public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("loja", "entregas");

	public static final EntityManager entityManager() {
        EntityManager em = new Entregas().entityManager;
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
    public Entregas merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Entregas merged = this.entityManager.merge(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), merged.getId(), ControleValoresPendentes.class.getSimpleName());
        this.entityManager.flush();
        return merged;
    }
	
	public long validarValoresPendentes(Date data, Turno turno, int pedido) {
		String sql="SELECT count(o.id) FROM ControleValoresPendentes o where o.data=:data and numeroPedido=:pedido and o.loja=:loja and turno="+turno.ordinal();
		Query query = entityManager().createQuery(sql, Long.class);
		query.setParameter("data", data);
		query.setParameter("loja", ObejctSession.loja());
		query.setParameter("pedido", pedido);
       	return (long) query.getSingleResult();
	}
	
	public static List<Entregas> findAllEntregasByCaixa() {
		Query query = entityManager().createQuery("SELECT o FROM Entregas o where o.data=:data order by o.id desc", Entregas.class);
		query.setParameter("data", new Date());
		return query.getResultList();
    }
	

}
