package br.com.conjmc.cadastrobasico;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Configurable
public class Fornecedores {

    /**
     * Nome dos Fornecedores
     */
    @NotNull
    @Size(max = 10)
    private String nome;

    /**
     * Nome fantansia dos fonecedores
     */
    @NotNull
    private String apelido;

    /**
     *  Cadastro Nacional da Pessoa Jurídica dos fonecedores.
     */
    @Size(max = 18)
    private String cnpj;

    /**
     * Se esta ativo ou não.
     */
    @NotNull
    @AssertTrue
    private Boolean situacao;

    /**
     * Lista de produdos dos fornecedores. 
     */
    @OneToMany(targetEntity=DespesasGastos.class, fetch=FetchType.EAGER)
    @JoinTable(name="fornecedores_produtos_fornecidos", joinColumns={@JoinColumn(name="fornecedores", referencedColumnName="id")}, inverseJoinColumns={@JoinColumn(name="produtos_fornecidos", referencedColumnName="id", unique=false)})
    private List<DespesasGastos> produtosFornecidos;

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

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("nome", "apelido", "cnpj", "situacao", "produtosFornecidos");

	public static final EntityManager entityManager() {
        EntityManager em = new Fornecedores().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countFornecedoreses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Fornecedores o", Long.class).getSingleResult();
    }

	public static List<Fornecedores> findAllFornecedoreses() {
        return entityManager().createQuery("SELECT o FROM Fornecedores o", Fornecedores.class).getResultList();
    }

	public static List<Fornecedores> findAllFornecedoreses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Fornecedores o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Fornecedores.class).getResultList();
    }

	public static Fornecedores findFornecedores(Long id) {
        if (id == null) return null;
        return entityManager().find(Fornecedores.class, id);
    }

	public static List<Fornecedores> findFornecedoresEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Fornecedores o", Fornecedores.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Fornecedores> findFornecedoresEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Fornecedores o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Fornecedores.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Fornecedores attached = Fornecedores.findFornecedores(this.id);
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
    public Fornecedores merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Fornecedores merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getNome() {
        return this.nome;
    }

	public void setNome(String nome) {
        this.nome = nome;
    }

	public String getApelido() {
        return this.apelido;
    }

	public void setApelido(String apelido) {
        this.apelido = apelido;
    }

	public String getCnpj() {
        return this.cnpj;
    }

	public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

	public Boolean getSituacao() {
        return this.situacao;
    }

	public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apelido == null) ? 0 : apelido.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime
				* result
				+ ((produtosFornecidos == null) ? 0 : produtosFornecidos
						.hashCode());
		result = prime * result
				+ ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		Fornecedores other = (Fornecedores) obj;
		if (apelido == null) {
			if (other.apelido != null)
				return false;
		} else if (!apelido.equals(other.apelido))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (produtosFornecidos == null) {
			if (other.produtosFornecidos != null)
				return false;
		} else if (!produtosFornecidos.equals(other.produtosFornecidos))
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

	public List<DespesasGastos> getProdutosFornecidos() {
		return produtosFornecidos;
	}
	
	public void setProdutosFornecidos(List<DespesasGastos> produtosFornecidos) {
		this.produtosFornecidos = produtosFornecidos;
	}
}
