package br.com.conjmc.controlediario.fechamento;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.conjmc.cadastrobasico.Faturamento;
import br.com.conjmc.cadastrobasico.MetaData;
import br.com.conjmc.cadastrobasico.Motoqueiros;
import br.com.conjmc.jsf.util.ObejctSession;

import javax.persistence.ManyToOne;

@Entity
@Configurable
public class Cobrar {

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yy-hh")
    private Calendar periodo;

    /**
     */
    @NotNull
    @ManyToOne
    private Motoqueiros motoqueiro;

    /**
     */
    @NotNull
    private String numeroPedido;

    /**
     */
    @NotNull
    private String telefone;

    /**
     */
    @NotNull
    private Double valor;

    /**
     */
    @NotNull
    private String tipoPagamento;

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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("periodo", "motoqueiro", "numeroPedido", "telefone", "valor", "tipoPagamento");

	public static final EntityManager entityManager() {
        EntityManager em = new Cobrar().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCobrars() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Cobrar o", Long.class).getSingleResult();
    }

	public static List<Cobrar> findAllCobrars() {
        return entityManager().createQuery("SELECT o FROM Cobrar o", Cobrar.class).getResultList();
    }

	public static List<Cobrar> findAllCobrars(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Cobrar o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Cobrar.class).getResultList();
    }

	public static Cobrar findCobrar(Long id) {
        if (id == null) return null;
        return entityManager().find(Cobrar.class, id);
    }

	public static List<Cobrar> findCobrarEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Cobrar o", Cobrar.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Cobrar> findCobrarEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Cobrar o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Cobrar.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Cobrar attached = Cobrar.findCobrar(this.id);
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
    public Cobrar merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Cobrar merged = this.entityManager.merge(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), merged.getId(), Cobrar.class.getSimpleName());
        this.entityManager.flush();
        return merged;
    }

	public Calendar getPeriodo() {
        return this.periodo;
    }

	public void setPeriodo(Calendar periodo) {
        this.periodo = periodo;
    }

	public Motoqueiros getMotoqueiro() {
        return this.motoqueiro;
    }

	public void setMotoqueiro(Motoqueiros motoqueiro) {
        this.motoqueiro = motoqueiro;
    }

	public String getNumeroPedido() {
        return this.numeroPedido;
    }

	public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

	public String getTelefone() {
        return this.telefone;
    }

	public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

	public Double getValor() {
        return this.valor;
    }

	public void setValor(Double valor) {
        this.valor = valor;
    }

	public String getTipoPagamento() {
        return this.tipoPagamento;
    }

	public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
