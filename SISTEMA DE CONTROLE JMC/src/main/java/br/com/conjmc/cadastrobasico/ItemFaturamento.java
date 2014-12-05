package br.com.conjmc.cadastrobasico;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.ObejctSession;

@Entity
@Configurable
public class ItemFaturamento {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String descricao;
	
	private int quantidade;
	
	private Double valor;
	
	private int numeroComanda;
	
	@ManyToOne
    private Lojas loja;
	
	@Version
    @Column(name = "version")
    private Integer version;
	
	@ManyToOne
    @JoinColumn(name="faturamento_id")
    private Faturamento faturamento;
	
	//Getters and Setters	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public int getNumeroComanda() {
		return numeroComanda;
	}

	public void setNumeroComanda(int numeroComanda) {
		this.numeroComanda = numeroComanda;
	}

	public Lojas getLoja() {
		return loja;
	}

	public void setLoja(Lojas loja) {
		this.loja = loja;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Faturamento getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(Faturamento faturamento) {
		this.faturamento = faturamento;
	}

	//DAO
	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Faturamento().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
	
	public static List<Faturamento> findAllItemFaturmento() {
		Query query = entityManager().createQuery("SELECT o FROM ItemFaturamento o where o.loja.id = :loja", ItemFaturamento.class);
		query.setParameter("loja", ObejctSession.idLoja());
        return query.getResultList();
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
        }
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public ItemFaturamento merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ItemFaturamento merged = this.entityManager.merge(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), merged.getId(), ItemFaturamento.class.getSimpleName());
        this.entityManager.flush();
        return merged;
    }
}
