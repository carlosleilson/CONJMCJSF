package br.com.conjmc.jsf;
import br.com.conjmc.cadastrobasico.Despesa_Gastos;
import br.com.conjmc.cadastrobasico.Despesas;
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
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@Configurable
@ManagedBean(name = "despesa_GastosBean")
@SessionScoped
@RooSerializable
@RooJsfManagedBean(entity = Despesa_Gastos.class, beanName = "despesa_GastosBean")
public class Despesa_GastosBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name = "Despesa_Gastoses";

	private Despesa_Gastos despesa_Gastos;

	private List<Despesa_Gastos> allDespesa_Gastoses;

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
        findAllDespesa_Gastoses();
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Despesa_Gastos> getAllDespesa_Gastoses() {
        return allDespesa_Gastoses;
    }

	public void setAllDespesa_Gastoses(List<Despesa_Gastos> allDespesa_Gastoses) {
        this.allDespesa_Gastoses = allDespesa_Gastoses;
    }

	public String findAllDespesa_Gastoses() {
        allDespesa_Gastoses = Despesa_Gastos.findAllDespesa_Gastoses();
        dataVisible = !allDespesa_Gastoses.isEmpty();
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
        descrisaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesa_GastosBean.despesa_Gastos.descrisao}", String.class));
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
        classificacaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesa_GastosBean.despesa_Gastos.classificacao}", Despesas.class));
        classificacaoCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{despesa_GastosBean.completeClassificacao}", List.class, new Class[] { String.class }));
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
        
        OutputLabel DespesaPessoalCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        DespesaPessoalCreateOutput.setFor("DespesaPessoalCreateInput");
        DespesaPessoalCreateOutput.setId("DespesaPessoalCreateOutput");
        DespesaPessoalCreateOutput.setValue("Despesa Pessoal:");
        htmlPanelGrid.getChildren().add(DespesaPessoalCreateOutput);
        
        SelectBooleanCheckbox DespesaPessoalCreateInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        DespesaPessoalCreateInput.setId("DespesaPessoalCreateInput");
        DespesaPessoalCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesa_GastosBean.despesa_Gastos.DespesaPessoal}", Boolean.class));
        DespesaPessoalCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(DespesaPessoalCreateInput);
        
        Message DespesaPessoalCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        DespesaPessoalCreateInputMessage.setId("DespesaPessoalCreateInputMessage");
        DespesaPessoalCreateInputMessage.setFor("DespesaPessoalCreateInput");
        DespesaPessoalCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(DespesaPessoalCreateInputMessage);
        
        OutputLabel situacaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoCreateOutput.setFor("situacaoCreateInput");
        situacaoCreateOutput.setId("situacaoCreateOutput");
        situacaoCreateOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoCreateOutput);
        
        SelectBooleanCheckbox situacaoCreateInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoCreateInput.setId("situacaoCreateInput");
        situacaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesa_GastosBean.despesa_Gastos.situacao}", Boolean.class));
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
        descrisaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesa_GastosBean.despesa_Gastos.descrisao}", String.class));
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
        classificacaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesa_GastosBean.despesa_Gastos.classificacao}", Despesas.class));
        classificacaoEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{despesa_GastosBean.completeClassificacao}", List.class, new Class[] { String.class }));
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
        
        OutputLabel DespesaPessoalEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        DespesaPessoalEditOutput.setFor("DespesaPessoalEditInput");
        DespesaPessoalEditOutput.setId("DespesaPessoalEditOutput");
        DespesaPessoalEditOutput.setValue("Despesa Pessoal:");
        htmlPanelGrid.getChildren().add(DespesaPessoalEditOutput);
        
        SelectBooleanCheckbox DespesaPessoalEditInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        DespesaPessoalEditInput.setId("DespesaPessoalEditInput");
        DespesaPessoalEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesa_GastosBean.despesa_Gastos.DespesaPessoal}", Boolean.class));
        DespesaPessoalEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(DespesaPessoalEditInput);
        
        Message DespesaPessoalEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        DespesaPessoalEditInputMessage.setId("DespesaPessoalEditInputMessage");
        DespesaPessoalEditInputMessage.setFor("DespesaPessoalEditInput");
        DespesaPessoalEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(DespesaPessoalEditInputMessage);
        
        OutputLabel situacaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoEditOutput.setFor("situacaoEditInput");
        situacaoEditOutput.setId("situacaoEditOutput");
        situacaoEditOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoEditOutput);
        
        SelectBooleanCheckbox situacaoEditInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoEditInput.setId("situacaoEditInput");
        situacaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesa_GastosBean.despesa_Gastos.situacao}", Boolean.class));
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
        descrisaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesa_GastosBean.despesa_Gastos.descrisao}", String.class));
        htmlPanelGrid.getChildren().add(descrisaoValue);
        
        HtmlOutputText classificacaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        classificacaoLabel.setId("classificacaoLabel");
        classificacaoLabel.setValue("Classificacao:");
        htmlPanelGrid.getChildren().add(classificacaoLabel);
        
        HtmlOutputText classificacaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        classificacaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesa_GastosBean.despesa_Gastos.classificacao}", Despesas.class));
        classificacaoValue.setConverter(new DespesasConverter());
        htmlPanelGrid.getChildren().add(classificacaoValue);
        
        HtmlOutputText DespesaPessoalLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        DespesaPessoalLabel.setId("DespesaPessoalLabel");
        DespesaPessoalLabel.setValue("Despesa Pessoal:");
        htmlPanelGrid.getChildren().add(DespesaPessoalLabel);
        
        HtmlOutputText DespesaPessoalValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        DespesaPessoalValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesa_GastosBean.despesa_Gastos.DespesaPessoal}", String.class));
        htmlPanelGrid.getChildren().add(DespesaPessoalValue);
        
        HtmlOutputText situacaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoLabel.setId("situacaoLabel");
        situacaoLabel.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoLabel);
        
        HtmlOutputText situacaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesa_GastosBean.despesa_Gastos.situacao}", String.class));
        htmlPanelGrid.getChildren().add(situacaoValue);
        
        return htmlPanelGrid;
    }

	public Despesa_Gastos getDespesa_Gastos() {
        if (despesa_Gastos == null) {
            despesa_Gastos = new Despesa_Gastos();
        }
        return despesa_Gastos;
    }

	public void setDespesa_Gastos(Despesa_Gastos despesa_Gastos) {
        this.despesa_Gastos = despesa_Gastos;
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
        findAllDespesa_Gastoses();
        return "despesa_Gastos";
    }

	public String displayCreateDialog() {
        despesa_Gastos = new Despesa_Gastos();
        createDialogVisible = true;
        return "despesa_Gastos";
    }

	public String persist() {
        String message = "";
        if (despesa_Gastos.getId() != null) {
            despesa_Gastos.merge();
            message = "message_successfully_updated";
        } else {
            despesa_Gastos.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Despesa_Gastos");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        findAllDespesa_Gastoses();
        return "/pages/despesa_Gastos.xhtml";
    }

	public String delete() {
        despesa_Gastos.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Despesa_Gastos");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllDespesa_Gastoses();
    }

	public void reset() {
        despesa_Gastos = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}
