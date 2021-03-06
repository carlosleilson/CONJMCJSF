package br.com.conjmc.cadastrobasico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.ObejctSession;

@Entity
@Configurable
public class ControleValoresPendentes implements Serializable {

	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer numeroPedido;
	
	private String telefone;
	
	private Double valor;
	
	private double dinheiro;
	
	private double cartao;
	
	private double trocado;
	
	private double moeda;
	
	private double cheque;
	
	private boolean ativo;
	
	@Enumerated
	private Status status;
	
	@ManyToOne
	private Motoqueiros motoqueiro;
	
	@Enumerated
	private Turno turno;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@NotNull
	@ManyToOne
	private Lojas loja;

	//Getter and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Integer numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Motoqueiros getMotoqueiro() {
		return motoqueiro;
	}

	public void setMotoqueiro(Motoqueiros motoqueiro) {
		this.motoqueiro = motoqueiro;
	}
	
	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Lojas getLoja() {
		return loja;
	}

	public void setLoja(Lojas loja) {
		this.loja = loja;
	}	
	
	public double getDinheiro() {
		return dinheiro;
	}

	public void setDinheiro(double dinheiro) {
		this.dinheiro = dinheiro;
	}

	public double getCartao() {
		return cartao;
	}

	public void setCartao(double cartao) {
		this.cartao = cartao;
	}

	public double getTrocado() {
		return trocado;
	}

	public void setTrocado(double trocado) {
		this.trocado = trocado;
	}

	public double getMoeda() {
		return moeda;
	}

	public void setMoeda(double moeda) {
		this.moeda = moeda;
	}
	
	public double getCheque() {
		return cheque;
	}

	public void setCheque(double cheque) {
		this.cheque = cheque;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	// DAO
	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("nome", "controleValoresPendentes");

	public static final EntityManager entityManager() {
        EntityManager em = new ControleValoresPendentes().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countControleValoresPendenteses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ControleValoresPendentes o", Long.class).getSingleResult();
    }
	
	public long validarValoresPendentes(Date data, Turno turno, int pedido) {
		String sql="SELECT count(o.id) FROM ControleValoresPendentes o where o.data=:data and numeroPedido=:pedido and o.loja=:loja and turno="+turno.ordinal();
		Query query = entityManager().createQuery(sql, Long.class);
		query.setParameter("data", data);
		query.setParameter("loja", ObejctSession.loja());
		query.setParameter("pedido", pedido);
       	return (long) query.getSingleResult();
	}

	public static List<ControleValoresPendentes> findAllControleValoresPendenteses() {
		Query query = entityManager().createQuery("SELECT o FROM ControleValoresPendentes o where o.ativo=true and o.data=:data and o.baixado=false order by o.id desc", ControleValoresPendentes.class);
		query.setParameter("data", new Date());
		return query.getResultList();
    }

	public static List<ControleValoresPendentes> findAllControleValoresPendenteses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ControleValoresPendentes o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ControleValoresPendentes.class).getResultList();
    }

	public static ControleValoresPendentes findControleValoresPendentes(Long id) {
        if (id == null) return null;
        return entityManager().find(ControleValoresPendentes.class, id);
    }

	public static List<ControleValoresPendentes> findControleValoresPendentesEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ControleValoresPendentes o", ControleValoresPendentes.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<ControleValoresPendentes> findControleValoresPendentesEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ControleValoresPendentes o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ControleValoresPendentes.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            ControleValoresPendentes attached = ControleValoresPendentes.findControleValoresPendentes(this.id);
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
    public ControleValoresPendentes merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ControleValoresPendentes merged = this.entityManager.merge(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), merged.getId(), ControleValoresPendentes.class.getSimpleName());
        this.entityManager.flush();
        return merged;
    }
	
	public Double TotalContasPendentes(Date data,Turno turno, int status){
		double valor = 0;
		Query query = entityManager().createQuery("SELECT SUM(o.dinheiro) FROM ControleValoresPendentes o WHERE data=:data and status="+status+"and loja=:loja and turno="+turno.ordinal(), Double.class);
		query.setParameter("data", data);
		query.setParameter("loja", ObejctSession.loja());
		
		Query query1 = entityManager().createQuery("SELECT SUM(o.trocado) FROM ControleValoresPendentes o WHERE data=:data and status="+status+"and loja=:loja and turno="+turno.ordinal(), Double.class);
		query1.setParameter("data", data);
		query1.setParameter("loja", ObejctSession.loja());
		
		Query query2 = entityManager().createQuery("SELECT SUM(o.moeda) FROM ControleValoresPendentes o WHERE data=:data and status="+status+"and loja=:loja and turno="+turno.ordinal(), Double.class);
		query2.setParameter("data", data);
		query2.setParameter("loja", ObejctSession.loja());
		
		Query query3 = entityManager().createQuery("SELECT SUM(o.cheque) FROM ControleValoresPendentes o WHERE data=:data and status="+status+"and loja=:loja and turno="+turno.ordinal(), Double.class);
		query3.setParameter("data", data);
		query3.setParameter("loja", ObejctSession.loja());
		
		Query query4 = entityManager().createQuery("SELECT SUM(o.cartao) FROM ControleValoresPendentes o WHERE data=:data and status="+status+"and loja=:loja and turno="+turno.ordinal(), Double.class);
		query4.setParameter("data", data);
		query4.setParameter("loja", ObejctSession.loja());
		try {
			valor += (double) query.getSingleResult();
			valor += (double) query1.getSingleResult();
			valor += (double) query2.getSingleResult();
			valor += (double) query3.getSingleResult();
			valor += (double) query4.getSingleResult();
		} catch(NullPointerException e) {
			
		}
       	return valor;
	}
	
	public List<ControleValoresPendentes> ContasPendentes(Date data,Turno turno, int tipoPagamento){
		Query query = entityManager().createQuery("SELECT o FROM ControleValoresPendentes o WHERE data=:data and tipoPagamento="+tipoPagamento+"and loja=:loja and turno="+turno.ordinal(), ControleValoresPendentes.class);
		query.setParameter("data", data);
		query.setParameter("loja", ObejctSession.loja());
       	return query.getResultList();
	}
	
	public Double TotalBaixadoDinheiro(){
		Query query = entityManager().createQuery("select sum(o.dinheiro) from ControleValoresPendentes o where o.status=2 and o.loja=:loja", Double.class);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public Double TotalBaixadoTrocado(){
		Query query = entityManager().createQuery("select sum(o.trocado) from ControleValoresPendentes o where o.status=2 and o.loja=:loja", Double.class);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public Double totalBaixadoTrocado(Date dataInicial, Date dataFinal){
		Query query = entityManager().createQuery("select sum(o.trocado) from ControleValoresPendentes o where o.data between :dataInicial and :dataFinal and o.status=2 and o.loja=:loja", Double.class);
		query.setParameter("dataIncial", dataInicial);
		query.setParameter("dataIncial", dataFinal);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public Double TotalBaixadoMoeda(){
		Query query = entityManager().createQuery("select sum(o.moeda) from ControleValoresPendentes o where o.status=2 and o.loja=:loja", Double.class);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public static List<ControleValoresPendentes> findByControleValores(ControleValoresPendentes controlePendentes, Date dataFinal){	
		CriteriaBuilder cb = entityManager().getCriteriaBuilder();
		CriteriaQuery<ControleValoresPendentes> c = cb.createQuery(ControleValoresPendentes.class);
		Root<ControleValoresPendentes> root = c.from(ControleValoresPendentes.class);
		c.select(root);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Path<Date> data = root.get("data");
		predicates.add(cb.and(cb.between(data, controlePendentes.data, dataFinal)));
		
		/*if(controlePendentes.data != null) {
			Path<Date> data = root.get("data");
			predicates.add(cb.and(cb.equal(data, controlePendentes.data)));
		}*/
				
		if(controlePendentes.turno != null) { 
			Path<String> turno = root.get("turno");
			predicates.add(cb.and(cb.equal(turno, controlePendentes.turno)));
		}
		
		if(controlePendentes.motoqueiro != null) {
			Path<String> motoqueiro = root.get("motoqueiro");
			predicates.add(cb.and(cb.equal(motoqueiro, controlePendentes.motoqueiro)));
		}
		 
		if(controlePendentes.status != null) {
			Path<String> status = root.get("status");
			predicates.add(cb.and(cb.equal(status, controlePendentes.status)));
		}
		
		/*Path<String> baixado = root.get("baixado");
		predicates.add(cb.and(cb.equal(baixado, false)));*/
				
		Path<String> loja = root.get("loja");
		predicates.add(cb.and(cb.equal(loja, ObejctSession.idLoja())));
			
		c.select(root).where(predicates.toArray(new Predicate[]{}));
		List<ControleValoresPendentes> listaControles = entityManager().createQuery(c).getResultList();
		return listaControles;
	}
	
}