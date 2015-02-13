package br.com.conjmc.cadastrobasico;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.ObejctSession;

@Entity
@Configurable
public class Motoqueiros implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Size(max = 10,message="Não pode ter mais do que 10 caracteres")
	
	private String apelido;
	private String nome;
	private boolean situacao;
	
	@Version
	private int version;
	
	//Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApelido() {
		return apelido;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public boolean isSituacao() {
		return situacao;
	}
	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}
	
	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("apelido", "nome");

	public static final EntityManager entityManager() {
        EntityManager em = new Motoqueiros().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countMotoqueiroses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Motoqueiros o", Long.class).getSingleResult();
    }

	public static List<Motoqueiros> findAllMotoqueiroses() {
        return entityManager().createQuery("SELECT o FROM Motoqueiros o where o.situacao=true", Motoqueiros.class).getResultList();
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

	public static List<Motoqueiros> findMotoqueiroEntries(int firstResult, int maxResults) {
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
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), this.id, this.getClass().getSimpleName());
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
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), merged.getId(), Motoqueiros.class.getSimpleName());
        this.entityManager.flush();
        return merged;
	}

}
