package br.com.conjmc.cadastrobasico;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.DataUltil;
import br.com.conjmc.jsf.util.ObejctSession;

@Entity
@Configurable
public class Faturamento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Date periodo;

	private Double faturamentoBruto;
	
	@ManyToOne
    private Lojas loja;

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

	public Lojas getLoja() {
		return loja;
	}

	public void setLoja(Lojas loja) {
		this.loja = loja;
	}

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("periodo", "faturamentoBruto", "loja");

	public static final EntityManager entityManager() {
        EntityManager em = new Faturamento().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countFaturamentoses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Faturamento o", Long.class).getSingleResult();
    }

	public static List<Faturamento> findAllMFaturamentoes() {
		Query query = entityManager().createQuery("SELECT o FROM Faturamento o where o.loja.id = :loja", Faturamento.class);
		query.setParameter("loja", ObejctSession.idLoja());
        return query.getResultList();
    }

	public static List<Faturamento> faturamentoesPorDate(Date data) {
		Query query = entityManager().createQuery("FROM Faturamento o where o.loja.id = :loja and o.periodo between :dataInicial and :dataFinal ", Faturamento.class);
		query.setParameter("loja", ObejctSession.idLoja());
		query.setParameter("dataInicial", DataUltil.primeiroDiaMes(DataUltil.porMes(data)));
		query.setParameter("dataFinal", DataUltil.ultimoDiaMes(DataUltil.porMes(data)));		
        return query.getResultList();
    }	
	
	// spring roo
	/*public static List<Faturamento> findAllFaturamentoes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Faturamento o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Faturamento.class).getResultList();
    }*/

	public static Faturamento findFaturamento(Long id) {
        if (id == null) return null;
        return entityManager().find(Faturamento.class, id);
    }
	
	public static Faturamento findFaturamentoPorData(Date data) {
        if (data == null) return null;
        List results = entityManager().createQuery("SELECT o FROM Faturamento o where o.periodo between :dataInicial and :dataFinal and o.loja.id = :loja", Faturamento.class)
        .setParameter("loja", ObejctSession.idLoja())	
		.setParameter("dataInicial", DataUltil.primeiroDiaMes(DataUltil.porMes(data)))
		.setParameter("dataFinal", DataUltil.ultimoDiaMes(DataUltil.porMes(data)))
        .getResultList();
        if (results.isEmpty()) return null;
        return (Faturamento) results.get(0);
    }	
	// Spring Roo
	/*public static List<Faturamento> findFaturamentoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Faturamento o", Faturamento.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }*/

	// Spring Roo
	/*public static List<Faturamento> findFaturamentoEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Faturamento o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Faturamento.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }*/

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
