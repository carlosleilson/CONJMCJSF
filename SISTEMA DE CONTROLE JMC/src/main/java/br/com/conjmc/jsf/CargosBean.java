package br.com.conjmc.jsf;
import br.com.conjmc.cadastrobasico.Cargos;
import br.com.conjmc.cadastrobasico.Perfil;
import br.com.conjmc.cadastrobasico.Setor;
import br.com.conjmc.jsf.util.MessageFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@Configurable
@ManagedBean(name = "cargosBean")
@SessionScoped
@RooSerializable
@RooJsfManagedBean(entity = Cargos.class, beanName = "cargosBean")
public class CargosBean implements Serializable {

	private String name = "Cargoses";

	private Cargos cargos;

	private List<Cargos> allCargoses;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;
	
	private Setor setor;
	
	private List<Setor> allSetor;

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public List<Setor> getAllSetor() {
		return Arrays.asList(Setor.values());
	}

	public void setAllSetor(List<Setor> allSetor) {
		this.allSetor = allSetor;
	}

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("nome");
        findAllCargoses();
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Cargos> getAllCargoses() {
        return allCargoses;
    }

	public void setAllCargoses(List<Cargos> allCargoses) {
        this.allCargoses = allCargoses;
    }

	public String findAllCargoses() {
        allCargoses = Cargos.findAllCargoses();
        dataVisible = !allCargoses.isEmpty();
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
        
        OutputLabel nomeCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomeCreateOutput.setFor("nomeCreateInput");
        nomeCreateOutput.setId("nomeCreateOutput");
        nomeCreateOutput.setValue("Nome:");
        htmlPanelGrid.getChildren().add(nomeCreateOutput);
        
        InputText nomeCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        nomeCreateInput.setId("nomeCreateInput");
        nomeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cargosBean.cargos.nome}", String.class));
        nomeCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomeCreateInput);
        
        Message nomeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomeCreateInputMessage.setId("nomeCreateInputMessage");
        nomeCreateInputMessage.setFor("nomeCreateInput");
        nomeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomeCreateInputMessage);
        
        OutputLabel setorCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        setorCreateOutput.setFor("setorCreateInput");
        setorCreateOutput.setId("setorCreateOutput");
        setorCreateOutput.setValue("Setor:");
        htmlPanelGrid.getChildren().add(setorCreateOutput);
        
        AutoComplete setorCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        setorCreateInput.setId("setorCreateInput");
        setorCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cargosBean.cargos.setor}", Setor.class));
        setorCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{cargosBean.completeSetor}", List.class, new Class[] { String.class }));
        setorCreateInput.setDropdown(true);
        setorCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(setorCreateInput);
        
        Message setorCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        setorCreateInputMessage.setId("setorCreateInputMessage");
        setorCreateInputMessage.setFor("setorCreateInput");
        setorCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(setorCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        OutputLabel nomeEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        nomeEditOutput.setFor("nomeEditInput");
        nomeEditOutput.setId("nomeEditOutput");
        nomeEditOutput.setValue("Nome:");
        htmlPanelGrid.getChildren().add(nomeEditOutput);
        
        InputText nomeEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        nomeEditInput.setId("nomeEditInput");
        nomeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cargosBean.cargos.nome}", String.class));
        nomeEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomeEditInput);
        
        Message nomeEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomeEditInputMessage.setId("nomeEditInputMessage");
        nomeEditInputMessage.setFor("nomeEditInput");
        nomeEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomeEditInputMessage);
        
        OutputLabel setorEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        setorEditOutput.setFor("setorEditInput");
        setorEditOutput.setId("setorEditOutput");
        setorEditOutput.setValue("Setor:");
        htmlPanelGrid.getChildren().add(setorEditOutput);
        
        AutoComplete setorEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        setorEditInput.setId("setorEditInput");
        setorEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cargosBean.cargos.setor}", Setor.class));
        setorEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{cargosBean.completeSetor}", List.class, new Class[] { String.class }));
        setorEditInput.setDropdown(true);
        setorEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(setorEditInput);
        
        Message setorEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        setorEditInputMessage.setId("setorEditInputMessage");
        setorEditInputMessage.setFor("setorEditInput");
        setorEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(setorEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText nomeLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nomeLabel.setId("nomeLabel");
        nomeLabel.setValue("Nome:");
        htmlPanelGrid.getChildren().add(nomeLabel);
        
        HtmlOutputText nomeValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        nomeValue.setId("nomeValue");
        nomeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cargosBean.cargos.nome}", String.class));
        htmlPanelGrid.getChildren().add(nomeValue);
        
        HtmlOutputText setorLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        setorLabel.setId("setorLabel");
        setorLabel.setValue("Setor:");
        htmlPanelGrid.getChildren().add(setorLabel);
        
        HtmlOutputText setorValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        setorValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{cargosBean.cargos.setor}", String.class));
        htmlPanelGrid.getChildren().add(setorValue);
        
        return htmlPanelGrid;
    }

	public Cargos getCargos() {
        if (cargos == null) {
            cargos = new Cargos();
        }
        return cargos;
    }

	public void setCargos(Cargos cargos) {
        this.cargos = cargos;
    }

	public List<Setor> completeSetor(String query) {
        List<Setor> suggestions = new ArrayList<Setor>();
        for (Setor setor : Setor.values()) {
            if (setor.name().toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(setor);
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
        findAllCargoses();
        return "cargos";
    }

	public String displayCreateDialog() {
        cargos = new Cargos();
        createDialogVisible = true;
        return "cargos";
    }

	public String persist() {
        String message = "";
        if (cargos.getId() != null) {
            cargos.merge();
            message = "message_successfully_updated";
        } else {
            cargos.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Cargos");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllCargoses();
    }

	public String delete() {
        cargos.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Cargos");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllCargoses();
    }

	public void reset() {
        cargos = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	private static final long serialVersionUID = 1L;
}
