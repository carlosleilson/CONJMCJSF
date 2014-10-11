package br.com.conjmc.cadastrobasico;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
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
public class Motoqueiros implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
     */
    @NotNull
    @Size(max = 10)
    private String apelido;

    /**
     */
    private String nome;

    /**
     */
    @NotNull
    @AssertTrue
    private Boolean situacao;

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

	public String getApelido() {
        return this.apelido;
    }

	public void setApelido(String apelido) {
        this.apelido = apelido;
    }

	public String getNome() {
        return this.nome;
    }

	public void setNome(String nome) {
        this.nome = nome;
    }

	public Boolean getSituacao() {
        return this.situacao;
    }

	public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("apelido", "nome", "situacao");

	public static final EntityManager entityManager() {
        EntityManager em = new Motoqueiros().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countMotoqueiroses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Motoqueiros o", Long.class).getSingleResult();
    }

	public static List<Motoqueiros> findAllMotoqueiroses() {
        return entityManager().createQuery("SELECT o FROM Motoqueiros o", Motoqueiros.class).getResultList();
    }

	public static List<Motoqueiros> findAllMotoqueiroses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Motoqueiros o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Motoqueiros.class).getResultList();
    }

	public static Motoqueiros findMotoqueiros(Long id) {
        if (id == null) return null;
        return entityManager().find(Motoqueiros.class, id);
    }

	public static List<Motoqueiros> findMotoqueirosEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Motoqueiros o", Motoqueiros.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Motoqueiros> findMotoqueirosEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Motoqueiros o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Motoqueiros.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Motoqueiros attached = Motoqueiros.findMotoqueiros(this.id);
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
    public Motoqueiros merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Motoqueiros merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
