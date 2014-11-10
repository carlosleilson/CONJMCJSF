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
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.cadastrobasico.MetaData;

import javax.persistence.ManyToOne;

import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.ObejctSession;

@Entity
@Configurable
public class Diaria {

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
    private Funcionarios caixaResponsavel;

    /**
     */
    private Double valorBalcao;

    /**
     */
    private Integer qtdeValorBalcao;

    /**
     */
    private Double valorWeb;

    /**
     */
    private Integer qtdeValorWeb;

    /**
     */
    private Double valorCentral;

    /**
     */
    private Integer qtdeValorCentral;

    /**
     */
    private Double valorMesa;

    /**
     */
    private Integer qtdeValorMesa;

    /**
     */
    private Double valorTaxaEntrega;

    /**
     */
    private Integer qtdeValorTaxaEntrega;

    /**
     */
    private Double valorSevicoMesa;

    /**
     */
    private Integer qtdeValorSevicoMesa;

    /**
     */
    private Double valorTotalFita;

    /**
     */
    private Double caixaInicial;

    /**
     */
    @ManyToOne
    private Sangria trocado;

    /**
     */
    private Double sangriaCaixa;

    /**
     */
    private Double cartaoDebito;

    /**
     */
    private Double cartaoCredito;

    /**
     */
    private Double cartaoTicket;

    /**
     */
    private Double cheque;

    /**
     */
    @ManyToOne
    private Receber contaReceber;

    /**
     */
    @ManyToOne
    private Cobrar contaPagar;

    /**
     */
    private Double balcao;

    /**
     */
    private Double caixaFinal;

    /**
     */
    private Double totalFechamento;

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Calendar getPeriodo() {
        return this.periodo;
    }

	public void setPeriodo(Calendar periodo) {
        this.periodo = periodo;
    }

	public Funcionarios getCaixaResponsavel() {
        return this.caixaResponsavel;
    }

	public void setCaixaResponsavel(Funcionarios caixaResponsavel) {
        this.caixaResponsavel = caixaResponsavel;
    }

	public Double getValorBalcao() {
        return this.valorBalcao;
    }

	public void setValorBalcao(Double valorBalcao) {
        this.valorBalcao = valorBalcao;
    }

	public Integer getQtdeValorBalcao() {
        return this.qtdeValorBalcao;
    }

	public void setQtdeValorBalcao(Integer qtdeValorBalcao) {
        this.qtdeValorBalcao = qtdeValorBalcao;
    }

	public Double getValorWeb() {
        return this.valorWeb;
    }

	public void setValorWeb(Double valorWeb) {
        this.valorWeb = valorWeb;
    }

	public Integer getQtdeValorWeb() {
        return this.qtdeValorWeb;
    }

	public void setQtdeValorWeb(Integer qtdeValorWeb) {
        this.qtdeValorWeb = qtdeValorWeb;
    }

	public Double getValorCentral() {
        return this.valorCentral;
    }

	public void setValorCentral(Double valorCentral) {
        this.valorCentral = valorCentral;
    }

	public Integer getQtdeValorCentral() {
        return this.qtdeValorCentral;
    }

	public void setQtdeValorCentral(Integer qtdeValorCentral) {
        this.qtdeValorCentral = qtdeValorCentral;
    }

	public Double getValorMesa() {
        return this.valorMesa;
    }

	public void setValorMesa(Double valorMesa) {
        this.valorMesa = valorMesa;
    }

	public Integer getQtdeValorMesa() {
        return this.qtdeValorMesa;
    }

	public void setQtdeValorMesa(Integer qtdeValorMesa) {
        this.qtdeValorMesa = qtdeValorMesa;
    }

	public Double getValorTaxaEntrega() {
        return this.valorTaxaEntrega;
    }

	public void setValorTaxaEntrega(Double valorTaxaEntrega) {
        this.valorTaxaEntrega = valorTaxaEntrega;
    }

	public Integer getQtdeValorTaxaEntrega() {
        return this.qtdeValorTaxaEntrega;
    }

	public void setQtdeValorTaxaEntrega(Integer qtdeValorTaxaEntrega) {
        this.qtdeValorTaxaEntrega = qtdeValorTaxaEntrega;
    }

	public Double getValorSevicoMesa() {
        return this.valorSevicoMesa;
    }

	public void setValorSevicoMesa(Double valorSevicoMesa) {
        this.valorSevicoMesa = valorSevicoMesa;
    }

	public Integer getQtdeValorSevicoMesa() {
        return this.qtdeValorSevicoMesa;
    }

	public void setQtdeValorSevicoMesa(Integer qtdeValorSevicoMesa) {
        this.qtdeValorSevicoMesa = qtdeValorSevicoMesa;
    }

	public Double getValorTotalFita() {
        return this.valorTotalFita;
    }

	public void setValorTotalFita(Double valorTotalFita) {
        this.valorTotalFita = valorTotalFita;
    }

	public Double getCaixaInicial() {
        return this.caixaInicial;
    }

	public void setCaixaInicial(Double caixaInicial) {
        this.caixaInicial = caixaInicial;
    }

	public Sangria getTrocado() {
        return this.trocado;
    }

	public void setTrocado(Sangria trocado) {
        this.trocado = trocado;
    }

	public Double getSangriaCaixa() {
        return this.sangriaCaixa;
    }

	public void setSangriaCaixa(Double sangriaCaixa) {
        this.sangriaCaixa = sangriaCaixa;
    }

	public Double getCartaoDebito() {
        return this.cartaoDebito;
    }

	public void setCartaoDebito(Double cartaoDebito) {
        this.cartaoDebito = cartaoDebito;
    }

	public Double getCartaoCredito() {
        return this.cartaoCredito;
    }

	public void setCartaoCredito(Double cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

	public Double getCartaoTicket() {
        return this.cartaoTicket;
    }

	public void setCartaoTicket(Double cartaoTicket) {
        this.cartaoTicket = cartaoTicket;
    }

	public Double getCheque() {
        return this.cheque;
    }

	public void setCheque(Double cheque) {
        this.cheque = cheque;
    }

	public Receber getContaReceber() {
        return this.contaReceber;
    }

	public void setContaReceber(Receber contaReceber) {
        this.contaReceber = contaReceber;
    }

	public Cobrar getContaPagar() {
        return this.contaPagar;
    }

	public void setContaPagar(Cobrar contaPagar) {
        this.contaPagar = contaPagar;
    }

	public Double getBalcao() {
        return this.balcao;
    }

	public void setBalcao(Double balcao) {
        this.balcao = balcao;
    }

	public Double getCaixaFinal() {
        return this.caixaFinal;
    }

	public void setCaixaFinal(Double caixaFinal) {
        this.caixaFinal = caixaFinal;
    }

	public Double getTotalFechamento() {
        return this.totalFechamento;
    }

	public void setTotalFechamento(Double totalFechamento) {
        this.totalFechamento = totalFechamento;
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

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("periodo", "caixaResponsavel", "valorBalcao", "qtdeValorBalcao", "valorWeb", "qtdeValorWeb", "valorCentral", "qtdeValorCentral", "valorMesa", "qtdeValorMesa", "valorTaxaEntrega", "qtdeValorTaxaEntrega", "valorSevicoMesa", "qtdeValorSevicoMesa", "valorTotalFita", "caixaInicial", "trocado", "sangriaCaixa", "cartaoDebito", "cartaoCredito", "cartaoTicket", "cheque", "contaReceber", "contaPagar", "balcao", "caixaFinal", "totalFechamento");

	public static final EntityManager entityManager() {
        EntityManager em = new Diaria().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countDiarias() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Diaria o", Long.class).getSingleResult();
    }

	public static List<Diaria> findAllDiarias() {
        return entityManager().createQuery("SELECT o FROM Diaria o", Diaria.class).getResultList();
    }

	public static List<Diaria> findAllDiarias(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Diaria o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Diaria.class).getResultList();
    }

	public static Diaria findDiaria(Long id) {
        if (id == null) return null;
        return entityManager().find(Diaria.class, id);
    }

	public static List<Diaria> findDiariaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Diaria o", Diaria.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<Diaria> findDiariaEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Diaria o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Diaria.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Diaria attached = Diaria.findDiaria(this.id);
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
    public Diaria merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Diaria merged = this.entityManager.merge(this);
        MetaData.gravarMetadata(ObejctSession.getUsuarioLogado(), merged.getId(), Diaria.class.getSimpleName());
        this.entityManager.flush();
        return merged;
    }
}
