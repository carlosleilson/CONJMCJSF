package br.com.conjmc.jsf;
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

import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.jsf.converter.DespesasConverter;
import br.com.conjmc.jsf.util.MessageFactory;

@ManagedBean(name = "despesasGastosBean")
@SessionScoped
@Configurable
public class DespesasGastosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = "DespesasGastoses";

	private DespesasGastos despesasGastos;

	private List<DespesasGastos> allItensPessoalAtivos;
	
	private List<DespesasGastos> allDespesasGastosAtivos;
	
	private List<DespesasGastos> allDespesasGastosClassificacao;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("descrisao");
        despesasGastos = new DespesasGastos();
        despesasGastos.setDespesaPessoal(false);
        despesasGastos.setSituacao(true);
        despesasGastos.setSalario(false);
        despesasGastos.setCodigo(DespesasGastos.getMaxId()+1);
        findAllDespesasGastosAtivos();
        findAllItensPessoalAtivos();
    }

	private void findAllItensPessoalAtivos() {
		allItensPessoalAtivos = despesasGastos.findAllItensPessoalAtivos();		
	}

	private void findAllDespesasGastosAtivos() {
		allDespesasGastosAtivos = despesasGastos.findAllDespesasGastosAtivos();		
	}

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }
	
	public List<DespesasGastos> getAllDespesasGastosAtivos() {
		return allDespesasGastosAtivos;
	}

	public void setAllDespesasGastosAtivos(
			List<DespesasGastos> allDespesasGastosAtivos) {
		this.allDespesasGastosAtivos = allDespesasGastosAtivos;
	}
	
	public String findAllDespasGastosForClassificao() {
		allDespesasGastosClassificacao = DespesasGastos.findAllClassificaco(despesasGastos.getId());
		return null;
	}
	
	public boolean isDataVisible() {
        return dataVisible;
    }

	public void setDataVisible(boolean dataVisible) {
        this.dataVisible = dataVisible;
    }

	public HtmlPanelGrid getCreatePanelGrid() {
        if (createPanelGrid == null) {
            createPanelGrid = populateCreatePanel();
        }
        return createPanelGrid;
    }

	public void setCreatePanelGrid(HtmlPanelGrid createPanelGrid) {
        this.createPanelGrid = createPanelGrid;
    }

	public HtmlPanelGrid getEditPanelGrid() {
        if (editPanelGrid == null) {
            editPanelGrid = populateEditPanel();
        }
        return editPanelGrid;
    }

	public void setEditPanelGrid(HtmlPanelGrid editPanelGrid) {
        this.editPanelGrid = editPanelGrid;
    }

	public HtmlPanelGrid getViewPanelGrid() {
        return populateViewPanel();
    }

	public void setViewPanelGrid(HtmlPanelGrid viewPanelGrid) {
        this.viewPanelGrid = viewPanelGrid;
    }

	public HtmlPanelGrid populateCreatePanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        OutputLabel descrisaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        descrisaoCreateOutput.setFor("descrisaoCreateInput");
        descrisaoCreateOutput.setId("descrisaoCreateOutput");
        descrisaoCreateOutput.setValue("Descrisao:");
        htmlPanelGrid.getChildren().add(descrisaoCreateOutput);
        
        InputText descrisaoCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        descrisaoCreateInput.setId("descrisaoCreateInput");
        descrisaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasGastosBean.despesasGastos.descrisao}", String.class));
        LengthValidator descrisaoCreateInputValidator = new LengthValidator();
        descrisaoCreateInputValidator.setMaximum(30);
        descrisaoCreateInput.addValidator(descrisaoCreateInputValidator);
        descrisaoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(descrisaoCreateInput);
        
        Message descrisaoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        descrisaoCreateInputMessage.setId("descrisaoCreateInputMessage");
        descrisaoCreateInputMessage.setFor("descrisaoCreateInput");
        descrisaoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(descrisaoCreateInputMessage);
        
        OutputLabel classificacaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        classificacaoCreateOutput.setFor("classificacaoCreateInput");
        classificacaoCreateOutput.setId("classificacaoCreateOutput");
        classificacaoCreateOutput.setValue("Classificacao:");
        htmlPanelGrid.getChildren().add(classificacaoCreateOutput);
        
        AutoComplete classificacaoCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        classificacaoCreateInput.setId("classificacaoCreateInput");
        classificacaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasGastosBean.despesasGastos.classificacao}", Despesas.class));
        classificacaoCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{despesasGastosBean.completeClassificacao}", List.class, new Class[] { String.class }));
        classificacaoCreateInput.setDropdown(true);
        classificacaoCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "classificacao", String.class));
        classificacaoCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{classificacao.codigo} #{classificacao.descricao} #{classificacao.idResumo}", String.class));
        classificacaoCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{classificacao}", Despesas.class));
        classificacaoCreateInput.setConverter(new DespesasConverter());
        classificacaoCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(classificacaoCreateInput);
        
        Message classificacaoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        classificacaoCreateInputMessage.setId("classificacaoCreateInputMessage");
        classificacaoCreateInputMessage.setFor("classificacaoCreateInput");
        classificacaoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(classificacaoCreateInputMessage);
        
        OutputLabel despesaPessoalCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        despesaPessoalCreateOutput.setFor("despesaPessoalCreateInput");
        despesaPessoalCreateOutput.setId("despesaPessoalCreateOutput");
        despesaPessoalCreateOutput.setValue("Despesa Pessoal:");
        htmlPanelGrid.getChildren().add(despesaPessoalCreateOutput);
        
        SelectBooleanCheckbox despesaPessoalCreateInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        despesaPessoalCreateInput.setId("despesaPessoalCreateInput");
        despesaPessoalCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasGastosBean.despesasGastos.despesaPessoal}", Boolean.class));
        despesaPessoalCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(despesaPessoalCreateInput);
        
        Message despesaPessoalCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        despesaPessoalCreateInputMessage.setId("despesaPessoalCreateInputMessage");
        despesaPessoalCreateInputMessage.setFor("despesaPessoalCreateInput");
        despesaPessoalCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(despesaPessoalCreateInputMessage);
        
        OutputLabel situacaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoCreateOutput.setFor("situacaoCreateInput");
        situacaoCreateOutput.setId("situacaoCreateOutput");
        situacaoCreateOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoCreateOutput);
        
        SelectBooleanCheckbox situacaoCreateInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoCreateInput.setId("situacaoCreateInput");
        situacaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasGastosBean.despesasGastos.situacao}", Boolean.class));
        situacaoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(situacaoCreateInput);
        
        Message situacaoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        situacaoCreateInputMessage.setId("situacaoCreateInputMessage");
        situacaoCreateInputMessage.setFor("situacaoCreateInput");
        situacaoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(situacaoCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        OutputLabel descrisaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        descrisaoEditOutput.setFor("descrisaoEditInput");
        descrisaoEditOutput.setId("descrisaoEditOutput");
        descrisaoEditOutput.setValue("Descrisao:");
        htmlPanelGrid.getChildren().add(descrisaoEditOutput);
        
        InputText descrisaoEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        descrisaoEditInput.setId("descrisaoEditInput");
        descrisaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasGastosBean.despesasGastos.descrisao}", String.class));
        LengthValidator descrisaoEditInputValidator = new LengthValidator();
        descrisaoEditInputValidator.setMaximum(30);
        descrisaoEditInput.addValidator(descrisaoEditInputValidator);
        descrisaoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(descrisaoEditInput);
        
        Message descrisaoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        descrisaoEditInputMessage.setId("descrisaoEditInputMessage");
        descrisaoEditInputMessage.setFor("descrisaoEditInput");
        descrisaoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(descrisaoEditInputMessage);
        
        OutputLabel classificacaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        classificacaoEditOutput.setFor("classificacaoEditInput");
        classificacaoEditOutput.setId("classificacaoEditOutput");
        classificacaoEditOutput.setValue("Classificacao:");
        htmlPanelGrid.getChildren().add(classificacaoEditOutput);
        
        AutoComplete classificacaoEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        classificacaoEditInput.setId("classificacaoEditInput");
        classificacaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasGastosBean.despesasGastos.classificacao}", Despesas.class));
        classificacaoEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{despesasGastosBean.completeClassificacao}", List.class, new Class[] { String.class }));
        classificacaoEditInput.setDropdown(true);
        classificacaoEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "classificacao", String.class));
        classificacaoEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{classificacao.codigo} #{classificacao.descricao} #{classificacao.idResumo}", String.class));
        classificacaoEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{classificacao}", Despesas.class));
        classificacaoEditInput.setConverter(new DespesasConverter());
        classificacaoEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(classificacaoEditInput);
        
        Message classificacaoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        classificacaoEditInputMessage.setId("classificacaoEditInputMessage");
        classificacaoEditInputMessage.setFor("classificacaoEditInput");
        classificacaoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(classificacaoEditInputMessage);
        
        OutputLabel despesaPessoalEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        despesaPessoalEditOutput.setFor("despesaPessoalEditInput");
        despesaPessoalEditOutput.setId("despesaPessoalEditOutput");
        despesaPessoalEditOutput.setValue("Despesa Pessoal:");
        htmlPanelGrid.getChildren().add(despesaPessoalEditOutput);
        
        SelectBooleanCheckbox despesaPessoalEditInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        despesaPessoalEditInput.setId("despesaPessoalEditInput");
        despesaPessoalEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasGastosBean.despesasGastos.despesaPessoal}", Boolean.class));
        despesaPessoalEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(despesaPessoalEditInput);
        
        Message despesaPessoalEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        despesaPessoalEditInputMessage.setId("despesaPessoalEditInputMessage");
        despesaPessoalEditInputMessage.setFor("despesaPessoalEditInput");
        despesaPessoalEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(despesaPessoalEditInputMessage);
        
        OutputLabel situacaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoEditOutput.setFor("situacaoEditInput");
        situacaoEditOutput.setId("situacaoEditOutput");
        situacaoEditOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoEditOutput);
        
        SelectBooleanCheckbox situacaoEditInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoEditInput.setId("situacaoEditInput");
        situacaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasGastosBean.despesasGastos.situacao}", Boolean.class));
        situacaoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(situacaoEditInput);
        
        Message situacaoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        situacaoEditInputMessage.setId("situacaoEditInputMessage");
        situacaoEditInputMessage.setFor("situacaoEditInput");
        situacaoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(situacaoEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText descrisaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        descrisaoLabel.setId("descrisaoLabel");
        descrisaoLabel.setValue("Descrisao:");
        htmlPanelGrid.getChildren().add(descrisaoLabel);
        
        HtmlOutputText descrisaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        descrisaoValue.setId("descrisaoValue");
        descrisaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasGastosBean.despesasGastos.descrisao}", String.class));
        htmlPanelGrid.getChildren().add(descrisaoValue);
        
        HtmlOutputText classificacaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        classificacaoLabel.setId("classificacaoLabel");
        classificacaoLabel.setValue("Classificacao:");
        htmlPanelGrid.getChildren().add(classificacaoLabel);
        
        HtmlOutputText classificacaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        classificacaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasGastosBean.despesasGastos.classificacao}", Despesas.class));
        classificacaoValue.setConverter(new DespesasConverter());
        htmlPanelGrid.getChildren().add(classificacaoValue);
        
        HtmlOutputText despesaPessoalLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        despesaPessoalLabel.setId("despesaPessoalLabel");
        despesaPessoalLabel.setValue("Despesa Pessoal:");
        htmlPanelGrid.getChildren().add(despesaPessoalLabel);
        
        HtmlOutputText despesaPessoalValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        despesaPessoalValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasGastosBean.despesasGastos.despesaPessoal}", String.class));
        htmlPanelGrid.getChildren().add(despesaPessoalValue);
        
        HtmlOutputText situacaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoLabel.setId("situacaoLabel");
        situacaoLabel.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoLabel);
        
        HtmlOutputText situacaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasGastosBean.despesasGastos.situacao}", String.class));
        htmlPanelGrid.getChildren().add(situacaoValue);
        
        return htmlPanelGrid;
    }

	public DespesasGastos getDespesasGastos() {
        if (despesasGastos == null) {
            despesasGastos = new DespesasGastos();
        }
        return despesasGastos;
    }

	public void setDespesasGastos(DespesasGastos despesasGastos) {
        this.despesasGastos = despesasGastos;
    }

	public List<DespesasGastos> getAllDespesasGastosClassificacao() {
		return allDespesasGastosClassificacao;
	}

	public void setAllDespesasGastosClassificacao(
			List<DespesasGastos> allDespesasGastosClassificacao) {
		this.allDespesasGastosClassificacao = allDespesasGastosClassificacao;
	}

	public List<Despesas> completeClassificacao(String query) {
        List<Despesas> suggestions = new ArrayList<Despesas>();
        for (Despesas despesas : Despesas.findAllDespesases()) {
            String despesasStr = String.valueOf(despesas.getCodigo() +  " "  + despesas.getDescricao() +  " "  + despesas.getIdResumo());
            if (despesasStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(despesas);
            }
        }
        return suggestions;
    }

	public String onEdit() {
        return null;
    }

	public boolean isCreateDialogVisible() {
        return createDialogVisible;
    }

	public void setCreateDialogVisible(boolean createDialogVisible) {
        this.createDialogVisible = createDialogVisible;
    }

	public String displayList() {
        createDialogVisible = false;
        findAllDespesasGastosAtivos();
        return "despesasGastos";
    }

	public String displayCreateDialog() {
        despesasGastos = new DespesasGastos();
        createDialogVisible = true;
        return "despesasGastos";
    }

	public String persist() {
        String message = "";
        if (despesasGastos.getId() != null) {
            despesasGastos.merge();
            message = "message_successfully_updated";
        } else {
            despesasGastos.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "DespesasGastos");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "despesasGastos.xhtml";
    }

	public String persistContas() {
        String message = "";
        despesasGastos.setDespesaPessoal(true);
        
        if (despesasGastos.getId() != null) {
            despesasGastos.merge();
            message = "message_successfully_updated";
        } else {
            despesasGastos.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "DespesasGastos");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "contaFuncionarioRegistro.xhtml";
    }
	
	public String delete() {
		try {
			despesasGastos.setSituacao(false);
			despesasGastos.merge();
			FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "DespesasGastos");
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        init();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O item não pode ser deletado porque possui dependências em outros módulos", "O item não pode ser deletado porque possui dependências em outros módulos"));
		}
        return "despesasGastos.xhtml";
    }

	public void reset() {
        despesasGastos = null;
        init();
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	public String findAllDespasGastosByClassificao(Long id) {
		allDespesasGastosAtivos = DespesasGastos.findAllClassificaco(id);;
		return null;
	}

	public List<DespesasGastos> getAllItensPessoalAtivos() {
		return allItensPessoalAtivos;
	}

	public void setAllItensPessoalAtivos(List<DespesasGastos> allItensPessoalAtivos) {
		this.allItensPessoalAtivos = allItensPessoalAtivos;
	}		
}
