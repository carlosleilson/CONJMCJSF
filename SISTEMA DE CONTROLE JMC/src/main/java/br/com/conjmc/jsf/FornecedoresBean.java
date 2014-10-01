package br.com.conjmc.jsf;
import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.cadastrobasico.Fornecedores;
import br.com.conjmc.jsf.converter.DespesasConverter;
import br.com.conjmc.jsf.util.MessageFactory;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.faces.validator.LengthValidator;

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

@ManagedBean(name = "fornecedoresBean")
@SessionScoped
@Configurable
public class FornecedoresBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Fornecedores fornecedores;
	
	private List<Despesas> allDespesasAtivas;
	private List<Fornecedores> allFornecedoreses;

	@PostConstruct
    public void init() {
        findAllFornecedoreses();
        findAllDespesasAtivas();
    }
	
	private void findAllDespesasAtivas() {
		setAllDespesasAtivas(Despesas.findAllDespesasAtivas());	
	}
	
	public List<Fornecedores> getAllFornecedoreses() {
        return allFornecedoreses;
    }

	public void setAllFornecedoreses(List<Fornecedores> allFornecedoreses) {
        this.allFornecedoreses = allFornecedoreses;
    }

	public String findAllFornecedoreses() {
        allFornecedoreses = Fornecedores.findAllFornecedoreses();
        return null;
    }

	public Fornecedores getFornecedores() {
        if (fornecedores == null) {
            fornecedores = new Fornecedores();
        }
        return fornecedores;
    }

	public void setFornecedores(Fornecedores fornecedores) {
        this.fornecedores = fornecedores;
    }

	public List<Despesas> completeProdutosFornecidos(String query) {
        List<Despesas> suggestions = new ArrayList<Despesas>();
        for (Despesas despesas : Despesas.findAllDespesases()) {
            String despesasStr = String.valueOf(despesas.getCodigo() +  " "  + despesas.getDescricao() +  " "  + despesas.getIdResumo());
            if (despesasStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(despesas);
            }
        }
        return suggestions;
    }

	public String persist() {
        String message = "";
        if (fornecedores.getId() != null) {
            fornecedores.merge();
            message = "message_successfully_updated";
        } else {
            fornecedores.persist();
            message = "message_successfully_created";
        }
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Fornecedores");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        findAllFornecedoreses();
        return "/pages/fornecedor.xhtml";
    }

	public String delete() {
        fornecedores.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Fornecedores");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllFornecedoreses();
    }

	public void reset() {
        fornecedores = null;
    }

	public List<Despesas> getAllDespesasAtivas() {
		return allDespesasAtivas;
	}

	public void setAllDespesasAtivas(List<Despesas> allDespesasAtivas) {
		this.allDespesasAtivas = allDespesasAtivas;
	}

}
