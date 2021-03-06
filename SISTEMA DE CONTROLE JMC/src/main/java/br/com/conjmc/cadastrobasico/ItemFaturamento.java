package br.com.conjmc.cadastrobasico;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.DataUltil;
import br.com.conjmc.jsf.util.ObejctSession;

@Entity
@Configurable
public class ItemFaturamento {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int quantidade;
	
	private double valor;
	
	@Temporal(TemporalType.DATE)
	private Date periodo;
	
	@Enumerated
	private Turno turno;
	
	@ManyToOne
	@JoinColumn(name="faturamento_descricao_id")
	private ItemFaturamentoDescricao faturamentoDescricao;
	
	@Version
    @Column(name = "version")
    private Integer version;
	
	@ManyToOne
	private Lojas loja;
	
	//Getters and Setters		
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public Date getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}
	
	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}


	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public ItemFaturamentoDescricao getFaturamentoDescricao() {
		return faturamentoDescricao;
	}

	public void setFaturamentoDescricao(
			ItemFaturamentoDescricao faturamentoDescricao) {
		this.faturamentoDescricao = faturamentoDescricao;
	}

	public Lojas getLoja() {
		return loja;
	}

	public void setLoja(Lojas loja) {
		this.loja = loja;
	} 

	//DAO
	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new ItemFaturamento().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
	
	public static List<ItemFaturamento> findAllItemFaturmento() {
		Query query = entityManager().createQuery("SELECT o FROM ItemFaturamento o where o.loja.id = :loja", ItemFaturamento.class);
		query.setParameter("loja", ObejctSession.idLoja());
        return query.getResultList();
    }
	
	public static List<ItemFaturamento> findAllItemFaturmentoByDate(Date data) {
		Query query = entityManager().createQuery("SELECT o FROM ItemFaturamento o where o.periodo between :dataInicial and :dataFinal and o.loja.id = :loja", ItemFaturamento.class);
		query.setParameter("dataInicial", DataUltil.primeiroDiaMesTemp(data));
		query.setParameter("dataFinal", DataUltil.ultimoDiaMes(data));
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
	
	public static List<ItemFaturamento> findAllItemFaturmento(Date dataInicial, Date datafinal, Turno turno, String item) {
		String sql="SELECT o FROM ItemFaturamento o where o.periodo between :dataInicial and :dataFinal and o.loja= :loja";
		if(turno != null) {			
			sql+=" and turno="+turno.ordinal();
		}
		if (item != ""){
			sql+=" and o.faturamentoDescricao.id="+item;
		}
		Query query = entityManager().createQuery(sql, ItemFaturamento.class);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", datafinal);
		query.setParameter("loja", ObejctSession.loja());
        return query.getResultList();
    }
	
	public long quantidadeTotal(Date dataInicial, Date datafinal, Turno turno, String item) {
		String sql="SELECT SUM(o.quantidade) FROM ItemFaturamento o where o.periodo between :dataInicial and :dataFinal and o.loja=:loja";
		if(turno != null) {			
			sql+=" and turno="+turno.ordinal();
		}
		if (item != ""){
			sql+=" and o.faturamentoDescricao.id="+item;
		}
		Query query = entityManager().createQuery(sql, Long.class);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", datafinal);
		query.setParameter("loja", ObejctSession.loja());
		long valor;
		try {
			valor = (long) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public Double valorTotal(Date dataInicial, Date datafinal, Turno turno, String item) {
		String sql="SELECT SUM(o.valor) FROM ItemFaturamento o where o.periodo between :dataInicial and :dataFinal and o.loja=:loja";
		if(turno != null) {			
			sql+=" and turno="+turno.ordinal();
		}
		if (item != "") {
			sql+=" and o.faturamentoDescricao.id="+item;
		}
		Query query = entityManager().createQuery(sql, Double.class);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", datafinal);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public Double faturamentoByDateAndId(Date dataInicial, Date datafinal, String id) {
		Query query = entityManager().createQuery("SELECT SUM(o.valor) FROM ItemFaturamento o where o.periodo between :dataInicial and :dataFinal and o.loja=:loja and o.faturamentoDescricao.id=:ids", Double.class);
		query.setParameter("dataInicial", DataUltil.primeiroDiaMesTemp(dataInicial));
		query.setParameter("dataFinal",  DataUltil.ultimoDiaMes(datafinal));
		query.setParameter("ids", Long.valueOf(id));
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public long validarFaturamento(Date data, Turno turno) {
		String sql="SELECT count(o.quantidade) FROM ItemFaturamento o where o.periodo=:data and o.loja=:loja and turno="+turno.ordinal();
		Query query = entityManager().createQuery(sql, Long.class);
		query.setParameter("data", data);
		query.setParameter("loja", ObejctSession.loja());
       	return (long) query.getSingleResult();
	}
	
	public static ItemFaturamento ultimoFaturamento(){
		Query query = entityManager().createQuery("Select o from ItemFaturamento o where o.loja=:loja order by id desc", ItemFaturamento.class);
		query.setParameter("loja", ObejctSession.loja());
		query.setMaxResults(1);
		return (ItemFaturamento) query.getSingleResult();
	}
	
}
