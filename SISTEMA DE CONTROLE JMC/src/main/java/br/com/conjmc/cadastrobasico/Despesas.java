package br.com.conjmc.cadastrobasico;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    @Enumerated
    private Resumos idResumo;

    /**
     */
    @NotNull
    private Boolean situacao;
    
   /* @OneToMany (mappedBy="classificacao")
    private List<DespesasGastos> classificacao;*/

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

	public Resumos getIdResumo() {
        return this.idResumo;
    }

	public void setIdResumo(Resumos idResumo) {
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
	
	public static List<Despesas> findAllDespesasesByResumo() {
        return entityManager().createQuery("SELECT o FROM Despesas o order by o.idResumo asc", Despesas.class).getResultList();
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


	/*public List<DespesasGastos> getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(List<DespesasGastos> classificacao) {
		this.classificacao = classificacao;
	}*/
    public static List<Despesas> findAllIdResumo(Resumos idResumo) {
        Query query = entityManager().createQuery("SELECT o FROM Despesas o where o.idResumo = :idResumo", Despesas.class);
        query.setParameter("idResumo", idResumo);
       return (List< Despesas >)query.getResultList();        
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
		return true;
	}  	
    
   
}
