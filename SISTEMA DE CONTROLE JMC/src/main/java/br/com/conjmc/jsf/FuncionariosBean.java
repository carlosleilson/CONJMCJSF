package br.com.conjmc.jsf;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.validator.LengthValidator;

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.conjmc.cadastrobasico.Cargos;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.jsf.converter.CargosConverter;
import br.com.conjmc.jsf.util.MessageFactory;

@Configurable
@ManagedBean(name = "funcionariosBean")
@SessionScoped
public class FuncionariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = "Funcionarioses";

	private Funcionarios funcionarios;

	private List<Funcionarios> allFuncionarioses;
	
	private List<Funcionarios> allFuncionariosAtivos;

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
        columns.add("salario");
        columns.add("cpf");
        columns.add("identidade");
        funcionarios = new Funcionarios();
        funcionarios.setSituacao(true);
        findAllFuncionarioses();
        findAllFuncionariosAtivos();
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Funcionarios> getAllFuncionarioses() {
        return allFuncionarioses;
    }

	public void setAllFuncionarioses(List<Funcionarios> allFuncionarioses) {
        this.allFuncionarioses = allFuncionarioses;
    }

	public List<Funcionarios> getAllFuncionariosAtivos() {
		return allFuncionariosAtivos;
	}

	public void setAllFuncionariosAtivos(List<Funcionarios> allFuncionariosAtivos) {
		this.allFuncionariosAtivos = allFuncionariosAtivos;
	}

	public String findAllFuncionarioses() {
        allFuncionarioses = Funcionarios.findAllFuncionarioses();
        dataVisible = !allFuncionarioses.isEmpty();
        return null;
    }
	
	public void findAllFuncionariosAtivos() {
        allFuncionariosAtivos = Funcionarios.findAllFuncionariosAtivos();
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
        nomeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.nome}", String.class));
        LengthValidator nomeCreateInputValidator = new LengthValidator();
        nomeCreateInputValidator.setMinimum(10);
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
        apelidoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.apelido}", String.class));
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
        
        OutputLabel cargoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cargoCreateOutput.setFor("cargoCreateInput");
        cargoCreateOutput.setId("cargoCreateOutput");
        cargoCreateOutput.setValue("Cargo:");
        htmlPanelGrid.getChildren().add(cargoCreateOutput);
        
        AutoComplete cargoCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        cargoCreateInput.setId("cargoCreateInput");
        cargoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.cargo}", Cargos.class));
        cargoCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{funcionariosBean.completeCargo}", List.class, new Class[] { String.class }));
        cargoCreateInput.setDropdown(true);
        cargoCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "cargo", String.class));
        cargoCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{cargo.nome}", String.class));
        cargoCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{cargo}", Cargos.class));
        cargoCreateInput.setConverter(new CargosConverter());
        cargoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(cargoCreateInput);
        
        Message cargoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cargoCreateInputMessage.setId("cargoCreateInputMessage");
        cargoCreateInputMessage.setFor("cargoCreateInput");
        cargoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cargoCreateInputMessage);
        
        OutputLabel salarioCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        salarioCreateOutput.setFor("salarioCreateInput");
        salarioCreateOutput.setId("salarioCreateOutput");
        salarioCreateOutput.setValue("Salario:");
        htmlPanelGrid.getChildren().add(salarioCreateOutput);
        
        InputText salarioCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        salarioCreateInput.setId("salarioCreateInput");
        salarioCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.salario}", Double.class));
        salarioCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(salarioCreateInput);
        
        Message salarioCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        salarioCreateInputMessage.setId("salarioCreateInputMessage");
        salarioCreateInputMessage.setFor("salarioCreateInput");
        salarioCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(salarioCreateInputMessage);
        
        OutputLabel situacaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoCreateOutput.setFor("situacaoCreateInput");
        situacaoCreateOutput.setId("situacaoCreateOutput");
        situacaoCreateOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoCreateOutput);
        
        SelectBooleanCheckbox situacaoCreateInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoCreateInput.setId("situacaoCreateInput");
        situacaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.situacao}", Boolean.class));
        situacaoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(situacaoCreateInput);
        
        Message situacaoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        situacaoCreateInputMessage.setId("situacaoCreateInputMessage");
        situacaoCreateInputMessage.setFor("situacaoCreateInput");
        situacaoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(situacaoCreateInputMessage);
        
        OutputLabel cpfCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cpfCreateOutput.setFor("cpfCreateInput");
        cpfCreateOutput.setId("cpfCreateOutput");
        cpfCreateOutput.setValue("Cpf:");
        htmlPanelGrid.getChildren().add(cpfCreateOutput);
        
        InputText cpfCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cpfCreateInput.setId("cpfCreateInput");
        cpfCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.cpf}", String.class));
        LengthValidator cpfCreateInputValidator = new LengthValidator();
        cpfCreateInputValidator.setMaximum(15);
        cpfCreateInput.addValidator(cpfCreateInputValidator);
        cpfCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(cpfCreateInput);
        
        Message cpfCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cpfCreateInputMessage.setId("cpfCreateInputMessage");
        cpfCreateInputMessage.setFor("cpfCreateInput");
        cpfCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cpfCreateInputMessage);
        
        OutputLabel identidadeCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        identidadeCreateOutput.setFor("identidadeCreateInput");
        identidadeCreateOutput.setId("identidadeCreateOutput");
        identidadeCreateOutput.setValue("Identidade:");
        htmlPanelGrid.getChildren().add(identidadeCreateOutput);
        
        InputText identidadeCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        identidadeCreateInput.setId("identidadeCreateInput");
        identidadeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.identidade}", String.class));
        LengthValidator identidadeCreateInputValidator = new LengthValidator();
        identidadeCreateInputValidator.setMaximum(30);
        identidadeCreateInput.addValidator(identidadeCreateInputValidator);
        identidadeCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(identidadeCreateInput);
        
        Message identidadeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        identidadeCreateInputMessage.setId("identidadeCreateInputMessage");
        identidadeCreateInputMessage.setFor("identidadeCreateInput");
        identidadeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(identidadeCreateInputMessage);
        
        OutputLabel logradouroCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        logradouroCreateOutput.setFor("logradouroCreateInput");
        logradouroCreateOutput.setId("logradouroCreateOutput");
        logradouroCreateOutput.setValue("Logradouro:");
        htmlPanelGrid.getChildren().add(logradouroCreateOutput);
        
        InputText logradouroCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        logradouroCreateInput.setId("logradouroCreateInput");
        logradouroCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.logradouro}", String.class));
        logradouroCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(logradouroCreateInput);
        
        Message logradouroCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        logradouroCreateInputMessage.setId("logradouroCreateInputMessage");
        logradouroCreateInputMessage.setFor("logradouroCreateInput");
        logradouroCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(logradouroCreateInputMessage);
        
        OutputLabel bairroCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        bairroCreateOutput.setFor("bairroCreateInput");
        bairroCreateOutput.setId("bairroCreateOutput");
        bairroCreateOutput.setValue("Bairro:");
        htmlPanelGrid.getChildren().add(bairroCreateOutput);
        
        InputText bairroCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        bairroCreateInput.setId("bairroCreateInput");
        bairroCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.bairro}", String.class));
        bairroCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(bairroCreateInput);
        
        Message bairroCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        bairroCreateInputMessage.setId("bairroCreateInputMessage");
        bairroCreateInputMessage.setFor("bairroCreateInput");
        bairroCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(bairroCreateInputMessage);
        
        OutputLabel cidadeCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cidadeCreateOutput.setFor("cidadeCreateInput");
        cidadeCreateOutput.setId("cidadeCreateOutput");
        cidadeCreateOutput.setValue("Cidade:");
        htmlPanelGrid.getChildren().add(cidadeCreateOutput);
        
        InputText cidadeCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cidadeCreateInput.setId("cidadeCreateInput");
        cidadeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.cidade}", String.class));
        cidadeCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(cidadeCreateInput);
        
        Message cidadeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cidadeCreateInputMessage.setId("cidadeCreateInputMessage");
        cidadeCreateInputMessage.setFor("cidadeCreateInput");
        cidadeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cidadeCreateInputMessage);
        
        OutputLabel cepCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cepCreateOutput.setFor("cepCreateInput");
        cepCreateOutput.setId("cepCreateOutput");
        cepCreateOutput.setValue("Cep:");
        htmlPanelGrid.getChildren().add(cepCreateOutput);
        
        InputText cepCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cepCreateInput.setId("cepCreateInput");
        cepCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.cep}", String.class));
        LengthValidator cepCreateInputValidator = new LengthValidator();
        cepCreateInputValidator.setMaximum(10);
        cepCreateInput.addValidator(cepCreateInputValidator);
        cepCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(cepCreateInput);
        
        Message cepCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cepCreateInputMessage.setId("cepCreateInputMessage");
        cepCreateInputMessage.setFor("cepCreateInput");
        cepCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cepCreateInputMessage);
        
        OutputLabel outrasInformacoesCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        outrasInformacoesCreateOutput.setFor("outrasInformacoesCreateInput");
        outrasInformacoesCreateOutput.setId("outrasInformacoesCreateOutput");
        outrasInformacoesCreateOutput.setValue("Outras Informacoes:");
        htmlPanelGrid.getChildren().add(outrasInformacoesCreateOutput);
        
        InputTextarea outrasInformacoesCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        outrasInformacoesCreateInput.setId("outrasInformacoesCreateInput");
        outrasInformacoesCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.outrasInformacoes}", String.class));
        LengthValidator outrasInformacoesCreateInputValidator = new LengthValidator();
        outrasInformacoesCreateInputValidator.setMaximum(120);
        outrasInformacoesCreateInput.addValidator(outrasInformacoesCreateInputValidator);
        outrasInformacoesCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(outrasInformacoesCreateInput);
        
        Message outrasInformacoesCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        outrasInformacoesCreateInputMessage.setId("outrasInformacoesCreateInputMessage");
        outrasInformacoesCreateInputMessage.setFor("outrasInformacoesCreateInput");
        outrasInformacoesCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(outrasInformacoesCreateInputMessage);
        
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
        nomeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.nome}", String.class));
        LengthValidator nomeEditInputValidator = new LengthValidator();
        nomeEditInputValidator.setMinimum(10);
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
        apelidoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.apelido}", String.class));
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
        
        OutputLabel cargoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cargoEditOutput.setFor("cargoEditInput");
        cargoEditOutput.setId("cargoEditOutput");
        cargoEditOutput.setValue("Cargo:");
        htmlPanelGrid.getChildren().add(cargoEditOutput);
        
        AutoComplete cargoEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        cargoEditInput.setId("cargoEditInput");
        cargoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.cargo}", Cargos.class));
        cargoEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{funcionariosBean.completeCargo}", List.class, new Class[] { String.class }));
        cargoEditInput.setDropdown(true);
        cargoEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "cargo", String.class));
        cargoEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{cargo.nome}", String.class));
        cargoEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{cargo}", Cargos.class));
        cargoEditInput.setConverter(new CargosConverter());
        cargoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(cargoEditInput);
        
        Message cargoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cargoEditInputMessage.setId("cargoEditInputMessage");
        cargoEditInputMessage.setFor("cargoEditInput");
        cargoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cargoEditInputMessage);
        
        OutputLabel salarioEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        salarioEditOutput.setFor("salarioEditInput");
        salarioEditOutput.setId("salarioEditOutput");
        salarioEditOutput.setValue("Salario:");
        htmlPanelGrid.getChildren().add(salarioEditOutput);
        
        InputText salarioEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        salarioEditInput.setId("salarioEditInput");
        salarioEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.salario}", Double.class));
        salarioEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(salarioEditInput);
        
        Message salarioEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        salarioEditInputMessage.setId("salarioEditInputMessage");
        salarioEditInputMessage.setFor("salarioEditInput");
        salarioEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(salarioEditInputMessage);
        
        OutputLabel situacaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        situacaoEditOutput.setFor("situacaoEditInput");
        situacaoEditOutput.setId("situacaoEditOutput");
        situacaoEditOutput.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoEditOutput);
        
        SelectBooleanCheckbox situacaoEditInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        situacaoEditInput.setId("situacaoEditInput");
        situacaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.situacao}", Boolean.class));
        situacaoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(situacaoEditInput);
        
        Message situacaoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        situacaoEditInputMessage.setId("situacaoEditInputMessage");
        situacaoEditInputMessage.setFor("situacaoEditInput");
        situacaoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(situacaoEditInputMessage);
        
        OutputLabel cpfEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cpfEditOutput.setFor("cpfEditInput");
        cpfEditOutput.setId("cpfEditOutput");
        cpfEditOutput.setValue("Cpf:");
        htmlPanelGrid.getChildren().add(cpfEditOutput);
        
        InputText cpfEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cpfEditInput.setId("cpfEditInput");
        cpfEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.cpf}", String.class));
        LengthValidator cpfEditInputValidator = new LengthValidator();
        cpfEditInputValidator.setMaximum(15);
        cpfEditInput.addValidator(cpfEditInputValidator);
        cpfEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(cpfEditInput);
        
        Message cpfEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cpfEditInputMessage.setId("cpfEditInputMessage");
        cpfEditInputMessage.setFor("cpfEditInput");
        cpfEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cpfEditInputMessage);
        
        OutputLabel identidadeEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        identidadeEditOutput.setFor("identidadeEditInput");
        identidadeEditOutput.setId("identidadeEditOutput");
        identidadeEditOutput.setValue("Identidade:");
        htmlPanelGrid.getChildren().add(identidadeEditOutput);
        
        InputText identidadeEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        identidadeEditInput.setId("identidadeEditInput");
        identidadeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.identidade}", String.class));
        LengthValidator identidadeEditInputValidator = new LengthValidator();
        identidadeEditInputValidator.setMaximum(30);
        identidadeEditInput.addValidator(identidadeEditInputValidator);
        identidadeEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(identidadeEditInput);
        
        Message identidadeEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        identidadeEditInputMessage.setId("identidadeEditInputMessage");
        identidadeEditInputMessage.setFor("identidadeEditInput");
        identidadeEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(identidadeEditInputMessage);
        
        OutputLabel logradouroEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        logradouroEditOutput.setFor("logradouroEditInput");
        logradouroEditOutput.setId("logradouroEditOutput");
        logradouroEditOutput.setValue("Logradouro:");
        htmlPanelGrid.getChildren().add(logradouroEditOutput);
        
        InputText logradouroEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        logradouroEditInput.setId("logradouroEditInput");
        logradouroEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.logradouro}", String.class));
        logradouroEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(logradouroEditInput);
        
        Message logradouroEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        logradouroEditInputMessage.setId("logradouroEditInputMessage");
        logradouroEditInputMessage.setFor("logradouroEditInput");
        logradouroEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(logradouroEditInputMessage);
        
        OutputLabel bairroEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        bairroEditOutput.setFor("bairroEditInput");
        bairroEditOutput.setId("bairroEditOutput");
        bairroEditOutput.setValue("Bairro:");
        htmlPanelGrid.getChildren().add(bairroEditOutput);
        
        InputText bairroEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        bairroEditInput.setId("bairroEditInput");
        bairroEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.bairro}", String.class));
        bairroEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(bairroEditInput);
        
        Message bairroEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        bairroEditInputMessage.setId("bairroEditInputMessage");
        bairroEditInputMessage.setFor("bairroEditInput");
        bairroEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(bairroEditInputMessage);
        
        OutputLabel cidadeEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cidadeEditOutput.setFor("cidadeEditInput");
        cidadeEditOutput.setId("cidadeEditOutput");
        cidadeEditOutput.setValue("Cidade:");
        htmlPanelGrid.getChildren().add(cidadeEditOutput);
        
        InputText cidadeEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cidadeEditInput.setId("cidadeEditInput");
        cidadeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.cidade}", String.class));
        cidadeEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(cidadeEditInput);
        
        Message cidadeEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cidadeEditInputMessage.setId("cidadeEditInputMessage");
        cidadeEditInputMessage.setFor("cidadeEditInput");
        cidadeEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cidadeEditInputMessage);
        
        OutputLabel cepEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cepEditOutput.setFor("cepEditInput");
        cepEditOutput.setId("cepEditOutput");
        cepEditOutput.setValue("Cep:");
        htmlPanelGrid.getChildren().add(cepEditOutput);
        
        InputText cepEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cepEditInput.setId("cepEditInput");
        cepEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.cep}", String.class));
        LengthValidator cepEditInputValidator = new LengthValidator();
        cepEditInputValidator.setMaximum(10);
        cepEditInput.addValidator(cepEditInputValidator);
        cepEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(cepEditInput);
        
        Message cepEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cepEditInputMessage.setId("cepEditInputMessage");
        cepEditInputMessage.setFor("cepEditInput");
        cepEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cepEditInputMessage);
        
        OutputLabel outrasInformacoesEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        outrasInformacoesEditOutput.setFor("outrasInformacoesEditInput");
        outrasInformacoesEditOutput.setId("outrasInformacoesEditOutput");
        outrasInformacoesEditOutput.setValue("Outras Informacoes:");
        htmlPanelGrid.getChildren().add(outrasInformacoesEditOutput);
        
        InputTextarea outrasInformacoesEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        outrasInformacoesEditInput.setId("outrasInformacoesEditInput");
        outrasInformacoesEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.outrasInformacoes}", String.class));
        LengthValidator outrasInformacoesEditInputValidator = new LengthValidator();
        outrasInformacoesEditInputValidator.setMaximum(120);
        outrasInformacoesEditInput.addValidator(outrasInformacoesEditInputValidator);
        outrasInformacoesEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(outrasInformacoesEditInput);
        
        Message outrasInformacoesEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        outrasInformacoesEditInputMessage.setId("outrasInformacoesEditInputMessage");
        outrasInformacoesEditInputMessage.setFor("outrasInformacoesEditInput");
        outrasInformacoesEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(outrasInformacoesEditInputMessage);
        
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
        nomeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.nome}", String.class));
        htmlPanelGrid.getChildren().add(nomeValue);
        
        HtmlOutputText apelidoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        apelidoLabel.setId("apelidoLabel");
        apelidoLabel.setValue("Apelido:");
        htmlPanelGrid.getChildren().add(apelidoLabel);
        
        HtmlOutputText apelidoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        apelidoValue.setId("apelidoValue");
        apelidoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.apelido}", String.class));
        htmlPanelGrid.getChildren().add(apelidoValue);
        
        HtmlOutputText cargoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cargoLabel.setId("cargoLabel");
        cargoLabel.setValue("Cargo:");
        htmlPanelGrid.getChildren().add(cargoLabel);
        
        HtmlOutputText cargoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cargoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.cargo}", Cargos.class));
        cargoValue.setConverter(new CargosConverter());
        htmlPanelGrid.getChildren().add(cargoValue);
        
        HtmlOutputText salarioLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        salarioLabel.setId("salarioLabel");
        salarioLabel.setValue("Salario:");
        htmlPanelGrid.getChildren().add(salarioLabel);
        
        HtmlOutputText salarioValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        salarioValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.salario}", String.class));
        htmlPanelGrid.getChildren().add(salarioValue);
        
        HtmlOutputText situacaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoLabel.setId("situacaoLabel");
        situacaoLabel.setValue("Situacao:");
        htmlPanelGrid.getChildren().add(situacaoLabel);
        
        HtmlOutputText situacaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        situacaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.situacao}", String.class));
        htmlPanelGrid.getChildren().add(situacaoValue);
        
        HtmlOutputText cpfLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cpfLabel.setId("cpfLabel");
        cpfLabel.setValue("Cpf:");
        htmlPanelGrid.getChildren().add(cpfLabel);
        
        HtmlOutputText cpfValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cpfValue.setId("cpfValue");
        cpfValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.cpf}", String.class));
        htmlPanelGrid.getChildren().add(cpfValue);
        
        HtmlOutputText identidadeLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        identidadeLabel.setId("identidadeLabel");
        identidadeLabel.setValue("Identidade:");
        htmlPanelGrid.getChildren().add(identidadeLabel);
        
        HtmlOutputText identidadeValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        identidadeValue.setId("identidadeValue");
        identidadeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.identidade}", String.class));
        htmlPanelGrid.getChildren().add(identidadeValue);
        
        HtmlOutputText logradouroLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        logradouroLabel.setId("logradouroLabel");
        logradouroLabel.setValue("Logradouro:");
        htmlPanelGrid.getChildren().add(logradouroLabel);
        
        HtmlOutputText logradouroValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        logradouroValue.setId("logradouroValue");
        logradouroValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.logradouro}", String.class));
        htmlPanelGrid.getChildren().add(logradouroValue);
        
        HtmlOutputText bairroLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        bairroLabel.setId("bairroLabel");
        bairroLabel.setValue("Bairro:");
        htmlPanelGrid.getChildren().add(bairroLabel);
        
        HtmlOutputText bairroValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        bairroValue.setId("bairroValue");
        bairroValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.bairro}", String.class));
        htmlPanelGrid.getChildren().add(bairroValue);
        
        HtmlOutputText cidadeLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cidadeLabel.setId("cidadeLabel");
        cidadeLabel.setValue("Cidade:");
        htmlPanelGrid.getChildren().add(cidadeLabel);
        
        HtmlOutputText cidadeValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cidadeValue.setId("cidadeValue");
        cidadeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.cidade}", String.class));
        htmlPanelGrid.getChildren().add(cidadeValue);
        
        HtmlOutputText cepLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cepLabel.setId("cepLabel");
        cepLabel.setValue("Cep:");
        htmlPanelGrid.getChildren().add(cepLabel);
        
        HtmlOutputText cepValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cepValue.setId("cepValue");
        cepValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.cep}", String.class));
        htmlPanelGrid.getChildren().add(cepValue);
        
        HtmlOutputText outrasInformacoesLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        outrasInformacoesLabel.setId("outrasInformacoesLabel");
        outrasInformacoesLabel.setValue("Outras Informacoes:");
        htmlPanelGrid.getChildren().add(outrasInformacoesLabel);
        
        InputTextarea outrasInformacoesValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        outrasInformacoesValue.setId("outrasInformacoesValue");
        outrasInformacoesValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{funcionariosBean.funcionarios.outrasInformacoes}", String.class));
        outrasInformacoesValue.setReadonly(true);
        outrasInformacoesValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(outrasInformacoesValue);
        
        return htmlPanelGrid;
    }

	public Funcionarios getFuncionarios() {
        if (funcionarios == null) {
            funcionarios = new Funcionarios();
        }
        return funcionarios;
    }

	public void setFuncionarios(Funcionarios funcionarios) {
        this.funcionarios = funcionarios;
    }

	public List<Cargos> completeCargo(String query) {
        List<Cargos> suggestions = new ArrayList<Cargos>();
        for (Cargos cargos : Cargos.findAllCargoses()) {
            String cargosStr = String.valueOf(cargos.getNome());
            if (cargosStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(cargos);
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
        findAllFuncionarioses();
        return "funcionarios";
    }

	public String displayCreateDialog() {
        funcionarios = new Funcionarios();
        createDialogVisible = true;
        return "funcionarios";
    }

	public String persist() {
		boolean existe = true;
		String message = "";
		for (Funcionarios func : allFuncionarioses) {
			if (func.getApelido().equals(funcionarios.getApelido())) {
				existe = false;
				message = "já existe funcionário com apelido cadastrado";
			} else if (func.getCpf().equals(funcionarios.getCpf())) {
				existe = false;
				message = "Já existe CPF com funcionário cadastrado";
			}
		}
		
		if (funcionarios.getId() != null) {
			boolean merge = true;
			for (Funcionarios func : allFuncionarioses) {
				if ((func.getApelido().equals(funcionarios.getApelido()) && (func.getId() != funcionarios.getId()))) {
					merge = false;
					message = "já existe funcionário com apelido cadastrado";
				} else if ((func.getCpf().equals(funcionarios.getCpf()) && (func.getId() != funcionarios.getId()))) {
					merge = false;
					message = "Já existe CPF com funcionário cadastrado";
				}
			}
			if (merge) {
				funcionarios.merge();
				message = "message_successfully_updated";
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("createDialogWidget.hide()");
				context.execute("editDialogWidget.hide()");			
				FacesMessage facesMessage = MessageFactory.getMessage(message, "Funcionarios");
				FacesContext.getCurrentInstance().addMessage(null, facesMessage);
				init();
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, " "));
				findAllFuncionarioses();
			}
		} else {
			if (existe) {
				funcionarios.persist();
				message = "message_successfully_created";
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("createDialogWidget.hide()");
				context.execute("editDialogWidget.hide()");			
				FacesMessage facesMessage = MessageFactory.getMessage(message, "Funcionarios");
				FacesContext.getCurrentInstance().addMessage(null, facesMessage);
				init();
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, " "));
			}
		}
        return "/pages/funcionarios.xhtml";
    }

	public String delete() {
		try {
			funcionarios.remove();
			FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "DespesasGastos");
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        init();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O item não pode ser deletado porque possui dependências em outros módulos", "O item não pode ser deletado porque possui dependências em outros módulos"));
		}
        return "funcionarios.xhtml";
    }

	public void reset() {
        funcionarios = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}
