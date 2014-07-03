package br.com.conjmc.despesa;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import java.util.Calendar;
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
import br.com.conjmc.cadastrobasico.Despesa_Gastos;

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
    @DateTimeFormat(pattern = "MM/yyyy")
    private Calendar mes_ano;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd")
    private Calendar dia;

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
    private Despesa_Gastos item;

	public Calendar getMes_ano() {
        return this.mes_ano;
    }

	public void setMes_ano(Calendar mes_ano) {
        this.mes_ano = mes_ano;
    }

	public Calendar getDia() {
        return this.dia;
    }

	public void setDia(Calendar dia) {
        this.dia = dia;
    }

	public Double getValor() {
        return this.valor;
    }

	public void setValor(Double valor) {
        this.valor = valor;
    }

	public Despesas getClassificacao() {
        return this.classificacao;
    }

	public void setClassificacao(Despesas classificacao) {
        this.classificacao = classificacao;
    }

	public Despesa_Gastos getItem() {
        return this.item;
    }

	public void setItem(Despesa_Gastos item) {
        this.item = item;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("mes_ano", "dia", "valor", "classificacao", "item");

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

	public static Long countFindDespesasLojasByItem(Despesa_Gastos item) {
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

	public static TypedQuery<DespesasLoja> findDespesasLojasByItem(Despesa_Gastos item) {
        if (item == null) throw new IllegalArgumentException("The item argument is required");
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery<DespesasLoja> q = em.createQuery("SELECT o FROM DespesasLoja AS o WHERE o.item = :item", DespesasLoja.class);
        q.setParameter("item", item);
        return q;
    }

	public static TypedQuery<DespesasLoja> findDespesasLojasByItem(Despesa_Gastos item, String sortFieldName, String sortOrder) {
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
}
