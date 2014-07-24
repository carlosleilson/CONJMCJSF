package br.com.conjmc.controlediario.controlesaida;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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

import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.DespesasGastos;

import javax.persistence.ManyToOne;

import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.despesa.DespesasLoja;

@Configurable
@Entity
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Sangria {

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date periodo;

    /**
     */
    @NotNull
    private Double valor;

    /**
     */
    private String origem;
    
    @NotNull
    private boolean sangria;
    
    private String turno;

    /**
     */
    @ManyToOne
    private DespesasGastos item;
    
    /**
     * Campo do Despesas
     */
    @ManyToOne
    private Despesas classificacao;

    /**
     */
    @ManyToOne
    private Funcionarios funcionario;
    
    /**
     */

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Date getPeriodo() {
        return this.periodo;
    }

	public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

	public Double getValor() {
        return this.valor;
    }

	public void setValor(Double valor) {
        this.valor = valor;
    }

	public String getOrigem() {
        return this.origem;
    }

	public void setOrigem(String origem) {
        this.origem = origem;
    }

	public DespesasGastos getItem() {
        return this.item;
    }

	public void setItem(DespesasGastos item) {
        this.item = item;
    }

	public Funcionarios getFuncionario() {
        return this.funcionario;
    }

	public void setFuncionario(Funcionarios funcionario) {
        this.funcionario = funcionario;
    }

	public boolean isSangria() {
		return sangria;
	}

	public void setSangria(boolean sangria) {
		this.sangria = sangria;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("periodo", "valor", "origem", "item", "classificacao", "funcionario","sangria");

	public static final EntityManager entityManager() {
        EntityManager em = new Sangria().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countSangrias() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Sangria o", Long.class).getSingleResult();
    }

	public static List<Sangria> findAllSangrias() {
        return entityManager().createQuery("SELECT o FROM Sangria o", Sangria.class).getResultList();
    }
	
	public static List<Sangria> findAllSangriasAtivas() {
        return entityManager().createQuery("SELECT o FROM Sangria o where o.sangria = true", Sangria.class).getResultList();
    }

	public static List<Sangria> findAllSangrias(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Sangria o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Sangria.class).getResultList();
    }

	public static Sangria findSangria(Long id) {
        if (id == null) return null;
        return entityManager().find(Sangria.class, id);
    }

	public static List<Sangria> findSangriaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Sangria o", Sangria.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Sangria> findSangriaEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Sangria o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Sangria.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Sangria attached = Sangria.findSangria(this.id);
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
    public Sangria merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Sangria merged = this.entityManager.merge(this);
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

	public Despesas getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Despesas classificacao) {
		this.classificacao = classificacao;
	}
	
	public static List< Sangria > encontrarPorData(Date dataInicial, Date dataFinal,DespesasGastos item) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (dataInicial == null) throw new IllegalArgumentException("O Dia é obrigatorio");
        if (dataFinal == null) throw new IllegalArgumentException("O Até Mes/ano é obrigatorio");
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery<Sangria> q = null;
        if(item!=null){
            q = em.createQuery("SELECT o FROM Sangria AS o WHERE o.periodo between :dataInicial and :dataFinal and o.item = :item", Sangria.class);
            q.setParameter("item", item);
        }else{	
        	q = em.createQuery("SELECT o FROM Sangria AS o WHERE o.periodo between :dataInicial and :dataFinal", Sangria.class);
        }
        q.setParameter("dataInicial", dataInicial );
        q.setParameter("dataFinal", dataFinal);
        //return (q.getResultList().isEmpty()? findAllDespesasLojas():q.getResultList());
        return q.getResultList();
    }
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sangria other = (Sangria) obj;
		if (classificacao == null) {
			if (other.classificacao != null)
				return false;
		} else if (!classificacao.equals(other.classificacao))
			return false;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
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
		if (origem == null) {
			if (other.origem != null)
				return false;
		} else if (!origem.equals(other.origem))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
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
