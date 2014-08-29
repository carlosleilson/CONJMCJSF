package br.com.conjmc.jsf;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.cadastrobasico.Faturamento;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.converter.DespesasGastosConverter;
import br.com.conjmc.jsf.converter.FuncionariosConverter;
import br.com.conjmc.jsf.util.DataUltil;
import br.com.conjmc.jsf.util.MessageFactory;

@ManagedBean(name = "faturamentoBean")
@Configurable
public class FaturamentoBean implements Serializable  {

	private static final long serialVersionUID = 1L;
	private Faturamento faturamento;
	private List<Faturamento> faturamentos;
	@PostConstruct
    public void init() {
		faturamento = new Faturamento();
		carregaFaturamento();
    }
	
	private void carregaFaturamento() {
		faturamentos = Faturamento.findAllMFaturamentoes();
	}
	public Faturamento getFaturamento() {
		return faturamento;
	}
	public void setFaturamento(Faturamento faturamento) {
		this.faturamento = faturamento;
	}
	
	public String persist() {
        String message = "";
        if (faturamento.getId() != null) {
            faturamento.merge();
            message = "message_successfully_updated";
        } else {
            faturamento.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Despesas");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "/pages/faturamento.xhtml";
    }

	public List<Faturamento> getFaturamentos() {
		return faturamentos;
	}

	public void setFaturamentos(List<Faturamento> faturamentos) {
		this.faturamentos = faturamentos;
	}
	
}