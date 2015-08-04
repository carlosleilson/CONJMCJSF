package br.com.conjmc.cadastrobasico;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.jsf.util.ObejctSession;

@Entity
@Configurable
public class ValoresExtra {

	@Id @GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	private Double valor;
	private String tipoValor;
	
	private String Descricao;
	
	@ManyToOne
	private Lojas loja;
	
	//Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getTipoValor() {
		return tipoValor;
	}
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}
	
	public String getDescricao() {
		return Descricao;
	}
	public void setDescricao(String descricao) {
		Descricao = descricao;
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
        EntityManager em = new ValoresExtra().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
	
	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), this.id, this.getClass().getSimpleName());
    }
	
	public List<ValoresExtra> findAllValores() {
		Query query = entityManager().createQuery("SELECT o FROM ValoresExtra o where loja=:loja order by data desc", ValoresExtra.class);
		query.setParameter("loja", ObejctSession.loja());
		return query.getResultList();
    }
	
	public Double TotalValorExtra(String tipoPagamento){
		Query query = entityManager().createQuery("SELECT SUM(o.valor) FROM ValoresExtra o WHERE loja=:loja and tipoValor=:tipoValor", Double.class);
		query.setParameter("loja", ObejctSession.loja());
		query.setParameter("tipoValor", tipoPagamento);
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
}
