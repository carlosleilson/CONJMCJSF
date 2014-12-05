package br.com.conjmc.cadastrobasico;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	
	@ManyToOne
    private Lojas loja;

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
	
}
