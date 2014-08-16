package br.com.conjmc.cadastrobasico;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.AssertTrue;

@Configurable
@Entity
public class LancamentosFuncionarios {

    /**
     */
    @NotNull
    @Size(max = 30)
    private String descricao;

    /**
     */
    @NotNull
    @AssertTrue
    private Boolean situacao;

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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("descricao", "situacao");

	public static final EntityManager entityManager() {
        EntityManager em = new LancamentosFuncionarios().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countLancamentosFuncionarioses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM LancamentosFuncionarios o", Long.class).getSingleResult();
    }

	public static List<LancamentosFuncionarios> findAllLancamentosFuncionarioses() {
        return entityManager().createQuery("SELECT o FROM LancamentosFuncionarios o", LancamentosFuncionarios.class).getResultList();
    }

	public static List<LancamentosFuncionarios> findAllLancamentosFuncionarioses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM LancamentosFuncionarios o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, LancamentosFuncionarios.class).getResultList();
    }

	public static LancamentosFuncionarios findLancamentosFuncionarios(Long id) {
        if (id == null) return null;
        return entityManager().find(LancamentosFuncionarios.class, id);
    }

	public static List<LancamentosFuncionarios> findLancamentosFuncionariosEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM LancamentosFuncionarios o", LancamentosFuncionarios.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<LancamentosFuncionarios> findLancamentosFuncionariosEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM LancamentosFuncionarios o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, LancamentosFuncionarios.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            LancamentosFuncionarios attached = LancamentosFuncionarios.findLancamentosFuncionarios(this.id);
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
    public LancamentosFuncionarios merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        LancamentosFuncionarios merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String getDescricao() {
        return this.descricao;
    }

	public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

	public Boolean getSituacao() {
        return this.situacao;
    }

	public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }
}
