package br.com.conjmc.jsf;
import br.com.conjmc.cadastrobasico.Despesas;
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
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@ManagedBean(name = "despesasBean")
@SessionScoped
@Configurable
@RooSerializable
@RooJsfManagedBean(entity = Despesas.class, beanName = "despesasBean")
public class DespesasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = "Despesases";

	private Despesas despesas;

	private List<Despesas> allDespesases;
	
	private List<Despesas> allDespesasAtivas;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("codigo");
        columns.add("descricao");
        columns.add("idResumo");
        despesas = new Despesas();
        despesas.setSituacao(true);
        despesas = new Despesas();
        despesas.setSituacao(true);
        findAllDespesases();
        findAllDespesasAtivas();
    }

	private void findAllDespesasAtivas() {
		allDespesasAtivas = despesas.findAllDespesasAtivas();	
	}

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Despesas> getAllDespesases() {
        return allDespesases;
    }

	public void setAllDespesases(List<Despesas> allDespesases) {
        this.allDespesases = allDespesases;
    }

	public String findAllDespesases() {
        allDespesases = Despesas.findAllDespesases();
        dataVisible = !allDespesases.isEmpty();
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
        
        OutputLabel codigoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        codigoCreateOutput.setFor("codigoCreateInput");
        codigoCreateOutput.setId("codigoCreateOutput");
        codigoCreateOutput.setValue("Codigo:");
        htmlPanelGrid.getChildren().add(codigoCreateOutput);
        
        InputText codigoCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        codigoCreateInput.setId("codigoCreateInput");
        codigoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasBean.despesas.codigo}", String.class));
        LengthValidator codigoCreateInputValidator = new LengthValidator();
        codigoCreateInputValidator.setMaximum(2);
        codigoCreateInput.addValidator(codigoCreateInputValidator);
        codigoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(codigoCreateInput);
        
        Message codigoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        codigoCreateInputMessage.setId("codigoCreateInputMessage");
        codigoCreateInputMessage.setFor("codigoCreateInput");
        codigoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(codigoCreateInputMessage);
        
        OutputLabel descricaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        descricaoCreateOutput.setFor("descricaoCreateInput");
        descricaoCreateOutput.setId("descricaoCreateOutput");
        descricaoCreateOutput.setValue("Descricao:");
        htmlPanelGrid.getChildren().add(descricaoCreateOutput);
        
        InputTextarea descricaoCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        descricaoCreateInput.setId("descricaoCreateInput");
        descricaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasBean.despesas.descricao}", String.class));
        LengthValidator descricaoCreateInputValidator = new LengthValidator();
        descricaoCreateInputValidator.setMaximum(50);
        descricaoCreateInput.addValidator(descricaoCreateInputValidator);
        descricaoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(descricaoCreateInput);
        
        Message descricaoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        descricaoCreateInputMessage.setId("descricaoCreateInputMessage");
        descricaoCreateInputMessage.setFor("descricaoCreateInput");
        descricaoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(descricaoCreateInputMessage);
        
        OutputLabel idResumoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idResumoCreateOutput.setFor("idResumoCreateInput");
        idResumoCreateOutput.setId("idResumoCreateOutput");
        idResumoCreateOutput.setValue("Id Resumo:");
        htmlPanelGrid.getChildren().add(idResumoCreateOutput);
        
        InputText idResumoCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        idResumoCreateInput.setId("idResumoCreateInput");
        idResumoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasBean.despesas.idResumo}", String.class));
        LengthValidator idResumoCreateInputValidator = new LengthValidator();
        idResumoCreateInputValidator.setMaximum(5);
        idResumoCreateInput.addValidator(idResumoCreateInputValidator);
        idResumoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(idResumoCreateInput);
        
        Message idResumoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idResumoCreateInputMessage.setId("idResumoCreateInputMessage");
        idResumoCreateInputMessage.setFor("idResumoCreateInput");
        idResumoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idResumoCreateInputMessage);
        
        OutputLabel situacaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoCreateOutput.setFor("situacaoCreateInput");
        situacaoCreateOutput.setId("situacaoCreateOutput");
        situacaoCreateOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoCreateOutput);
        
        SelectBooleanCheckbox situacaoCreateInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoCreateInput.setId("situacaoCreateInput");
        situacaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasBean.despesas.situacao}", Boolean.class));
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
        
        OutputLabel codigoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        codigoEditOutput.setFor("codigoEditInput");
        codigoEditOutput.setId("codigoEditOutput");
        codigoEditOutput.setValue("Codigo:");
        htmlPanelGrid.getChildren().add(codigoEditOutput);
        
        InputText codigoEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        codigoEditInput.setId("codigoEditInput");
        codigoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasBean.despesas.codigo}", String.class));
        LengthValidator codigoEditInputValidator = new LengthValidator();
        codigoEditInputValidator.setMaximum(2);
        codigoEditInput.addValidator(codigoEditInputValidator);
        codigoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(codigoEditInput);
        
        Message codigoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        codigoEditInputMessage.setId("codigoEditInputMessage");
        codigoEditInputMessage.setFor("codigoEditInput");
        codigoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(codigoEditInputMessage);
        
        OutputLabel descricaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        descricaoEditOutput.setFor("descricaoEditInput");
        descricaoEditOutput.setId("descricaoEditOutput");
        descricaoEditOutput.setValue("Descricao:");
        htmlPanelGrid.getChildren().add(descricaoEditOutput);
        
        InputTextarea descricaoEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        descricaoEditInput.setId("descricaoEditInput");
        descricaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasBean.despesas.descricao}", String.class));
        LengthValidator descricaoEditInputValidator = new LengthValidator();
        descricaoEditInputValidator.setMaximum(50);
        descricaoEditInput.addValidator(descricaoEditInputValidator);
        descricaoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(descricaoEditInput);
        
        Message descricaoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        descricaoEditInputMessage.setId("descricaoEditInputMessage");
        descricaoEditInputMessage.setFor("descricaoEditInput");
        descricaoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(descricaoEditInputMessage);
        
        OutputLabel idResumoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        idResumoEditOutput.setFor("idResumoEditInput");
        idResumoEditOutput.setId("idResumoEditOutput");
        idResumoEditOutput.setValue("Id Resumo:");
        htmlPanelGrid.getChildren().add(idResumoEditOutput);
        
        InputText idResumoEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        idResumoEditInput.setId("idResumoEditInput");
        idResumoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasBean.despesas.idResumo}", String.class));
        LengthValidator idResumoEditInputValidator = new LengthValidator();
        idResumoEditInputValidator.setMaximum(5);
        idResumoEditInput.addValidator(idResumoEditInputValidator);
        idResumoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(idResumoEditInput);
        
        Message idResumoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        idResumoEditInputMessage.setId("idResumoEditInputMessage");
        idResumoEditInputMessage.setFor("idResumoEditInput");
        idResumoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(idResumoEditInputMessage);
        
        OutputLabel situacaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoEditOutput.setFor("situacaoEditInput");
        situacaoEditOutput.setId("situacaoEditOutput");
        situacaoEditOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoEditOutput);
        
        SelectBooleanCheckbox situacaoEditInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoEditInput.setId("situacaoEditInput");
        situacaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasBean.despesas.situacao}", Boolean.class));
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
        
        HtmlOutputText codigoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        codigoLabel.setId("codigoLabel");
        codigoLabel.setValue("Codigo:");
        htmlPanelGrid.getChildren().add(codigoLabel);
        
        HtmlOutputText codigoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        codigoValue.setId("codigoValue");
        codigoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasBean.despesas.codigo}", String.class));
        htmlPanelGrid.getChildren().add(codigoValue);
        
        HtmlOutputText descricaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        descricaoLabel.setId("descricaoLabel");
        descricaoLabel.setValue("Descricao:");
        htmlPanelGrid.getChildren().add(descricaoLabel);
        
        InputTextarea descricaoValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        descricaoValue.setId("descricaoValue");
        descricaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasBean.despesas.descricao}", String.class));
        descricaoValue.setReadonly(true);
        descricaoValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(descricaoValue);
        
        HtmlOutputText idResumoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idResumoLabel.setId("idResumoLabel");
        idResumoLabel.setValue("Id Resumo:");
        htmlPanelGrid.getChildren().add(idResumoLabel);
        
        HtmlOutputText idResumoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        idResumoValue.setId("idResumoValue");
        idResumoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasBean.despesas.idResumo}", String.class));
        htmlPanelGrid.getChildren().add(idResumoValue);
        
        HtmlOutputText situacaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoLabel.setId("situacaoLabel");
        situacaoLabel.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoLabel);
        
        HtmlOutputText situacaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasBean.despesas.situacao}", String.class));
        htmlPanelGrid.getChildren().add(situacaoValue);
        
        return htmlPanelGrid;
    }

	public Despesas getDespesas() {
        if (despesas == null) {
            despesas = new Despesas();
        }
        return despesas;
    }

	public void setDespesas(Despesas despesas) {
        this.despesas = despesas;
    }

	public String onEdit() {
        return null;
    }

	public List<Despesas> getAllDespesasAtivas() {
		return allDespesasAtivas;
	}

	public void setAllDespesasAtivas(List<Despesas> allDespesasAtivas) {
		this.allDespesasAtivas = allDespesasAtivas;
	}

	public boolean isCreateDialogVisible() {
        return createDialogVisible;
    }

	public void setCreateDialogVisible(boolean createDialogVisible) {
        this.createDialogVisible = createDialogVisible;
    }

	public String displayList() {
        createDialogVisible = false;
        findAllDespesases();
        return "despesas";
    }

	public String displayCreateDialog() {
        despesas = new Despesas();
        createDialogVisible = true;
        return "despesas";
    }

	public String persist() {
        String message = "";
        if (despesas.getId() != null) {
            despesas.merge();
            message = "message_successfully_updated";
        } else {
            despesas.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Despesas");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        findAllDespesases();
        return "/pages/despesas.xtml";
    }

	public String delete() {
        despesas.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Despesas");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllDespesases();
    }

	public void reset() {
        despesas = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
	
}
