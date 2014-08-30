package br.com.conjmc.cadastrobasico;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.Security;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Enumerated;

@Configurable
@Entity
public class Usuarios {

    /**
     */
    @NotNull
    @OneToOne
    private Funcionarios nome;

    /**
     */
    @NotNull
    @Size(min = 6)
    private String senha;

    /**
     */
    @NotNull
    @Enumerated
    private Perfil perfil;

	public Funcionarios getNome() {
        return this.nome;
    }

	public void setNome(Funcionarios nome) {
        this.nome = nome;
    }

	public String getSenha() {
        return this.senha;
    }

	public void setSenha(String senha) {
        this.senha = Security.sha256(senha);
    }

	public Perfil getPerfil() {
        return this.perfil;
    }

	public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("nome", "senha", "perfil");

	public static final EntityManager entityManager() {
        EntityManager em = new Usuarios().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countUsuarioses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Usuarios o", Long.class).getSingleResult();
    }

	public static List<Usuarios> findAllUsuarioses() {
        return entityManager().createQuery("SELECT o FROM Usuarios o", Usuarios.class).getResultList();
    }

	public static List<Usuarios> findAllUsuarioses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Usuarios o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Usuarios.class).getResultList();
    }

	public static Usuarios findUsuarios(Long id) {
        if (id == null) return null;
        return entityManager().find(Usuarios.class, id);
    }

	public static List<Usuarios> findUsuariosEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Usuarios o", Usuarios.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Usuarios> findUsuariosEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Usuarios o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Usuarios.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Usuarios attached = Usuarios.findUsuarios(this.id);
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
    public Usuarios merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Usuarios merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
