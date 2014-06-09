package br.com.conjmc.jsf;
import br.com.conjmc.cadastrobasico.Motoqueiros;
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

@ManagedBean(name = "motoqueirosBean")
@SessionScoped
@Configurable
@RooSerializable
@RooJsfManagedBean(entity = Motoqueiros.class, beanName = "motoqueirosBean")
public class MotoqueirosBean implements Serializable {

	private String name = "Motoqueiroses";

	private Motoqueiros motoqueiros;

	private List<Motoqueiros> allMotoqueiroses;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("apelido");
        columns.add("nome");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Motoqueiros> getAllMotoqueiroses() {
        return allMotoqueiroses;
    }

	public void setAllMotoqueiroses(List<Motoqueiros> allMotoqueiroses) {
        this.allMotoqueiroses = allMotoqueiroses;
    }

	public String findAllMotoqueiroses() {
        allMotoqueiroses = Motoqueiros.findAllMotoqueiroses();
        dataVisible = !allMotoqueiroses.isEmpty();
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
        
        OutputLabel apelidoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        apelidoCreateOutput.setFor("apelidoCreateInput");
        apelidoCreateOutput.setId("apelidoCreateOutput");
        apelidoCreateOutput.setValue("Apelido:");
        htmlPanelGrid.getChildren().add(apelidoCreateOutput);
        
        InputText apelidoCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        apelidoCreateInput.setId("apelidoCreateInput");
        apelidoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{motoqueirosBean.motoqueiros.apelido}", String.class));
        LengthValidator apelidoCreateInputValidator = new LengthValidator();
        apelidoCreateInputValidator.setMaximum(10);
        apelidoCreateInput.addValidator(apelidoCreateInputValidator);
        apelidoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(apelidoCreateInput);
        
        Message apelidoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        apelidoCreateInputMessage.setId("apelidoCreateInputMessage");
        apelidoCreateInputMessage.setFor("apelidoCreateInput");
        apelidoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(apelidoCreateInputMessage);
        
        OutputLabel nomeCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomeCreateOutput.setFor("nomeCreateInput");
        nomeCreateOutput.setId("nomeCreateOutput");
        nomeCreateOutput.setValue("Nome:");
        htmlPanelGrid.getChildren().add(nomeCreateOutput);
        
        InputText nomeCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        nomeCreateInput.setId("nomeCreateInput");
        nomeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{motoqueirosBean.motoqueiros.nome}", String.class));
        nomeCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(nomeCreateInput);
        
        Message nomeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomeCreateInputMessage.setId("nomeCreateInputMessage");
        nomeCreateInputMessage.setFor("nomeCreateInput");
        nomeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomeCreateInputMessage);
        
        OutputLabel situacaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoCreateOutput.setFor("situacaoCreateInput");
        situacaoCreateOutput.setId("situacaoCreateOutput");
        situacaoCreateOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoCreateOutput);
        
        SelectBooleanCheckbox situacaoCreateInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoCreateInput.setId("situacaoCreateInput");
        situacaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{motoqueirosBean.motoqueiros.situacao}", Boolean.class));
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
        
        OutputLabel apelidoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        apelidoEditOutput.setFor("apelidoEditInput");
        apelidoEditOutput.setId("apelidoEditOutput");
        apelidoEditOutput.setValue("Apelido:");
        htmlPanelGrid.getChildren().add(apelidoEditOutput);
        
        InputText apelidoEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        apelidoEditInput.setId("apelidoEditInput");
        apelidoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{motoqueirosBean.motoqueiros.apelido}", String.class));
        LengthValidator apelidoEditInputValidator = new LengthValidator();
        apelidoEditInputValidator.setMaximum(10);
        apelidoEditInput.addValidator(apelidoEditInputValidator);
        apelidoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(apelidoEditInput);
        
        Message apelidoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        apelidoEditInputMessage.setId("apelidoEditInputMessage");
        apelidoEditInputMessage.setFor("apelidoEditInput");
        apelidoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(apelidoEditInputMessage);
        
        OutputLabel nomeEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomeEditOutput.setFor("nomeEditInput");
        nomeEditOutput.setId("nomeEditOutput");
        nomeEditOutput.setValue("Nome:");
        htmlPanelGrid.getChildren().add(nomeEditOutput);
        
        InputText nomeEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        nomeEditInput.setId("nomeEditInput");
        nomeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{motoqueirosBean.motoqueiros.nome}", String.class));
        nomeEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(nomeEditInput);
        
        Message nomeEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomeEditInputMessage.setId("nomeEditInputMessage");
        nomeEditInputMessage.setFor("nomeEditInput");
        nomeEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomeEditInputMessage);
        
        OutputLabel situacaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoEditOutput.setFor("situacaoEditInput");
        situacaoEditOutput.setId("situacaoEditOutput");
        situacaoEditOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoEditOutput);
        
        SelectBooleanCheckbox situacaoEditInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoEditInput.setId("situacaoEditInput");
        situacaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{motoqueirosBean.motoqueiros.situacao}", Boolean.class));
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
        
        HtmlOutputText apelidoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        apelidoLabel.setId("apelidoLabel");
        apelidoLabel.setValue("Apelido:");
        htmlPanelGrid.getChildren().add(apelidoLabel);
        
        HtmlOutputText apelidoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        apelidoValue.setId("apelidoValue");
        apelidoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{motoqueirosBean.motoqueiros.apelido}", String.class));
        htmlPanelGrid.getChildren().add(apelidoValue);
        
        HtmlOutputText nomeLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nomeLabel.setId("nomeLabel");
        nomeLabel.setValue("Nome:");
        htmlPanelGrid.getChildren().add(nomeLabel);
        
        HtmlOutputText nomeValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nomeValue.setId("nomeValue");
        nomeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{motoqueirosBean.motoqueiros.nome}", String.class));
        htmlPanelGrid.getChildren().add(nomeValue);
        
        HtmlOutputText situacaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoLabel.setId("situacaoLabel");
        situacaoLabel.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoLabel);
        
        HtmlOutputText situacaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{motoqueirosBean.motoqueiros.situacao}", String.class));
        htmlPanelGrid.getChildren().add(situacaoValue);
        
        return htmlPanelGrid;
    }

	public Motoqueiros getMotoqueiros() {
        if (motoqueiros == null) {
            motoqueiros = new Motoqueiros();
        }
        return motoqueiros;
    }

	public void setMotoqueiros(Motoqueiros motoqueiros) {
        this.motoqueiros = motoqueiros;
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
        findAllMotoqueiroses();
        return "motoqueiros";
    }

	public String displayCreateDialog() {
        motoqueiros = new Motoqueiros();
        createDialogVisible = true;
        return "motoqueiros";
    }

	public String persist() {
        String message = "";
        if (motoqueiros.getId() != null) {
            motoqueiros.merge();
            message = "message_successfully_updated";
        } else {
            motoqueiros.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Motoqueiros");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllMotoqueiroses();
    }

	public String delete() {
        motoqueiros.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Motoqueiros");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllMotoqueiroses();
    }

	public void reset() {
        motoqueiros = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	private static final long serialVersionUID = 1L;
}
