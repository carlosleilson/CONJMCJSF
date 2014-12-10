package br.com.conjmc.cadastrobasico;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
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
public class ItemFaturamentoDescricao {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String item;
	
	@Version
	private int versao;
	
	private boolean ativo;
	
	@ManyToOne
    @JoinColumn(name="faturamento_id")
    private Faturamento faturamento;
	
	@ManyToOne(targetEntity=ItemFaturamento.class)
	@JoinColumn(name="item_faturamento_id")
	private ItemFaturamento itemFaturamento;

	//Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getVersao() {
		return versao;
	}

	public void setVersao(int versao) {
		this.versao = versao;
	}
	
	public Faturamento getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(Faturamento faturamento) {
		this.faturamento = faturamento;
	}
	
	public ItemFaturamento getItemFaturamento() {
		return itemFaturamento;
	}

	public void setItemFaturamento(ItemFaturamento itemFaturamento) {
		this.itemFaturamento = itemFaturamento;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	//DAO
	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Faturamento().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
	
	public static List<ItemFaturamentoDescricao> findAllItemFaturmento() {
		Query query = entityManager().createQuery("SELECT o FROM ItemFaturamentoDescricao o", ItemFaturamentoDescricao.class);
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
    public ItemFaturamentoDescricao merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ItemFaturamentoDescricao merged = this.entityManager.merge(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), merged.getId(), ItemFaturamentoDescricao.class.getSimpleName());
        this.entityManager.flush();
        return merged;
    }
	
	public long quantidadeTotal(Date dataInicial, Date datafinal, String turno) {
		String sql = "SELECT SUM(o.itemFaturamento.quantidade) FROM ItemFaturamentoDescricao o where o.faturamento.periodo between :dataInicial and :dataFinal";
		Query query = entityManager().createQuery(sql, Long.class);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", datafinal);
       	return (long) query.getSingleResult();
	}
	
	public long numeroComandasTotal(Date dataInicial, Date datafinal, String turno) {
		String sql = "SELECT SUM(o.itemFaturamento.numeroComanda) FROM ItemFaturamentoDescricao o where o.faturamento.periodo between :dataInicial and :dataFinal";
		Query query = entityManager().createQuery(sql, Long.class);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", datafinal);
       	return (long) query.getSingleResult();
	}
	
	public Double valorTotal(Date dataInicial, Date datafinal, String turno) {
		String sql = "SELECT SUM(o.itemFaturamento.valor) FROM ItemFaturamentoDescricao o where o.faturamento.periodo between :dataInicial and :dataFinal";
		Query query = entityManager().createQuery(sql, Double.class);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", datafinal);
       	return (double) query.getSingleResult();
	}
	
}
