package br.com.conjmc.cadastrobasico;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.controlediario.controlesaida.Sangria;

@Entity
@Configurable
public class DespesasGastos {

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
    private Boolean despesaPessoal;

    /**
     */
    @NotNull
    private Boolean situacao;
    
    /*@OneToMany (mappedBy="item")
    private List<Sangria> sangria;*/

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("descrisao", "classificacao", "despesaPessoal", "situacao");

	public static final EntityManager entityManager() {
        EntityManager em = new DespesasGastos().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countDespesasGastoses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM DespesasGastos o", Long.class).getSingleResult();
    }

	public static List<DespesasGastos> findAllDespesasGastoses() {
        return entityManager().createQuery("SELECT o FROM DespesasGastos o", DespesasGastos.class).getResultList();
    }
	
	public static List<DespesasGastos> findAllDespesasGastosAtivos() {
        return entityManager().createQuery("SELECT o FROM DespesasGastos o where o.situacao = true", DespesasGastos.class).getResultList();
    }	
	
	public static List<DespesasGastos> findAllClassificaco(long classificacao) {
		 Query query = entityManager().createQuery("SELECT o FROM DespesasGastos o where o.classificacao.id = :classificacao", DespesasGastos.class);
		 query.setParameter("classificacao", classificacao);
		 List<DespesasGastos> despesasGastosForClassificacao = query.getResultList();
		 return despesasGastosForClassificacao;
	}

	public static List<DespesasGastos> findAllDespesasGastoses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM DespesasGastos o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, DespesasGastos.class).getResultList();
    }

	public static DespesasGastos findDespesasGastos(Long id) {
        if (id == null) return null;
        return entityManager().find(DespesasGastos.class, id);
    }

	public static List<DespesasGastos> findDespesasGastosEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM DespesasGastos o", DespesasGastos.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<DespesasGastos> findDespesasGastosEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM DespesasGastos o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, DespesasGastos.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            DespesasGastos attached = DespesasGastos.findDespesasGastos(this.id);
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
    public DespesasGastos merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        DespesasGastos merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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
        return this.despesaPessoal;
    }

	public void setDespesaPessoal(Boolean despesaPessoal) {
        this.despesaPessoal = despesaPessoal;
    }

	public Boolean getSituacao() {
        return this.situacao;
    }

	public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descrisao == null) ? 0 : descrisao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DespesasGastos other = (DespesasGastos) obj;
		if (descrisao == null) {
			if (other.descrisao != null)
				return false;
		} else if (!descrisao.equals(other.descrisao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/*public List<Sangria> getSangria() {
		return sangria;
	}

	public void setSangria(List<Sangria> sangria) {
		this.sangria = sangria;
	}
	*/
}
