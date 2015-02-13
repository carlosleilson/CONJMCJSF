package br.com.conjmc.cadastrobasico;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.ObejctSession;

@Entity
@Configurable
public class ControleValoresPendentes {

	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int numeroPedido;
	
	private String telefone;
	
	private Double valor;
	
	private boolean ativo;
	
	@Enumerated
	private TipoPagamento tipoPagamento;
	
	@Enumerated
	private Status status;
	
	@ManyToMany
	private Motoqueiros motoqueiro;

	//Getter and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(int numeroPedido) {
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
	
	// DAO
	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("nome", "setor");

	public static final EntityManager entityManager() {
        EntityManager em = new ControleValoresPendentes().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countControleValoresPendenteses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ControleValoresPendentes o", Long.class).getSingleResult();
    }

	public static List<ControleValoresPendentes> findAllControleValoresPendenteses() {
        return entityManager().createQuery("SELECT o FROM ControleValoresPendentes o where o.ativo=true", ControleValoresPendentes.class).getResultList();
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
	
}


