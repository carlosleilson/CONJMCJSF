package br.com.conjmc.jsf;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.cadastrobasico.Perfil;
import br.com.conjmc.cadastrobasico.Usuarios;
import br.com.conjmc.jsf.converter.FuncionariosConverter;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@ManagedBean(name = "usuariosBean")
@SessionScoped
@Configurable
@RooSerializable
@RooJsfManagedBean(entity = Usuarios.class, beanName = "usuariosBean")
public class UsuariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = "Usuarioses";

	private Usuarios usuarios;

	private List<Usuarios> allUsuarioses;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("senha");
        findAllUsuarioses();
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Usuarios> getAllUsuarioses() {
        return allUsuarioses;
    }

	public void setAllUsuarioses(List<Usuarios> allUsuarioses) {
        this.allUsuarioses = allUsuarioses;
    }

	public String findAllUsuarioses() {
        allUsuarioses = Usuarios.findAllUsuarioses();
        dataVisible = !allUsuarioses.isEmpty();
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
        
        AutoComplete nomeCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        nomeCreateInput.setId("nomeCreateInput");
        nomeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuariosBean.usuarios.nome}", Funcionarios.class));
        nomeCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{usuariosBean.completeNome}", List.class, new Class[] { String.class }));
        nomeCreateInput.setDropdown(true);
        nomeCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "nome", String.class));
        nomeCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{nome.nome} #{nome.apelido} #{nome.salario} #{nome.cpf}", String.class));
        nomeCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{nome}", Funcionarios.class));
        nomeCreateInput.setConverter(new FuncionariosConverter());
        nomeCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomeCreateInput);
        
        Message nomeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomeCreateInputMessage.setId("nomeCreateInputMessage");
        nomeCreateInputMessage.setFor("nomeCreateInput");
        nomeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomeCreateInputMessage);
        
        OutputLabel senhaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        senhaCreateOutput.setFor("senhaCreateInput");
        senhaCreateOutput.setId("senhaCreateOutput");
        senhaCreateOutput.setValue("Senha:");
        htmlPanelGrid.getChildren().add(senhaCreateOutput);
        
        InputText senhaCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        senhaCreateInput.setId("senhaCreateInput");
        senhaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuariosBean.usuarios.senha}", String.class));
        LengthValidator senhaCreateInputValidator = new LengthValidator();
        senhaCreateInputValidator.setMinimum(6);
        senhaCreateInput.addValidator(senhaCreateInputValidator);
        senhaCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(senhaCreateInput);
        
        Message senhaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        senhaCreateInputMessage.setId("senhaCreateInputMessage");
        senhaCreateInputMessage.setFor("senhaCreateInput");
        senhaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(senhaCreateInputMessage);
        
        OutputLabel perfilCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        perfilCreateOutput.setFor("perfilCreateInput");
        perfilCreateOutput.setId("perfilCreateOutput");
        perfilCreateOutput.setValue("Perfil:");
        htmlPanelGrid.getChildren().add(perfilCreateOutput);
        
        AutoComplete perfilCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        perfilCreateInput.setId("perfilCreateInput");
        perfilCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuariosBean.usuarios.perfil}", Perfil.class));
        perfilCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{usuariosBean.completePerfil}", List.class, new Class[] { String.class }));
        perfilCreateInput.setDropdown(true);
        perfilCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(perfilCreateInput);
        
        Message perfilCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        perfilCreateInputMessage.setId("perfilCreateInputMessage");
        perfilCreateInputMessage.setFor("perfilCreateInput");
        perfilCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(perfilCreateInputMessage);
        
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
        
        AutoComplete nomeEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        nomeEditInput.setId("nomeEditInput");
        nomeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuariosBean.usuarios.nome}", Funcionarios.class));
        nomeEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{usuariosBean.completeNome}", List.class, new Class[] { String.class }));
        nomeEditInput.setDropdown(true);
        nomeEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "nome", String.class));
        nomeEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{nome.nome} #{nome.apelido} #{nome.salario} #{nome.cpf}", String.class));
        nomeEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{nome}", Funcionarios.class));
        nomeEditInput.setConverter(new FuncionariosConverter());
        nomeEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomeEditInput);
        
        Message nomeEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomeEditInputMessage.setId("nomeEditInputMessage");
        nomeEditInputMessage.setFor("nomeEditInput");
        nomeEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomeEditInputMessage);
        
        OutputLabel senhaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        senhaEditOutput.setFor("senhaEditInput");
        senhaEditOutput.setId("senhaEditOutput");
        senhaEditOutput.setValue("Senha:");
        htmlPanelGrid.getChildren().add(senhaEditOutput);
        
        InputText senhaEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        senhaEditInput.setId("senhaEditInput");
        senhaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuariosBean.usuarios.senha}", String.class));
        LengthValidator senhaEditInputValidator = new LengthValidator();
        senhaEditInputValidator.setMinimum(6);
        senhaEditInput.addValidator(senhaEditInputValidator);
        senhaEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(senhaEditInput);
        
        Message senhaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        senhaEditInputMessage.setId("senhaEditInputMessage");
        senhaEditInputMessage.setFor("senhaEditInput");
        senhaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(senhaEditInputMessage);
        
        OutputLabel perfilEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        perfilEditOutput.setFor("perfilEditInput");
        perfilEditOutput.setId("perfilEditOutput");
        perfilEditOutput.setValue("Perfil:");
        htmlPanelGrid.getChildren().add(perfilEditOutput);
        
        AutoComplete perfilEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        perfilEditInput.setId("perfilEditInput");
        perfilEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuariosBean.usuarios.perfil}", Perfil.class));
        perfilEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{usuariosBean.completePerfil}", List.class, new Class[] { String.class }));
        perfilEditInput.setDropdown(true);
        perfilEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(perfilEditInput);
        
        Message perfilEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        perfilEditInputMessage.setId("perfilEditInputMessage");
        perfilEditInputMessage.setFor("perfilEditInput");
        perfilEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(perfilEditInputMessage);
        
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
        nomeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuariosBean.usuarios.nome}", Funcionarios.class));
        nomeValue.setConverter(new FuncionariosConverter());
        htmlPanelGrid.getChildren().add(nomeValue);
        
        HtmlOutputText senhaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        senhaLabel.setId("senhaLabel");
        senhaLabel.setValue("Senha:");
        htmlPanelGrid.getChildren().add(senhaLabel);
        
        HtmlOutputText senhaValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        senhaValue.setId("senhaValue");
        senhaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuariosBean.usuarios.senha}", String.class));
        htmlPanelGrid.getChildren().add(senhaValue);
        
        HtmlOutputText perfilLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        perfilLabel.setId("perfilLabel");
        perfilLabel.setValue("Perfil:");
        htmlPanelGrid.getChildren().add(perfilLabel);
        
        HtmlOutputText perfilValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        perfilValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{usuariosBean.usuarios.perfil}", String.class));
        htmlPanelGrid.getChildren().add(perfilValue);
        
        return htmlPanelGrid;
    }

	public Usuarios getUsuarios() {
        if (usuarios == null) {
            usuarios = new Usuarios();
        }
        return usuarios;
    }

	public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

	public List<Funcionarios> completeNome(String query) {
        List<Funcionarios> suggestions = new ArrayList<Funcionarios>();
        for (Funcionarios funcionarios : Funcionarios.findAllFuncionarioses()) {
            String funcionariosStr = String.valueOf(funcionarios.getNome() +  " "  + funcionarios.getApelido() +  " "  + funcionarios.getSalario() +  " "  + funcionarios.getCpf());
            if (funcionariosStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(funcionarios);
            }
        }
        return suggestions;
    }

	public List<Perfil> completePerfil(String query) {
        List<Perfil> suggestions = new ArrayList<Perfil>();
        for (Perfil perfil : Perfil.values()) {
            if (perfil.name().toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(perfil);
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
        findAllUsuarioses();
        return "usuarios";
    }

	public String displayCreateDialog() {
        usuarios = new Usuarios();
        createDialogVisible = true;
        return "usuarios";
    }

	public String persist() {
        String message = "";
        if (usuarios.getId() != null) {
            usuarios.merge();
            message = "message_successfully_updated";
        } else {
            usuarios.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Usuarios");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        findAllUsuarioses();
        return "/pages/usuarios.xhtml";
    }

	public String delete() {
        usuarios.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Usuarios");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllUsuarioses();
    }

	public void reset() {
        usuarios = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}
