package br.com.conjmc.cadastrobasico;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import javax.validation.constraints.AssertTrue;

@Entity
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Funcionarios {

    /**
     */
    @NotNull
    @Size(min = 10)
    private String nome;

    /**
     */
    @NotNull
    @Size(max = 10)
    private String apelido;

    /**
     */
    @NotNull
    @ManyToOne
    private Cargos cargo;

    /**
     */
    @NotNull
    private Double salario;

    /**
     */
    @NotNull
    private Boolean situacao;

    /**
     */
    @Size(max = 15)
    private String cpf;

    /**
     */
    @Size(max = 30)
    private String identidade;

    /**
     */
    private String logradouro;

    /**
     */
    private String bairro;

    /**
     */
    private String cidade;

    /**
     */
    @Size(max = 10)
    private String cep;

    /**
     */
    @Size(max = 120)
    private String outrasInformacoes;

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

	public Cargos getCargo() {
        return this.cargo;
    }

	public void setCargo(Cargos cargo) {
        this.cargo = cargo;
    }

	public Double getSalario() {
        return this.salario;
    }

	public void setSalario(Double salario) {
        this.salario = salario;
    }

	public Boolean getSituacao() {
        return this.situacao;
    }

	public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

	public String getCpf() {
        return this.cpf;
    }

	public void setCpf(String cpf) {
        this.cpf = cpf;
    }

	public String getIdentidade() {
        return this.identidade;
    }

	public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

	public String getLogradouro() {
        return this.logradouro;
    }

	public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

	public String getBairro() {
        return this.bairro;
    }

	public void setBairro(String bairro) {
        this.bairro = bairro;
    }

	public String getCidade() {
        return this.cidade;
    }

	public void setCidade(String cidade) {
        this.cidade = cidade;
    }

	public String getCep() {
        return this.cep;
    }

	public void setCep(String cep) {
        this.cep = cep;
    }

	public String getOutrasInformacoes() {
        return this.outrasInformacoes;
    }

	public void setOutrasInformacoes(String outrasInformacoes) {
        this.outrasInformacoes = outrasInformacoes;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("nome", "apelido", "cargo", "salario", "situacao", "cpf", "identidade", "logradouro", "bairro", "cidade", "cep", "outrasInformacoes");

	public static final EntityManager entityManager() {
        EntityManager em = new Funcionarios().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countFuncionarioses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Funcionarios o", Long.class).getSingleResult();
    }

	public static List<Funcionarios> findAllFuncionarioses() {
        return entityManager().createQuery("SELECT o FROM Funcionarios o", Funcionarios.class).getResultList();
    }
	
	public static List<Funcionarios> findAllFuncionariosAtivos() {
        return entityManager().createQuery("SELECT o FROM Funcionarios o where o.situacao = true", Funcionarios.class).getResultList();
    }

	public static List<Funcionarios> findAllFuncionarioses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Funcionarios o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Funcionarios.class).getResultList();
    }

	public static Funcionarios findFuncionarios(Long id) {
        if (id == null) return null;
        return entityManager().find(Funcionarios.class, id);
    }

	public static List<Funcionarios> findFuncionariosEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Funcionarios o", Funcionarios.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Funcionarios> findFuncionariosEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Funcionarios o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Funcionarios.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public String remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
        	this.entityManager.remove(this);
        	/*try {
        		this.entityManager.remove(this);
			} catch (Exception ex) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "O usuário não pode ser apagando porque depende de outros modulos."));
				return "pages/funcionarios.xhtml";
			}*/
        } else {
            Funcionarios attached = Funcionarios.findFuncionarios(this.id);
            this.entityManager.remove(attached);
        }
        return null;
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
    public Funcionarios merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Funcionarios merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionarios other = (Funcionarios) obj;
		if (apelido == null) {
			if (other.apelido != null)
				return false;
		} else if (!apelido.equals(other.apelido))
			return false;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cargo == null) {
			if (other.cargo != null)
				return false;
		} else if (!cargo.equals(other.cargo))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (identidade == null) {
			if (other.identidade != null)
				return false;
		} else if (!identidade.equals(other.identidade))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (outrasInformacoes == null) {
			if (other.outrasInformacoes != null)
				return false;
		} else if (!outrasInformacoes.equals(other.outrasInformacoes))
			return false;
		if (salario == null) {
			if (other.salario != null)
				return false;
		} else if (!salario.equals(other.salario))
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
