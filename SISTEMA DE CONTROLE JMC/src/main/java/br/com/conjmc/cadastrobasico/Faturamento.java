package br.com.conjmc.cadastrobasico;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
public class Faturamento {
	
	private Date periodo;

	private Double faturamentoBruto;
	
	private Double taxaEntrega;
	
	private Double servicoMesa;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;
	
	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Date getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

	public Double getFaturamentoBruto() {
		return faturamentoBruto;
	}

	public void setFaturamentoBruto(Double faturamentoBruto) {
		this.faturamentoBruto = faturamentoBruto;
	}

	public Double getTaxaEntrega() {
		return taxaEntrega;
	}

	public void setTaxaEntrega(Double taxaEntrega) {
		this.taxaEntrega = taxaEntrega;
	}

	public Double getServicoMesa() {
		return servicoMesa;
	}

	public void setServicoMesa(Double servicoMesa) {
		this.servicoMesa = servicoMesa;
	}



	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("apelido", "nome", "situacao");

	public static final EntityManager entityManager() {
        EntityManager em = new Faturamento().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countFaturamentoses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Faturamento o", Long.class).getSingleResult();
    }

	public static List<Faturamento> findAllMFaturamentoes() {
        return entityManager().createQuery("SELECT o FROM Faturamento o", Faturamento.class).getResultList();
    }

	public static List<Faturamento> findAllFaturamentoes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Faturamento o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Faturamento.class).getResultList();
    }

	public static Faturamento findFaturamento(Long id) {
        if (id == null) return null;
        return entityManager().find(Faturamento.class, id);
    }

	public static List<Faturamento> findFaturamentoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Faturamento o", Faturamento.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Faturamento> findFaturamentoEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Faturamento o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Faturamento.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Faturamento attached = Faturamento.findFaturamento(this.id);
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
    public Faturamento merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Faturamento merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
