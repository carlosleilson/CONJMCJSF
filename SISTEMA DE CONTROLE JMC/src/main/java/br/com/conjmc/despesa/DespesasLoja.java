package br.com.conjmc.despesa;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.conjmc.cadastrobasico.Despesas;

import javax.persistence.ManyToOne;

import br.com.conjmc.cadastrobasico.DespesasGastos;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findDespesasLojasByMes_anoEquals", "findDespesasLojasByItem" })
public class DespesasLoja {

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date mes_ano;


    /**
     */
    @NotNull
    private Double valor;

    /**
     */
    @ManyToOne
    private Despesas classificacao;

    /**
     */
    @ManyToOne
    private DespesasGastos item;

	public Date getMes_ano() {
        return this.mes_ano;
    }

	public void setMes_ano(Date mes_ano) {
        this.mes_ano = mes_ano;
    }

	public Double getValor() {
        return this.valor;
    }

	public void setValor(Double valor) {
		if(valor==0)
			this.valor=0.0;
		else
			this.valor = valor;
    }

	public Despesas getClassificacao() {
        return this.classificacao;
    }

	public void setClassificacao(Despesas classificacao) {
        this.classificacao = classificacao;
    }

	public DespesasGastos getItem() {
        return this.item;
    }

	public void setItem(DespesasGastos item) {
        this.item = item;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("mes_ano", "valor", "classificacao", "item");

	public static final EntityManager entityManager() {
        EntityManager em = new DespesasLoja().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countDespesasLojas() {
        return entityManager().createQuery("SELECT COUNT(o) FROM DespesasLoja o", Long.class).getSingleResult();
    }

	public static List<DespesasLoja> findAllDespesasLojas() {
        return entityManager().createQuery("SELECT o FROM DespesasLoja o", DespesasLoja.class).getResultList();
    }

	public static List<DespesasLoja> findAllDespesasLojas(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM DespesasLoja o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, DespesasLoja.class).getResultList();
    }

	public static DespesasLoja findDespesasLoja(Long id) {
        if (id == null) return null;
        return entityManager().find(DespesasLoja.class, id);
    }

	public static List<DespesasLoja> findDespesasLojaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM DespesasLoja o", DespesasLoja.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<DespesasLoja> findDespesasLojaEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM DespesasLoja o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, DespesasLoja.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            DespesasLoja attached = DespesasLoja.findDespesasLoja(this.id);
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
    public DespesasLoja merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        DespesasLoja merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public static Long countFindDespesasLojasByItem(DespesasGastos item) {
        if (item == null) throw new IllegalArgumentException("The item argument is required");
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM DespesasLoja AS o WHERE o.item = :item", Long.class);
        q.setParameter("item", item);
        return ((Long) q.getSingleResult());
    }

	public static Long countFindDespesasLojasByMes_anoEquals(Calendar mes_ano) {
        if (mes_ano == null) throw new IllegalArgumentException("The mes_ano argument is required");
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM DespesasLoja AS o WHERE o.mes_ano = :mes_ano", Long.class);
        q.setParameter("mes_ano", mes_ano);
        return ((Long) q.getSingleResult());
    }

	public static TypedQuery<DespesasLoja> findDespesasLojasByItem(DespesasGastos item) {
        if (item == null) throw new IllegalArgumentException("The item argument is required");
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery<DespesasLoja> q = em.createQuery("SELECT o FROM DespesasLoja AS o WHERE o.item = :item", DespesasLoja.class);
        q.setParameter("item", item);
        return q;
    }

	public static TypedQuery<DespesasLoja> findDespesasLojasByItem(DespesasGastos item, String sortFieldName, String sortOrder) {
        if (item == null) throw new IllegalArgumentException("The item argument is required");
        EntityManager em = DespesasLoja.entityManager();
        String jpaQuery = "SELECT o FROM DespesasLoja AS o WHERE o.item = :item";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        TypedQuery<DespesasLoja> q = em.createQuery(jpaQuery, DespesasLoja.class);
        q.setParameter("item", item);
        return q;
    }

	public static TypedQuery<DespesasLoja> findDespesasLojasByMes_anoEquals(Calendar mes_ano) {
        if (mes_ano == null) throw new IllegalArgumentException("The mes_ano argument is required");
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery<DespesasLoja> q = em.createQuery("SELECT o FROM DespesasLoja AS o WHERE o.mes_ano = :mes_ano", DespesasLoja.class);
        q.setParameter("mes_ano", mes_ano);
        return q;
    }

	public static TypedQuery<DespesasLoja> findDespesasLojasByMes_anoEquals(Calendar mes_ano, String sortFieldName, String sortOrder) {
        if (mes_ano == null) throw new IllegalArgumentException("The mes_ano argument is required");
        EntityManager em = DespesasLoja.entityManager();
        String jpaQuery = "SELECT o FROM DespesasLoja AS o WHERE o.mes_ano = :mes_ano";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        TypedQuery<DespesasLoja> q = em.createQuery(jpaQuery, DespesasLoja.class);
        q.setParameter("mes_ano", mes_ano);
        return q;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DespesasLoja other = (DespesasLoja) obj;
		if (classificacao == null) {
			if (other.classificacao != null)
				return false;
		} else if (!classificacao.equals(other.classificacao))
			return false;
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
		if (mes_ano == null) {
			if (other.mes_ano != null)
				return false;
		} else if (!mes_ano.equals(other.mes_ano))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	
	
}
