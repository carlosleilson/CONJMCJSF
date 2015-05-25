package br.com.conjmc.controlediario.controlesaida;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.cadastrobasico.MetaData;
import br.com.conjmc.cadastrobasico.Turno;
import br.com.conjmc.jsf.util.ObejctSession;

@Configurable
@Entity
public class Sangria2015 {
	
	@Id @GeneratedValue
	private Long id;
	private String valor;
	private String origem;
	private String tipo;
	private String descricao;
	private boolean troco;
	
	@Enumerated
	private Turno turno;
	
	@ManyToOne
	private Lojas loja;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	//Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	public boolean isTroco() {
		return troco;
	}
	public void setTroco(boolean troco) {
		this.troco = troco;
	}
	
	public Lojas getLoja() {
		return loja;
	}

	public void setLoja(Lojas loja) {
		this.loja = loja;
	}

	public Turno getTurno() {
		return turno;
	}
	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	//DAO
	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Sangria2015().entityManager;
        if (em == null) throw new IllegalStateException("Entity não pode ser enjetado)");
        return em;
    }
	
	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), this.id, this.getClass().getSimpleName());
    }
	
	@Transactional
    public Sangria2015 merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Sangria2015 merged = this.entityManager.merge(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), merged.getId(), Sangria.class.getSimpleName());
        this.entityManager.flush();
        return merged;
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
	
	public static List<Sangria2015> findAllSangrias() {
		Query query = entityManager().createQuery("select o from Sangria2015 o where o.loja=:loja order by o.id desc", Sangria2015.class);
		query.setParameter("loja", ObejctSession.loja());
		query.setMaxResults(50);
        return query.getResultList();
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
	
	public Double TotalCaixaInicial(Date data, String tipo, Turno turno){
		Query query = entityManager().createQuery("SELECT SUM(o.valor) FROM Sangria2015 o WHERE o.data=:data and o.origem='SANGRIA COFRE' and tipo=:tipo and troco=true and loja=:loja and turno="+turno.ordinal(), String.class);
		query.setParameter("data", data);
		query.setParameter("tipo", tipo);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = Double.parseDouble((String) query.getSingleResult()); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public Double TotalSangriaCaixa(Date data, Turno turno) {
		Query query = entityManager().createQuery("SELECT SUM(o.valor) FROM Sangria2015 o WHERE o.data=:data and o.origem='SANGRIA CAIXA' and loja=:loja and turno="+turno.ordinal(), String.class);
		query.setParameter("data", data);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = Double.parseDouble((String) query.getSingleResult()); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public Double TotalSangira2015Cofre(String origem){
		Query query = entityManager().createQuery("SELECT SUM(o.valor) FROM Sangria2015 o WHERE origem=:origem and loja=:loja", String.class);
		query.setParameter("origem", origem);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = Double.parseDouble((String) query.getSingleResult()); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
}
