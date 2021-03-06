package br.com.conjmc.controlediario.controlesaida;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.cadastrobasico.Contas;
import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.cadastrobasico.MetaData;
import br.com.conjmc.cadastrobasico.Turno;
import br.com.conjmc.cadastrobasico.Usuarios;
import br.com.conjmc.despesa.DespesasLoja;
import br.com.conjmc.jsf.util.DataUltil;
import br.com.conjmc.jsf.util.ObejctSession;

@Configurable
@Entity
public class Sangria implements Serializable{

	private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.DATE)
    private Date periodo;

    @NotNull
    private Double valor;

    private String origem;
    
    @NotNull
    private boolean sangria;
    
    /*private String turno;*/

    @ManyToOne
    private DespesasGastos item;
    
    @ManyToOne
    private Despesas classificacao;

    @ManyToOne
    private Funcionarios funcionario;

    @ManyToOne
    private Lojas loja;
    
    @ManyToOne
    @JoinColumn(name="conta_id")
    private Contas conta;
    
    private String descricao;
    
    // Pagamento
	@Enumerated
	private Turno turno;	
    
    private String detalhamento;
	
	private String detalhamentoBanco;
	
	private String tipoPagamento;
	
	private String banco;

    
    //Generate Getters and Setters
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Date getPeriodo() {
        return this.periodo;
    }

	public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

	public Double getValor() {
        return this.valor;
    }

	public void setValor(Double valor) {
        this.valor = valor;
    }

	public String getOrigem() {
        return this.origem;
    }

	public void setOrigem(String origem) {
        this.origem = origem;
    }

	public DespesasGastos getItem() {
        return this.item;
    }

	public void setItem(DespesasGastos item) {
        this.item = item;
    }

	public Funcionarios getFuncionario() {
        return this.funcionario;
    }

	public void setFuncionario(Funcionarios funcionario) {
        this.funcionario = funcionario;
    }

	public boolean isSangria() {
		return sangria;
	}

	public void setSangria(boolean sangria) {
		this.sangria = sangria;
	}

	/*public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}*/

	public Contas getConta() {
		return conta;
	}

	public void setConta(Contas conta) {
		this.conta = conta;
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

	public String getDetalhamento() {
		return detalhamento;
	}

	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}

	public String getDetalhamentoBanco() {
		return detalhamentoBanco;
	}

	public void setDetalhamentoBanco(String detalhamentoBanco) {
		this.detalhamentoBanco = detalhamentoBanco;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sangria other = (Sangria) obj;
		if (classificacao == null) {
			if (other.classificacao != null)
				return false;
		} else if (!classificacao.equals(other.classificacao))
			return false;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		} else if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (origem == null) {
			if (other.origem != null)
				return false;
		} else if (!origem.equals(other.origem))
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
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	
	
	// DAO
	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("periodo", "valor", "origem", "item", "classificacao", "funcionario","sangria");

	public static final EntityManager entityManager() {
        EntityManager em = new Sangria().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countSangrias() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Sangria o", Long.class).getSingleResult();
    }
	
	public static long countSangriaValidation(Sangria sangria) {
        Query query = entityManager().createQuery("SELECT COUNT(o) FROM Sangria o where o.periodo=:data and o.valor=:valor and o.item.descrisao=:descricao and o.loja.id = :loja", Long.class);
        query.setParameter("data", sangria.periodo);
        query.setParameter("valor", sangria.valor);
        query.setParameter("descricao", sangria.getItem().getDescrisao());
        query.setParameter("loja", ObejctSession.idLoja());
        return (long) query.getSingleResult();
    }

	public static List<Sangria> findAllSangrias() {
		Usuarios usuarioLogado = (Usuarios) ObejctSession.getObjectSession("usuarioLogado");
		String sql;
		if(usuarioLogado.getPerfil().getLabel() == "Administrador") {
			sql = "select o from Sangria o where o.loja.id = :loja and o.periodo != null order by o.periodo desc";
		} else {
			sql = "select o from Sangria o where o.loja.id = :loja and o.periodo != null and o.item.descrisao!='Pró-labore' order by o.periodo desc";
		}
		Query query = entityManager().createQuery(sql, Sangria.class);
		query.setParameter("loja", ObejctSession.idLoja());
		query.setMaxResults(50);
        return query.getResultList();
    }
	
	public static List<Sangria> findAllSangriasAtivas() {
		Query query = entityManager().createQuery("SELECT o FROM Sangria o where o.sangria = true and o.loja.id = :loja and o.periodo != null", Sangria.class);
		query.setParameter("loja", ObejctSession.idLoja());
		return query.getResultList();
    }

	public static Sangria findSangria(Long id) {
        if (id == null) return null;
        return entityManager().find(Sangria.class, id);
    }
	
	public static Sangria encontarFuncionarioPorItens(Funcionarios funcionario, DespesasGastos item){
		 List<Sangria> despesas = entityManager().createQuery("SELECT o FROM Sangria o where o.funcionario.id=:funcionarioId and o.item.id=:itemId and o.loja.id=:loja", Sangria.class)
		.setParameter("funcionarioId", funcionario.getId())
		.setParameter("itemId", item.getId())
		.setParameter("loja", ObejctSession.idLoja())
		.getResultList();
		for (Sangria despesa : despesas) {
			return despesa;
		}
		return new Sangria();
	}
	
	public static Sangria encontarFuncionarioPorItemData(Funcionarios funcionario, DespesasGastos item, Date dataTmp){
		 return entityManager().createQuery("SELECT o FROM Sangria o where o.periodo between :dataInicial and :dataFinal and o.funcionario.id=:funcionarioId and o.item.id=:itemId and o.loja.id=:loja", Sangria.class)
		.setParameter("funcionarioId", funcionario.getId())
		.setParameter("itemId", item.getId())
        .setParameter("dataInicial", DataUltil.primeiroDiaMes(dataTmp))
        .setParameter("dataFinal", DataUltil.ultimoDiaMes(dataTmp))		
		.setParameter("loja", ObejctSession.idLoja())
		.getSingleResult() ;
	}	
	
	public static List<Sangria> findSangriaConta(Contas conta) {
		Query query = entityManager().createQuery("SELECT o FROM Sangria o where o.conta = :conta", Sangria.class);
		query.setParameter("conta", conta);
        return query.getResultList();
    }

	//Query do spring roo
	public static List<Sangria> findSangriaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Sangria o", Sangria.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	//Query do spring roo
	public static List<Sangria> findSangriaEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Sangria o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Sangria.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Sangria attached = Sangria.findSangria(this.id);
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
    public Sangria merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Sangria merged = this.entityManager.merge(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), merged.getId(), Sangria.class.getSimpleName());
        this.entityManager.flush();
        return merged;
    }

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

	public Despesas getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Despesas classificacao) {
		this.classificacao = classificacao;
	}
	
	public static List<Sangria> encontrarPorData(Date dataInicial, Date dataFinal,DespesasGastos item) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (dataInicial == null) throw new IllegalArgumentException("O Dia � obrigatorio");
        if (dataFinal == null) throw new IllegalArgumentException("O At� Mes/ano � obrigatorio");
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery<Sangria> q = null;
        if(item!=null){
            q = em.createQuery("SELECT o FROM Sangria AS o WHERE o.periodo between :dataInicial and :dataFinal and o.item = :item and o.loja.id = :loja", Sangria.class);
            q.setParameter("item", item);
            q.setParameter("loja", ObejctSession.idLoja());
        }else{	
        	q = em.createQuery("SELECT o FROM Sangria AS o WHERE o.periodo between :dataInicial and :dataFinal and o.loja.id = :loja", Sangria.class);
        	q.setParameter("loja", ObejctSession.idLoja());
        }
        q.setParameter("dataInicial", dataInicial );
        q.setParameter("dataFinal", dataFinal);
        q.setParameter("loja", ObejctSession.idLoja());
        //return (q.getResultList().isEmpty()? findAllDespesasLojas():q.getResultList());
        return q.getResultList();
    }
	
	public static List< Sangria > paginaPorMes(Date data, Long id) {
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery<Sangria> q = null;
        q = em.createQuery("SELECT o FROM Sangria AS o WHERE o.periodo between :dataInicial and :dataFinal and o.item.id = :item and o.item.naoDespesa=false and o.loja.id = :loja", Sangria.class);
       	q.setParameter("item", id);
        q.setParameter("dataInicial", DataUltil.primeiroDiaMes(data));
        q.setParameter("dataFinal", DataUltil.ultimoDiaMes(data));
        q.setParameter("loja", ObejctSession.idLoja());
        return q.getResultList();
    }

	public static List< Sangria > paginaPorMes(Date data) {
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery<Sangria> q = null;
        if(ObejctSession.getUsuarioLogado().getPerfil().getLabel().equals("Administrador")){
        	q = em.createQuery("SELECT o FROM Sangria AS o WHERE o.periodo between :dataInicial and :dataFinal and o.item.naoDespesa=false and o.loja.id = :loja", Sangria.class);
        } else {
        	q = em.createQuery("SELECT o FROM Sangria AS o WHERE o.periodo between :dataInicial and :dataFinal and o.item.naoDespesa=false and o.loja.id = :loja and o.item.classificacao.descricao!='Pró-labore'", Sangria.class);      	
        }
        q.setParameter("dataInicial", DataUltil.primeiroDiaMes(data));
        q.setParameter("dataFinal", DataUltil.ultimoDiaMes(data));
        q.setParameter("loja", ObejctSession.idLoja());
        return q.getResultList();
    }
	
	public static List< Sangria > findSangriaByItens(Long id) {
        if (id == null) return null;
		 Query query = entityManager().createQuery("SELECT o FROM Sangria o where o.item.id = :item and o.loja.id = :loja", Sangria.class);
		 query.setParameter("item", id);
		 query.setParameter("loja", ObejctSession.idLoja());
        return (List< Sangria >)query.getResultList();
	}
	
	public static List< Sangria > findSangriaByConta(Long id) {
        if (id == null) return null;
		 Query query = entityManager().createQuery("SELECT o FROM Sangria o where o.conta.id = :item and o.loja.id = :loja", Sangria.class);
		 query.setParameter("item", id);
		 query.setParameter("loja", ObejctSession.idLoja());
        return (List< Sangria >)query.getResultList();
	}
	
	//Soma para o faturamento
	public Double TotalDespesa(Date data,Turno turno, String origem, boolean sangria){
		Query query = entityManager().createQuery("SELECT SUM(o.valor) FROM Sangria o WHERE periodo=:data and origem=:origem and loja=:loja and turno="+turno.ordinal(), Double.class);
		query.setParameter("data", data);
		query.setParameter("origem", origem);
		//query.setParameter("sangria", sangria);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
	public Double TotalSangiraCofre(){
		Query query = entityManager().createQuery("SELECT SUM(o.valor) FROM Sangria o WHERE origem='SANGRIA COFRE' and loja=:loja", Double.class);
		query.setParameter("loja", ObejctSession.loja());
		double valor;
		try {
			valor = (double) query.getSingleResult(); 
		} catch(NullPointerException e) {
			valor = 0;
		}
       	return valor;
	}
	
}
