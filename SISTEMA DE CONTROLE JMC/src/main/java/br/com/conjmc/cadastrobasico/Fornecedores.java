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
import javax.validation.constraints.AssertTrue;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Fornecedores {

    /**
     */
    @NotNull
    @Size(max = 10)
    private String nome;

    /**
     */
    @NotNull
    @Size(max = 10)
    private String apelido;

    /**
     */
    @Size(max = 18)
    private String cnpj;

    /**
     */
    @NotNull
    @AssertTrue
    private Boolean situacao;

    /**
     */
    @ManyToOne
    private Despesas produtosFornecidos;

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

	public Despesas getProdutosFornecidos() {
        return this.produtosFornecidos;
    }

	public void setProdutosFornecidos(Despesas produtosFornecidos) {
        this.produtosFornecidos = produtosFornecidos;
    }
}
