package br.com.conjmc.jsf;
import br.com.conjmc.cadastrobasico.LancamentosFuncionarios;
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
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@ManagedBean(name = "lancamentosFuncionariosBean")
@SessionScoped
@Configurable
@RooSerializable
@RooJsfManagedBean(entity = LancamentosFuncionarios.class, beanName = "lancamentosFuncionariosBean")
public class LancamentosFuncionariosBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name = "LancamentosFuncionarioses";

	private LancamentosFuncionarios lancamentosFuncionarios;

	private List<LancamentosFuncionarios> allLancamentosFuncionarioses;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("descricao");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<LancamentosFuncionarios> getAllLancamentosFuncionarioses() {
        return allLancamentosFuncionarioses;
    }

	public void setAllLancamentosFuncionarioses(List<LancamentosFuncionarios> allLancamentosFuncionarioses) {
        this.allLancamentosFuncionarioses = allLancamentosFuncionarioses;
    }

	public String findAllLancamentosFuncionarioses() {
        allLancamentosFuncionarioses = LancamentosFuncionarios.findAllLancamentosFuncionarioses();
        dataVisible = !allLancamentosFuncionarioses.isEmpty();
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
        
        OutputLabel descricaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        descricaoCreateOutput.setFor("descricaoCreateInput");
        descricaoCreateOutput.setId("descricaoCreateOutput");
        descricaoCreateOutput.setValue("Descricao:");
        htmlPanelGrid.getChildren().add(descricaoCreateOutput);
        
        InputText descricaoCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        descricaoCreateInput.setId("descricaoCreateInput");
        descricaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{lancamentosFuncionariosBean.lancamentosFuncionarios.descricao}", String.class));
        LengthValidator descricaoCreateInputValidator = new LengthValidator();
        descricaoCreateInputValidator.setMaximum(30);
        descricaoCreateInput.addValidator(descricaoCreateInputValidator);
        descricaoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(descricaoCreateInput);
        
        Message descricaoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        descricaoCreateInputMessage.setId("descricaoCreateInputMessage");
        descricaoCreateInputMessage.setFor("descricaoCreateInput");
        descricaoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(descricaoCreateInputMessage);
        
        OutputLabel situacaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoCreateOutput.setFor("situacaoCreateInput");
        situacaoCreateOutput.setId("situacaoCreateOutput");
        situacaoCreateOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoCreateOutput);
        
        SelectBooleanCheckbox situacaoCreateInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoCreateInput.setId("situacaoCreateInput");
        situacaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{lancamentosFuncionariosBean.lancamentosFuncionarios.situacao}", Boolean.class));
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
        
        OutputLabel descricaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        descricaoEditOutput.setFor("descricaoEditInput");
        descricaoEditOutput.setId("descricaoEditOutput");
        descricaoEditOutput.setValue("Descricao:");
        htmlPanelGrid.getChildren().add(descricaoEditOutput);
        
        InputText descricaoEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        descricaoEditInput.setId("descricaoEditInput");
        descricaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{lancamentosFuncionariosBean.lancamentosFuncionarios.descricao}", String.class));
        LengthValidator descricaoEditInputValidator = new LengthValidator();
        descricaoEditInputValidator.setMaximum(30);
        descricaoEditInput.addValidator(descricaoEditInputValidator);
        descricaoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(descricaoEditInput);
        
        Message descricaoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        descricaoEditInputMessage.setId("descricaoEditInputMessage");
        descricaoEditInputMessage.setFor("descricaoEditInput");
        descricaoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(descricaoEditInputMessage);
        
        OutputLabel situacaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoEditOutput.setFor("situacaoEditInput");
        situacaoEditOutput.setId("situacaoEditOutput");
        situacaoEditOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoEditOutput);
        
        SelectBooleanCheckbox situacaoEditInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoEditInput.setId("situacaoEditInput");
        situacaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{lancamentosFuncionariosBean.lancamentosFuncionarios.situacao}", Boolean.class));
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
        
        HtmlOutputText descricaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        descricaoLabel.setId("descricaoLabel");
        descricaoLabel.setValue("Descricao:");
        htmlPanelGrid.getChildren().add(descricaoLabel);
        
        HtmlOutputText descricaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        descricaoValue.setId("descricaoValue");
        descricaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{lancamentosFuncionariosBean.lancamentosFuncionarios.descricao}", String.class));
        htmlPanelGrid.getChildren().add(descricaoValue);
        
        HtmlOutputText situacaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoLabel.setId("situacaoLabel");
        situacaoLabel.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoLabel);
        
        HtmlOutputText situacaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{lancamentosFuncionariosBean.lancamentosFuncionarios.situacao}", String.class));
        htmlPanelGrid.getChildren().add(situacaoValue);
        
        return htmlPanelGrid;
    }

	public LancamentosFuncionarios getLancamentosFuncionarios() {
        if (lancamentosFuncionarios == null) {
            lancamentosFuncionarios = new LancamentosFuncionarios();
        }
        return lancamentosFuncionarios;
    }

	public void setLancamentosFuncionarios(LancamentosFuncionarios lancamentosFuncionarios) {
        this.lancamentosFuncionarios = lancamentosFuncionarios;
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
        findAllLancamentosFuncionarioses();
        return "lancamentosFuncionarios";
    }

	public String displayCreateDialog() {
        lancamentosFuncionarios = new LancamentosFuncionarios();
        createDialogVisible = true;
        return "lancamentosFuncionarios";
    }

	public String persist() {
        String message = "";
        if (lancamentosFuncionarios.getId() != null) {
            lancamentosFuncionarios.merge();
            message = "message_successfully_updated";
        } else {
            lancamentosFuncionarios.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "LancamentosFuncionarios");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllLancamentosFuncionarioses();
    }

	public String delete() {
        lancamentosFuncionarios.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "LancamentosFuncionarios");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllLancamentosFuncionarioses();
    }

	public void reset() {
        lancamentosFuncionarios = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}
