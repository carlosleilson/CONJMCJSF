package br.com.conjmc.cadastrobasico;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.controlediario.controlesaida.Sangria;

@Entity
@Configurable
public class Contas implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date vencimento;
	
	private Double valor;
	
	//private Fornecedores fornecedor;
	
	private String detalhamento;
	
	private String detalhamentoBanco;
	
	@Temporal(TemporalType.DATE)
	private Date dataPagamento;
	
	private String tipoPagamento;
	
	private String banco;
	
	@Version
    @Column(name = "version")
    private Integer version;

	//Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	@OneToMany (mappedBy="conta", cascade=CascadeType.REMOVE)
    private List<Sangria> sangria;

	/*public Fornecedores getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedores fornecedor) {
		this.fornecedor = fornecedor;
	}*/

	public String getDetalhamento() {
		return detalhamento;
	}

	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<Sangria> getSangria() {
		return sangria;
	}

	public void setSangria(List<Sangria> sangria) {
		this.sangria = sangria;
	}

	public String getDetalhamentoBanco() {
		return detalhamentoBanco;
	}

	public void setDetalhamentoBanco(String detalhamentoBanco) {
		this.detalhamentoBanco = detalhamentoBanco;
	}

	// DAO
	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Contas().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static List<Contas> findAllContas() {
        return entityManager().createQuery("SELECT o FROM Contas o", Contas.class).getResultList();
    }

	public static Contas findContas(Long id) {
        if (id == null) return null;
        return entityManager().find(Contas.class, id);
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
            Contas attached = Contas.findContas(this.id);
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
    public Contas merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Contas merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
}
