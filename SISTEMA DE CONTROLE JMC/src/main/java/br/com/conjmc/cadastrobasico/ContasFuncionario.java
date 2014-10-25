package br.com.conjmc.cadastrobasico;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.ObejctSession;

@Entity
@Configurable
public class ContasFuncionario implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	/**
     */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	/**
     */
	@NotNull
	@ManyToOne
	private Lojas loja;
	
	/**
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date periodo;
    
	/**
     */
    @NotNull
    @ManyToOne
	private Funcionarios funcionario;

    /**
     */
    @ManyToOne
    private DespesasGastos item;
    
	/**
     */
    @NotNull
	private Double valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lojas getLoja() {
		return loja;
	}

	public void setLoja(Lojas loja) {
		this.loja = loja;
	}

	public Date getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

	public Funcionarios getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}

	public DespesasGastos getItem() {
		return item;
	}

	public void setItem(DespesasGastos item) {
		this.item = item;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}	

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("loja", "periodo", "funcionario", "item", "valor");
	
	public static final EntityManager entityManager() {
        //EntityManager em = new  ContasFuncionario().entityManager;
		 EntityManager em = new Sangria().entityManager();
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
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
            ContasFuncionario attached = ContasFuncionario.findContasFuncionario(this.id);
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
    public ContasFuncionario merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ContasFuncionario merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }	

	public static ContasFuncionario findContasFuncionario(Long id) {
        if (id == null) return null;
        return entityManager().find(ContasFuncionario.class, id);
    }
	
	public static List<Funcionarios> encontraTodasFuncionarios() {
        EntityManager em = entityManager();
        Query query = entityManager().createQuery("SELECT o FROM Funcionarios o where o.situacao = true and o.loja.id = :loja", Funcionarios.class);
      	query.setParameter("loja", ObejctSession.idLoja());
        return query.getResultList();
    }	
	
	public static List<ContasFuncionario> encontraTodasContaFuncionario() {
        EntityManager em = entityManager();
        Query query = em.createQuery("SELECT o FROM ContasFuncionario o WHERE o.loja.id = :loja and o.funcionario is not null", ContasFuncionario.class);
      	query.setParameter("loja", ObejctSession.idLoja());
        return query.getResultList();
    }
	
	public static List<ContasFuncionario> encontraContaFuncionario(Date dataInicial, Date dataFinal, Funcionarios funcionario) {
        EntityManager em = entityManager();
        Query query = entityManager().createQuery("SELECT o FROM ContasFuncionario o WHERE o.periodo between :dataInicial and :dataFinal or o.funcionario = :funcionario and o.loja.id = :loja and o.funcionario is not null", ContasFuncionario.class);
		query.setParameter("dataInicial", dataInicial );
		query.setParameter("dataFinal", dataFinal);
		query.setParameter("funcionario", funcionario);
		query.setParameter("loja", ObejctSession.idLoja());
        return query.getResultList();
    }	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((periodo == null) ? 0 : periodo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContasFuncionario other = (ContasFuncionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (periodo == null) {
			if (other.periodo != null)
				return false;
		} else if (!periodo.equals(other.periodo))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}	
}
