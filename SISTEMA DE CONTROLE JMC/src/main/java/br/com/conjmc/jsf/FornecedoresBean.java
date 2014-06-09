package br.com.conjmc.jsf;
import br.com.conjmc.cadastrobasico.Despesas;
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
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@ManagedBean(name = "fornecedoresBean")
@SessionScoped
@Configurable
@RooSerializable
@RooJsfManagedBean(entity = Fornecedores.class, beanName = "fornecedoresBean")
public class FornecedoresBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = "Fornecedoreses";

	private Fornecedores fornecedores;

	private List<Fornecedores> allFornecedoreses;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("nome");
        columns.add("apelido");
        columns.add("cnpj");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Fornecedores> getAllFornecedoreses() {
        return allFornecedoreses;
    }

	public void setAllFornecedoreses(List<Fornecedores> allFornecedoreses) {
        this.allFornecedoreses = allFornecedoreses;
    }

	public String findAllFornecedoreses() {
        allFornecedoreses = Fornecedores.findAllFornecedoreses();
        dataVisible = !allFornecedoreses.isEmpty();
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
        nomeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.nome}", String.class));
        LengthValidator nomeCreateInputValidator = new LengthValidator();
        nomeCreateInputValidator.setMaximum(10);
        nomeCreateInput.addValidator(nomeCreateInputValidator);
        nomeCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomeCreateInput);
        
        Message nomeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomeCreateInputMessage.setId("nomeCreateInputMessage");
        nomeCreateInputMessage.setFor("nomeCreateInput");
        nomeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomeCreateInputMessage);
        
        OutputLabel apelidoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        apelidoCreateOutput.setFor("apelidoCreateInput");
        apelidoCreateOutput.setId("apelidoCreateOutput");
        apelidoCreateOutput.setValue("Apelido:");
        htmlPanelGrid.getChildren().add(apelidoCreateOutput);
        
        InputText apelidoCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        apelidoCreateInput.setId("apelidoCreateInput");
        apelidoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.apelido}", String.class));
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
        
        OutputLabel cnpjCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cnpjCreateOutput.setFor("cnpjCreateInput");
        cnpjCreateOutput.setId("cnpjCreateOutput");
        cnpjCreateOutput.setValue("Cnpj:");
        htmlPanelGrid.getChildren().add(cnpjCreateOutput);
        
        InputText cnpjCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cnpjCreateInput.setId("cnpjCreateInput");
        cnpjCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.cnpj}", String.class));
        LengthValidator cnpjCreateInputValidator = new LengthValidator();
        cnpjCreateInputValidator.setMaximum(18);
        cnpjCreateInput.addValidator(cnpjCreateInputValidator);
        cnpjCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(cnpjCreateInput);
        
        Message cnpjCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cnpjCreateInputMessage.setId("cnpjCreateInputMessage");
        cnpjCreateInputMessage.setFor("cnpjCreateInput");
        cnpjCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cnpjCreateInputMessage);
        
        OutputLabel situacaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoCreateOutput.setFor("situacaoCreateInput");
        situacaoCreateOutput.setId("situacaoCreateOutput");
        situacaoCreateOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoCreateOutput);
        
        SelectBooleanCheckbox situacaoCreateInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoCreateInput.setId("situacaoCreateInput");
        situacaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.situacao}", Boolean.class));
        situacaoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(situacaoCreateInput);
        
        Message situacaoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        situacaoCreateInputMessage.setId("situacaoCreateInputMessage");
        situacaoCreateInputMessage.setFor("situacaoCreateInput");
        situacaoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(situacaoCreateInputMessage);
        
        OutputLabel produtosFornecidosCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        produtosFornecidosCreateOutput.setFor("produtosFornecidosCreateInput");
        produtosFornecidosCreateOutput.setId("produtosFornecidosCreateOutput");
        produtosFornecidosCreateOutput.setValue("Produtos Fornecidos:");
        htmlPanelGrid.getChildren().add(produtosFornecidosCreateOutput);
        
        AutoComplete produtosFornecidosCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        produtosFornecidosCreateInput.setId("produtosFornecidosCreateInput");
        produtosFornecidosCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.produtosFornecidos}", Despesas.class));
        produtosFornecidosCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{fornecedoresBean.completeProdutosFornecidos}", List.class, new Class[] { String.class }));
        produtosFornecidosCreateInput.setDropdown(true);
        produtosFornecidosCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "produtosFornecidos", String.class));
        produtosFornecidosCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{produtosFornecidos.codigo} #{produtosFornecidos.descricao} #{produtosFornecidos.idResumo}", String.class));
        produtosFornecidosCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{produtosFornecidos}", Despesas.class));
        produtosFornecidosCreateInput.setConverter(new DespesasConverter());
        produtosFornecidosCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(produtosFornecidosCreateInput);
        
        Message produtosFornecidosCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        produtosFornecidosCreateInputMessage.setId("produtosFornecidosCreateInputMessage");
        produtosFornecidosCreateInputMessage.setFor("produtosFornecidosCreateInput");
        produtosFornecidosCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(produtosFornecidosCreateInputMessage);
        
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
        nomeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.nome}", String.class));
        LengthValidator nomeEditInputValidator = new LengthValidator();
        nomeEditInputValidator.setMaximum(10);
        nomeEditInput.addValidator(nomeEditInputValidator);
        nomeEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(nomeEditInput);
        
        Message nomeEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        nomeEditInputMessage.setId("nomeEditInputMessage");
        nomeEditInputMessage.setFor("nomeEditInput");
        nomeEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(nomeEditInputMessage);
        
        OutputLabel apelidoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        apelidoEditOutput.setFor("apelidoEditInput");
        apelidoEditOutput.setId("apelidoEditOutput");
        apelidoEditOutput.setValue("Apelido:");
        htmlPanelGrid.getChildren().add(apelidoEditOutput);
        
        InputText apelidoEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        apelidoEditInput.setId("apelidoEditInput");
        apelidoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.apelido}", String.class));
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
        
        OutputLabel cnpjEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cnpjEditOutput.setFor("cnpjEditInput");
        cnpjEditOutput.setId("cnpjEditOutput");
        cnpjEditOutput.setValue("Cnpj:");
        htmlPanelGrid.getChildren().add(cnpjEditOutput);
        
        InputText cnpjEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cnpjEditInput.setId("cnpjEditInput");
        cnpjEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.cnpj}", String.class));
        LengthValidator cnpjEditInputValidator = new LengthValidator();
        cnpjEditInputValidator.setMaximum(18);
        cnpjEditInput.addValidator(cnpjEditInputValidator);
        cnpjEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(cnpjEditInput);
        
        Message cnpjEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cnpjEditInputMessage.setId("cnpjEditInputMessage");
        cnpjEditInputMessage.setFor("cnpjEditInput");
        cnpjEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cnpjEditInputMessage);
        
        OutputLabel situacaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoEditOutput.setFor("situacaoEditInput");
        situacaoEditOutput.setId("situacaoEditOutput");
        situacaoEditOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoEditOutput);
        
        SelectBooleanCheckbox situacaoEditInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoEditInput.setId("situacaoEditInput");
        situacaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.situacao}", Boolean.class));
        situacaoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(situacaoEditInput);
        
        Message situacaoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        situacaoEditInputMessage.setId("situacaoEditInputMessage");
        situacaoEditInputMessage.setFor("situacaoEditInput");
        situacaoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(situacaoEditInputMessage);
        
        OutputLabel produtosFornecidosEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        produtosFornecidosEditOutput.setFor("produtosFornecidosEditInput");
        produtosFornecidosEditOutput.setId("produtosFornecidosEditOutput");
        produtosFornecidosEditOutput.setValue("Produtos Fornecidos:");
        htmlPanelGrid.getChildren().add(produtosFornecidosEditOutput);
        
        AutoComplete produtosFornecidosEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        produtosFornecidosEditInput.setId("produtosFornecidosEditInput");
        produtosFornecidosEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.produtosFornecidos}", Despesas.class));
        produtosFornecidosEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{fornecedoresBean.completeProdutosFornecidos}", List.class, new Class[] { String.class }));
        produtosFornecidosEditInput.setDropdown(true);
        produtosFornecidosEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "produtosFornecidos", String.class));
        produtosFornecidosEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{produtosFornecidos.codigo} #{produtosFornecidos.descricao} #{produtosFornecidos.idResumo}", String.class));
        produtosFornecidosEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{produtosFornecidos}", Despesas.class));
        produtosFornecidosEditInput.setConverter(new DespesasConverter());
        produtosFornecidosEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(produtosFornecidosEditInput);
        
        Message produtosFornecidosEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        produtosFornecidosEditInputMessage.setId("produtosFornecidosEditInputMessage");
        produtosFornecidosEditInputMessage.setFor("produtosFornecidosEditInput");
        produtosFornecidosEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(produtosFornecidosEditInputMessage);
        
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
        nomeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.nome}", String.class));
        htmlPanelGrid.getChildren().add(nomeValue);
        
        HtmlOutputText apelidoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        apelidoLabel.setId("apelidoLabel");
        apelidoLabel.setValue("Apelido:");
        htmlPanelGrid.getChildren().add(apelidoLabel);
        
        HtmlOutputText apelidoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        apelidoValue.setId("apelidoValue");
        apelidoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.apelido}", String.class));
        htmlPanelGrid.getChildren().add(apelidoValue);
        
        HtmlOutputText cnpjLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cnpjLabel.setId("cnpjLabel");
        cnpjLabel.setValue("Cnpj:");
        htmlPanelGrid.getChildren().add(cnpjLabel);
        
        HtmlOutputText cnpjValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cnpjValue.setId("cnpjValue");
        cnpjValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.cnpj}", String.class));
        htmlPanelGrid.getChildren().add(cnpjValue);
        
        HtmlOutputText situacaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoLabel.setId("situacaoLabel");
        situacaoLabel.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoLabel);
        
        HtmlOutputText situacaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.situacao}", String.class));
        htmlPanelGrid.getChildren().add(situacaoValue);
        
        HtmlOutputText produtosFornecidosLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        produtosFornecidosLabel.setId("produtosFornecidosLabel");
        produtosFornecidosLabel.setValue("Produtos Fornecidos:");
        htmlPanelGrid.getChildren().add(produtosFornecidosLabel);
        
        HtmlOutputText produtosFornecidosValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        produtosFornecidosValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{fornecedoresBean.fornecedores.produtosFornecidos}", Despesas.class));
        produtosFornecidosValue.setConverter(new DespesasConverter());
        htmlPanelGrid.getChildren().add(produtosFornecidosValue);
        
        return htmlPanelGrid;
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
        findAllFornecedoreses();
        return "fornecedores";
    }

	public String displayCreateDialog() {
        fornecedores = new Fornecedores();
        createDialogVisible = true;
        return "fornecedores";
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Fornecedores");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllFornecedoreses();
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
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}
