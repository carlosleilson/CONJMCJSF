package br.com.conjmc.cadastrobasico;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Despesa_Gastos {

    /**
     */
    @NotNull
    @Size(max = 30)
    private String descrisao;

    /**
     */
    @ManyToOne
    private Despesas classificacao;

    /**
     */
    @AssertFalse
    private Boolean DespesaPessoal;

    /**
     */
    @NotNull
    @AssertTrue
    private Boolean situacao;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getDescrisao() {
        return this.descrisao;
    }

	public void setDescrisao(String descrisao) {
        this.descrisao = descrisao;
    }

	public Despesas getClassificacao() {
        return this.classificacao;
    }

	public void setClassificacao(Despesas classificacao) {
        this.classificacao = classificacao;
    }

	public Boolean getDespesaPessoal() {
        return this.DespesaPessoal;
    }

	public void setDespesaPessoal(Boolean DespesaPessoal) {
        this.DespesaPessoal = DespesaPessoal;
    }

	public Boolean getSituacao() {
        return this.situacao;
    }

	public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("descrisao", "classificacao", "DespesaPessoal", "situacao");

	public static final EntityManager entityManager() {
        EntityManager em = new Despesa_Gastos().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countDespesa_Gastoses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Despesa_Gastos o", Long.class).getSingleResult();
    }

	public static List<Despesa_Gastos> findAllDespesa_Gastoses() {
        return entityManager().createQuery("SELECT o FROM Despesa_Gastos o", Despesa_Gastos.class).getResultList();
    }

	public static List<Despesa_Gastos> findAllDespesa_Gastoses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Despesa_Gastos o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Despesa_Gastos.class).getResultList();
    }

	public static Despesa_Gastos findDespesa_Gastos(Long id) {
        if (id == null) return null;
        return entityManager().find(Despesa_Gastos.class, id);
    }

	public static List<Despesa_Gastos> findDespesa_GastosEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Despesa_Gastos o", Despesa_Gastos.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Despesa_Gastos> findDespesa_GastosEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Despesa_Gastos o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Despesa_Gastos.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Despesa_Gastos attached = Despesa_Gastos.findDespesa_Gastos(this.id);
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
    public Despesa_Gastos merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Despesa_Gastos merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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
