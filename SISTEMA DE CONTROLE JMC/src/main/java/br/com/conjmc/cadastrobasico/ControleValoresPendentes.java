package br.com.conjmc.cadastrobasico;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.ObejctSession;

@Entity
@Configurable
public class ControleValoresPendentes implements Serializable {

	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer numeroPedido;
	
	private String telefone;
	
	private Double valor;
	
	private boolean baixado;
	
	private boolean ativo;
	
	@Enumerated
	private TipoPagamento tipoPagamento;
	
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

	//Getter and Setters
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
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

	public boolean isBaixado() {
		return baixado;
	}

	public void setBaixado(boolean baixado) {
		this.baixado = baixado;
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

	public static long countControleValoresPendenteses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ControleValoresPendentes o", Long.class).getSingleResult();
    }
	
	public long validarValoresPendentes(Date data, Turno turno, int pedido) {
		String sql="SELECT count(o.id) FROM ControleValoresPendentes o where o.data=:data and numeroPedido=:pedido and o.loja=:loja and turno="+turno.ordinal();
		Query query = entityManager().createQuery(sql, Long.class);
		query.setParameter("data", data);
		query.setParameter("loja", ObejctSession.loja());
		query.setParameter("pedido", pedido);
       	return (long) query.getSingleResult();
	}

	public static List<ControleValoresPendentes> findAllControleValoresPendenteses() {
		Query query = entityManager().createQuery("SELECT o FROM ControleValoresPendentes o where o.ativo=true and o.data=:data and o.baixado=false order by o.id desc", ControleValoresPendentes.class);
		query.setParameter("data", new Date());
		return query.getResultList();
    }

	public static List<ControleValoresPendentes> findAllControleValoresPendenteses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ControleValoresPendentes o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ControleValoresPendentes.class).getResultList();
    }

	public static ControleValoresPendentes findControleValoresPendentes(Long id) {
        if (id == null) return null;
        return entityManager().find(ControleValoresPendentes.class, id);
    }

	public static List<ControleValoresPendentes> findControleValoresPendentesEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ControleValoresPendentes o", ControleValoresPendentes.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<ControleValoresPendentes> findControleValoresPendentesEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ControleValoresPendentes o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ControleValoresPendentes.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), this.id, this.getClass().getSimpleName());
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ControleValoresPendentes attached = ControleValoresPendentes.findControleValoresPendentes(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public ControleValoresPendentes merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ControleValoresPendentes merged = this.entityManager.merge(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), merged.getId(), ControleValoresPendentes.class.getSimpleName());
        this.entityManager.flush();
        return merged;
    }
	
	public Double TotalContasPendentes(Date data,Turno turno, int status){
		Query query = entityManager().createQuery("SELECT SUM(o.valor) FROM ControleValoresPendentes o WHERE data=:data and status="+status+"and loja=:loja and turno="+turno.ordinal(), Double.class);
		query.setParameter("data", data);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public List<ControleValoresPendentes> ContasPendentes(Date data,Turno turno, int tipoPagamento){
		Query query = entityManager().createQuery("SELECT o FROM ControleValoresPendentes o WHERE data=:data and tipoPagamento="+tipoPagamento+"and loja=:loja and turno="+turno.ordinal(), ControleValoresPendentes.class);
		query.setParameter("data", data);
		query.setParameter("loja", ObejctSession.loja());
       	return query.getResultList();
	}
	
	public Double TotalBaixadoDinheiro(){
		Query query = entityManager().createQuery("select sum(o.valor) from ControleValoresPendentes o where o.tipoPagamento=0 and o.baixado=true and o.loja=:loja", Double.class);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public static List<ControleValoresPendentes> findByControleValores(ControleValoresPendentes controlePendentes){	
		CriteriaBuilder cb = entityManager().getCriteriaBuilder();
		CriteriaQuery<ControleValoresPendentes> c = cb.createQuery(ControleValoresPendentes.class);
		Root<ControleValoresPendentes> root = c.from(ControleValoresPendentes.class);
		c.select(root);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(controlePendentes.data != null) {
			Path<Date> data = root.get("data");
			predicates.add(cb.and(cb.equal(data, controlePendentes.data)));
		}
		
		if(controlePendentes.numeroPedido != null) {
			Path<Integer> numeroPedido = root.get("numeroPedido");
			predicates.add(cb.and(cb.equal(numeroPedido, controlePendentes.numeroPedido)));
		}
		
		if(controlePendentes.turno != null) { 
			Path<String> turno = root.get("turno");
			predicates.add(cb.and(cb.equal(turno, controlePendentes.turno)));
		}
		
		if(controlePendentes.motoqueiro != null) {
			Path<String> motoqueiro = root.get("motoqueiro");
			predicates.add(cb.and(cb.equal(motoqueiro, controlePendentes.motoqueiro)));
		}
		
		if(controlePendentes.tipoPagamento != null) {
			Path<String> tipoPagamento = root.get("tipoPagamento");
			predicates.add(cb.and(cb.equal(tipoPagamento, controlePendentes.tipoPagamento)));
		}
		 
		if(controlePendentes.status != null) {
			Path<String> status = root.get("status");
			predicates.add(cb.and(cb.equal(status, controlePendentes.status)));
		}
		
		Path<String> baixado = root.get("baixado");
		predicates.add(cb.and(cb.equal(baixado, false)));
				
		Path<String> loja = root.get("loja");
		predicates.add(cb.and(cb.equal(loja, ObejctSession.idLoja())));
			
		c.select(root).where(predicates.toArray(new Predicate[]{}));
		List<ControleValoresPendentes> listaControles = entityManager().createQuery(c).getResultList();
		return listaControles;
	}
	
}