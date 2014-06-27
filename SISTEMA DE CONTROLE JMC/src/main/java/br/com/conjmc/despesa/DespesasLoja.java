package br.com.conjmc.despesa;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Calendar;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import br.com.conjmc.cadastrobasico.Despesas;
import javax.persistence.ManyToOne;
import br.com.conjmc.cadastrobasico.Despesa_Gastos;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findDespesasLojasByMes_anoEquals", "findDespesasLojasByItem" })
public class DespesasLoja {

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "MM/yyyy")
    private Calendar mes_ano;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd")
    private Calendar dia;

    /**
     */
    @NotNull
    private Double valor;

    /**
     */
    @ManyToOne
    private Despesas classificacao;

    /**
     */
    @ManyToOne
    private Despesa_Gastos item;
}
