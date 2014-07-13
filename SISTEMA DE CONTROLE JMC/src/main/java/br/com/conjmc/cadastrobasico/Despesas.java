package br.com.conjmc.cadastrobasico;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
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

/**
 * @author c1276777
 *
 */
@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Despesas {

    /**
     */
    @NotNull
    @Size(max = 2)
    private String codigo;

    /**
     */
    @NotNull
    @Size(max = 50)
    private String descricao;

    /**
     */
    @NotNull
    @Size(max = 5)
    private String idResumo;

    /**
     */
    @NotNull
    private Boolean situacao;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getCodigo() {
        return this.codigo;
    }

	public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

	public String getDescricao() {
        return this.descricao;
    }

	public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

	public String getIdResumo() {
        return this.idResumo;
    }

	public void setIdResumo(String idResumo) {
        this.idResumo = idResumo;
    }

	public Boolean getSituacao() {
        return this.situacao;
    }

	public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
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

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("codigo", "descricao", "idResumo", "situacao");

	public static final EntityManager entityManager() {
        EntityManager em = new Despesas().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countDespesases() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Despesas o", Long.class).getSingleResult();
    }

	public static List<Despesas> findAllDespesases() {
        return entityManager().createQuery("SELECT o FROM Despesas o", Despesas.class).getResultList();
    }
	
	public static List<Despesas> findAllDespesasAtivas() {
        return entityManager().createQuery("SELECT o FROM Despesas o where o.situacao = true", Despesas.class).getResultList();
    }

	public static List<Despesas> findAllDespesases(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Despesas o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Despesas.class).getResultList();
    }

	public static Despesas findDespesas(Long id) {
        if (id == null) return null;
        return entityManager().find(Despesas.class, id);
    }

	public static List<Despesas> findDespesasEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Despesas o", Despesas.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Despesas> findDespesasEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Despesas o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Despesas.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Despesas attached = Despesas.findDespesas(this.id);
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
    public Despesas merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Despesas merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Despesas other = (Despesas) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idResumo == null) {
			if (other.idResumo != null)
				return false;
		} else if (!idResumo.equals(other.idResumo))
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	
}
