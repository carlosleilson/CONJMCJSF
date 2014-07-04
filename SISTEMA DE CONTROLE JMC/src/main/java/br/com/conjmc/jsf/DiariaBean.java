package br.com.conjmc.jsf;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.controlediario.fechamento.Cobrar;
import br.com.conjmc.controlediario.fechamento.Diaria;
import br.com.conjmc.controlediario.fechamento.Receber;
import br.com.conjmc.jsf.converter.CobrarConverter;
import br.com.conjmc.jsf.converter.FuncionariosConverter;
import br.com.conjmc.jsf.converter.ReceberConverter;
import br.com.conjmc.jsf.converter.SangriaConverter;
import br.com.conjmc.jsf.util.MessageFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.faces.convert.DateTimeConverter;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.spinner.Spinner;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@ManagedBean(name = "diariaBean")
@SessionScoped
@Configurable
@RooSerializable
@RooJsfManagedBean(entity = Diaria.class, beanName = "diariaBean")
public class DiariaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = "Fechamento de Diarias";

	private Diaria diaria;

	private List<Diaria> allDiarias;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("periodo");
        columns.add("valorBalcao");
        columns.add("qtdeValorBalcao");
        columns.add("valorWeb");
        columns.add("qtdeValorWeb");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Diaria> getAllDiarias() {
        return allDiarias;
    }

	public void setAllDiarias(List<Diaria> allDiarias) {
        this.allDiarias = allDiarias;
    }

	public String findAllDiarias() {
        allDiarias = Diaria.findAllDiarias();
        dataVisible = !allDiarias.isEmpty();
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
        
        OutputLabel periodoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        periodoCreateOutput.setFor("periodoCreateInput");
        periodoCreateOutput.setId("periodoCreateOutput");
        periodoCreateOutput.setValue("Periodo:");
        htmlPanelGrid.getChildren().add(periodoCreateOutput);
        
        Calendar periodoCreateInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        periodoCreateInput.setId("periodoCreateInput");
        periodoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.periodo}", Date.class));
        periodoCreateInput.setNavigator(true);
        periodoCreateInput.setEffect("slideDown");
        periodoCreateInput.setPattern("dd/MM/yyyy");
        periodoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(periodoCreateInput);
        
        Message periodoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        periodoCreateInputMessage.setId("periodoCreateInputMessage");
        periodoCreateInputMessage.setFor("periodoCreateInput");
        periodoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(periodoCreateInputMessage);
        
        OutputLabel caixaResponsavelCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        caixaResponsavelCreateOutput.setFor("caixaResponsavelCreateInput");
        caixaResponsavelCreateOutput.setId("caixaResponsavelCreateOutput");
        caixaResponsavelCreateOutput.setValue("Caixa Responsavel:");
        htmlPanelGrid.getChildren().add(caixaResponsavelCreateOutput);
        
        AutoComplete caixaResponsavelCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        caixaResponsavelCreateInput.setId("caixaResponsavelCreateInput");
        caixaResponsavelCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.caixaResponsavel}", Funcionarios.class));
        caixaResponsavelCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{diariaBean.completeCaixaResponsavel}", List.class, new Class[] { String.class }));
        caixaResponsavelCreateInput.setDropdown(true);
        caixaResponsavelCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "caixaResponsavel", String.class));
        caixaResponsavelCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{caixaResponsavel.nome} #{caixaResponsavel.apelido} #{caixaResponsavel.salario} #{caixaResponsavel.cpf}", String.class));
        caixaResponsavelCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{caixaResponsavel}", Funcionarios.class));
        caixaResponsavelCreateInput.setConverter(new FuncionariosConverter());
        caixaResponsavelCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(caixaResponsavelCreateInput);
        
        Message caixaResponsavelCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        caixaResponsavelCreateInputMessage.setId("caixaResponsavelCreateInputMessage");
        caixaResponsavelCreateInputMessage.setFor("caixaResponsavelCreateInput");
        caixaResponsavelCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(caixaResponsavelCreateInputMessage);
        
        OutputLabel valorBalcaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorBalcaoCreateOutput.setFor("valorBalcaoCreateInput");
        valorBalcaoCreateOutput.setId("valorBalcaoCreateOutput");
        valorBalcaoCreateOutput.setValue("Valor Balcao:");
        htmlPanelGrid.getChildren().add(valorBalcaoCreateOutput);
        
        InputText valorBalcaoCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorBalcaoCreateInput.setId("valorBalcaoCreateInput");
        valorBalcaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorBalcao}", Double.class));
        valorBalcaoCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorBalcaoCreateInput);
        
        Message valorBalcaoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorBalcaoCreateInputMessage.setId("valorBalcaoCreateInputMessage");
        valorBalcaoCreateInputMessage.setFor("valorBalcaoCreateInput");
        valorBalcaoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorBalcaoCreateInputMessage);
        
        OutputLabel qtdeValorBalcaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        qtdeValorBalcaoCreateOutput.setFor("qtdeValorBalcaoCreateInput");
        qtdeValorBalcaoCreateOutput.setId("qtdeValorBalcaoCreateOutput");
        qtdeValorBalcaoCreateOutput.setValue("Qtde Valor Balcao:");
        htmlPanelGrid.getChildren().add(qtdeValorBalcaoCreateOutput);
        
        Spinner qtdeValorBalcaoCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        qtdeValorBalcaoCreateInput.setId("qtdeValorBalcaoCreateInput");
        qtdeValorBalcaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorBalcao}", Integer.class));
        qtdeValorBalcaoCreateInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(qtdeValorBalcaoCreateInput);
        
        Message qtdeValorBalcaoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        qtdeValorBalcaoCreateInputMessage.setId("qtdeValorBalcaoCreateInputMessage");
        qtdeValorBalcaoCreateInputMessage.setFor("qtdeValorBalcaoCreateInput");
        qtdeValorBalcaoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(qtdeValorBalcaoCreateInputMessage);
        
        OutputLabel valorWebCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorWebCreateOutput.setFor("valorWebCreateInput");
        valorWebCreateOutput.setId("valorWebCreateOutput");
        valorWebCreateOutput.setValue("Valor Web:");
        htmlPanelGrid.getChildren().add(valorWebCreateOutput);
        
        InputText valorWebCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorWebCreateInput.setId("valorWebCreateInput");
        valorWebCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorWeb}", Double.class));
        valorWebCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorWebCreateInput);
        
        Message valorWebCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorWebCreateInputMessage.setId("valorWebCreateInputMessage");
        valorWebCreateInputMessage.setFor("valorWebCreateInput");
        valorWebCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorWebCreateInputMessage);
        
        OutputLabel qtdeValorWebCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        qtdeValorWebCreateOutput.setFor("qtdeValorWebCreateInput");
        qtdeValorWebCreateOutput.setId("qtdeValorWebCreateOutput");
        qtdeValorWebCreateOutput.setValue("Qtde Valor Web:");
        htmlPanelGrid.getChildren().add(qtdeValorWebCreateOutput);
        
        Spinner qtdeValorWebCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        qtdeValorWebCreateInput.setId("qtdeValorWebCreateInput");
        qtdeValorWebCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorWeb}", Integer.class));
        qtdeValorWebCreateInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(qtdeValorWebCreateInput);
        
        Message qtdeValorWebCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        qtdeValorWebCreateInputMessage.setId("qtdeValorWebCreateInputMessage");
        qtdeValorWebCreateInputMessage.setFor("qtdeValorWebCreateInput");
        qtdeValorWebCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(qtdeValorWebCreateInputMessage);
        
        OutputLabel valorCentralCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorCentralCreateOutput.setFor("valorCentralCreateInput");
        valorCentralCreateOutput.setId("valorCentralCreateOutput");
        valorCentralCreateOutput.setValue("Valor Central:");
        htmlPanelGrid.getChildren().add(valorCentralCreateOutput);
        
        InputText valorCentralCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorCentralCreateInput.setId("valorCentralCreateInput");
        valorCentralCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorCentral}", Double.class));
        valorCentralCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorCentralCreateInput);
        
        Message valorCentralCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorCentralCreateInputMessage.setId("valorCentralCreateInputMessage");
        valorCentralCreateInputMessage.setFor("valorCentralCreateInput");
        valorCentralCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorCentralCreateInputMessage);
        
        OutputLabel qtdeValorCentralCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        qtdeValorCentralCreateOutput.setFor("qtdeValorCentralCreateInput");
        qtdeValorCentralCreateOutput.setId("qtdeValorCentralCreateOutput");
        qtdeValorCentralCreateOutput.setValue("Qtde Valor Central:");
        htmlPanelGrid.getChildren().add(qtdeValorCentralCreateOutput);
        
        Spinner qtdeValorCentralCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        qtdeValorCentralCreateInput.setId("qtdeValorCentralCreateInput");
        qtdeValorCentralCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorCentral}", Integer.class));
        qtdeValorCentralCreateInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(qtdeValorCentralCreateInput);
        
        Message qtdeValorCentralCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        qtdeValorCentralCreateInputMessage.setId("qtdeValorCentralCreateInputMessage");
        qtdeValorCentralCreateInputMessage.setFor("qtdeValorCentralCreateInput");
        qtdeValorCentralCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(qtdeValorCentralCreateInputMessage);
        
        OutputLabel valorMesaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorMesaCreateOutput.setFor("valorMesaCreateInput");
        valorMesaCreateOutput.setId("valorMesaCreateOutput");
        valorMesaCreateOutput.setValue("Valor Mesa:");
        htmlPanelGrid.getChildren().add(valorMesaCreateOutput);
        
        InputText valorMesaCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorMesaCreateInput.setId("valorMesaCreateInput");
        valorMesaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorMesa}", Double.class));
        valorMesaCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorMesaCreateInput);
        
        Message valorMesaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorMesaCreateInputMessage.setId("valorMesaCreateInputMessage");
        valorMesaCreateInputMessage.setFor("valorMesaCreateInput");
        valorMesaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorMesaCreateInputMessage);
        
        OutputLabel qtdeValorMesaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        qtdeValorMesaCreateOutput.setFor("qtdeValorMesaCreateInput");
        qtdeValorMesaCreateOutput.setId("qtdeValorMesaCreateOutput");
        qtdeValorMesaCreateOutput.setValue("Qtde Valor Mesa:");
        htmlPanelGrid.getChildren().add(qtdeValorMesaCreateOutput);
        
        Spinner qtdeValorMesaCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        qtdeValorMesaCreateInput.setId("qtdeValorMesaCreateInput");
        qtdeValorMesaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorMesa}", Integer.class));
        qtdeValorMesaCreateInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(qtdeValorMesaCreateInput);
        
        Message qtdeValorMesaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        qtdeValorMesaCreateInputMessage.setId("qtdeValorMesaCreateInputMessage");
        qtdeValorMesaCreateInputMessage.setFor("qtdeValorMesaCreateInput");
        qtdeValorMesaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(qtdeValorMesaCreateInputMessage);
        
        OutputLabel valorTaxaEntregaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorTaxaEntregaCreateOutput.setFor("valorTaxaEntregaCreateInput");
        valorTaxaEntregaCreateOutput.setId("valorTaxaEntregaCreateOutput");
        valorTaxaEntregaCreateOutput.setValue("Valor Taxa Entrega:");
        htmlPanelGrid.getChildren().add(valorTaxaEntregaCreateOutput);
        
        InputText valorTaxaEntregaCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorTaxaEntregaCreateInput.setId("valorTaxaEntregaCreateInput");
        valorTaxaEntregaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorTaxaEntrega}", Double.class));
        valorTaxaEntregaCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorTaxaEntregaCreateInput);
        
        Message valorTaxaEntregaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorTaxaEntregaCreateInputMessage.setId("valorTaxaEntregaCreateInputMessage");
        valorTaxaEntregaCreateInputMessage.setFor("valorTaxaEntregaCreateInput");
        valorTaxaEntregaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorTaxaEntregaCreateInputMessage);
        
        OutputLabel qtdeValorTaxaEntregaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        qtdeValorTaxaEntregaCreateOutput.setFor("qtdeValorTaxaEntregaCreateInput");
        qtdeValorTaxaEntregaCreateOutput.setId("qtdeValorTaxaEntregaCreateOutput");
        qtdeValorTaxaEntregaCreateOutput.setValue("Qtde Valor Taxa Entrega:");
        htmlPanelGrid.getChildren().add(qtdeValorTaxaEntregaCreateOutput);
        
        Spinner qtdeValorTaxaEntregaCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        qtdeValorTaxaEntregaCreateInput.setId("qtdeValorTaxaEntregaCreateInput");
        qtdeValorTaxaEntregaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorTaxaEntrega}", Integer.class));
        qtdeValorTaxaEntregaCreateInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(qtdeValorTaxaEntregaCreateInput);
        
        Message qtdeValorTaxaEntregaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        qtdeValorTaxaEntregaCreateInputMessage.setId("qtdeValorTaxaEntregaCreateInputMessage");
        qtdeValorTaxaEntregaCreateInputMessage.setFor("qtdeValorTaxaEntregaCreateInput");
        qtdeValorTaxaEntregaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(qtdeValorTaxaEntregaCreateInputMessage);
        
        OutputLabel valorSevicoMesaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorSevicoMesaCreateOutput.setFor("valorSevicoMesaCreateInput");
        valorSevicoMesaCreateOutput.setId("valorSevicoMesaCreateOutput");
        valorSevicoMesaCreateOutput.setValue("Valor Sevico Mesa:");
        htmlPanelGrid.getChildren().add(valorSevicoMesaCreateOutput);
        
        InputText valorSevicoMesaCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorSevicoMesaCreateInput.setId("valorSevicoMesaCreateInput");
        valorSevicoMesaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorSevicoMesa}", Double.class));
        valorSevicoMesaCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorSevicoMesaCreateInput);
        
        Message valorSevicoMesaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorSevicoMesaCreateInputMessage.setId("valorSevicoMesaCreateInputMessage");
        valorSevicoMesaCreateInputMessage.setFor("valorSevicoMesaCreateInput");
        valorSevicoMesaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorSevicoMesaCreateInputMessage);
        
        OutputLabel qtdeValorSevicoMesaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        qtdeValorSevicoMesaCreateOutput.setFor("qtdeValorSevicoMesaCreateInput");
        qtdeValorSevicoMesaCreateOutput.setId("qtdeValorSevicoMesaCreateOutput");
        qtdeValorSevicoMesaCreateOutput.setValue("Qtde Valor Sevico Mesa:");
        htmlPanelGrid.getChildren().add(qtdeValorSevicoMesaCreateOutput);
        
        Spinner qtdeValorSevicoMesaCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        qtdeValorSevicoMesaCreateInput.setId("qtdeValorSevicoMesaCreateInput");
        qtdeValorSevicoMesaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorSevicoMesa}", Integer.class));
        qtdeValorSevicoMesaCreateInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(qtdeValorSevicoMesaCreateInput);
        
        Message qtdeValorSevicoMesaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        qtdeValorSevicoMesaCreateInputMessage.setId("qtdeValorSevicoMesaCreateInputMessage");
        qtdeValorSevicoMesaCreateInputMessage.setFor("qtdeValorSevicoMesaCreateInput");
        qtdeValorSevicoMesaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(qtdeValorSevicoMesaCreateInputMessage);
        
        OutputLabel valorTotalFitaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorTotalFitaCreateOutput.setFor("valorTotalFitaCreateInput");
        valorTotalFitaCreateOutput.setId("valorTotalFitaCreateOutput");
        valorTotalFitaCreateOutput.setValue("Valor Total Fita:");
        htmlPanelGrid.getChildren().add(valorTotalFitaCreateOutput);
        
        InputText valorTotalFitaCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorTotalFitaCreateInput.setId("valorTotalFitaCreateInput");
        valorTotalFitaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorTotalFita}", Double.class));
        valorTotalFitaCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorTotalFitaCreateInput);
        
        Message valorTotalFitaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorTotalFitaCreateInputMessage.setId("valorTotalFitaCreateInputMessage");
        valorTotalFitaCreateInputMessage.setFor("valorTotalFitaCreateInput");
        valorTotalFitaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorTotalFitaCreateInputMessage);
        
        OutputLabel caixaInicialCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        caixaInicialCreateOutput.setFor("caixaInicialCreateInput");
        caixaInicialCreateOutput.setId("caixaInicialCreateOutput");
        caixaInicialCreateOutput.setValue("Caixa Inicial:");
        htmlPanelGrid.getChildren().add(caixaInicialCreateOutput);
        
        InputText caixaInicialCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        caixaInicialCreateInput.setId("caixaInicialCreateInput");
        caixaInicialCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.caixaInicial}", Double.class));
        caixaInicialCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(caixaInicialCreateInput);
        
        Message caixaInicialCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        caixaInicialCreateInputMessage.setId("caixaInicialCreateInputMessage");
        caixaInicialCreateInputMessage.setFor("caixaInicialCreateInput");
        caixaInicialCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(caixaInicialCreateInputMessage);
        
        OutputLabel trocadoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        trocadoCreateOutput.setFor("trocadoCreateInput");
        trocadoCreateOutput.setId("trocadoCreateOutput");
        trocadoCreateOutput.setValue("Trocado:");
        htmlPanelGrid.getChildren().add(trocadoCreateOutput);
        
        AutoComplete trocadoCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        trocadoCreateInput.setId("trocadoCreateInput");
        trocadoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.trocado}", Sangria.class));
        trocadoCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{diariaBean.completeTrocado}", List.class, new Class[] { String.class }));
        trocadoCreateInput.setDropdown(true);
        trocadoCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "trocado", String.class));
        trocadoCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{trocado.periodo} #{trocado.valor} #{trocado.origem}", String.class));
        trocadoCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{trocado}", Sangria.class));
        trocadoCreateInput.setConverter(new SangriaConverter());
        trocadoCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(trocadoCreateInput);
        
        Message trocadoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        trocadoCreateInputMessage.setId("trocadoCreateInputMessage");
        trocadoCreateInputMessage.setFor("trocadoCreateInput");
        trocadoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(trocadoCreateInputMessage);
        
        OutputLabel sangriaCaixaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        sangriaCaixaCreateOutput.setFor("sangriaCaixaCreateInput");
        sangriaCaixaCreateOutput.setId("sangriaCaixaCreateOutput");
        sangriaCaixaCreateOutput.setValue("Sangria Caixa:");
        htmlPanelGrid.getChildren().add(sangriaCaixaCreateOutput);
        
        InputText sangriaCaixaCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        sangriaCaixaCreateInput.setId("sangriaCaixaCreateInput");
        sangriaCaixaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.sangriaCaixa}", Double.class));
        sangriaCaixaCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(sangriaCaixaCreateInput);
        
        Message sangriaCaixaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        sangriaCaixaCreateInputMessage.setId("sangriaCaixaCreateInputMessage");
        sangriaCaixaCreateInputMessage.setFor("sangriaCaixaCreateInput");
        sangriaCaixaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(sangriaCaixaCreateInputMessage);
        
        OutputLabel cartaoDebitoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cartaoDebitoCreateOutput.setFor("cartaoDebitoCreateInput");
        cartaoDebitoCreateOutput.setId("cartaoDebitoCreateOutput");
        cartaoDebitoCreateOutput.setValue("Cartao Debito:");
        htmlPanelGrid.getChildren().add(cartaoDebitoCreateOutput);
        
        InputText cartaoDebitoCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cartaoDebitoCreateInput.setId("cartaoDebitoCreateInput");
        cartaoDebitoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.cartaoDebito}", Double.class));
        cartaoDebitoCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(cartaoDebitoCreateInput);
        
        Message cartaoDebitoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cartaoDebitoCreateInputMessage.setId("cartaoDebitoCreateInputMessage");
        cartaoDebitoCreateInputMessage.setFor("cartaoDebitoCreateInput");
        cartaoDebitoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cartaoDebitoCreateInputMessage);
        
        OutputLabel cartaoCreditoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cartaoCreditoCreateOutput.setFor("cartaoCreditoCreateInput");
        cartaoCreditoCreateOutput.setId("cartaoCreditoCreateOutput");
        cartaoCreditoCreateOutput.setValue("Cartao Credito:");
        htmlPanelGrid.getChildren().add(cartaoCreditoCreateOutput);
        
        InputText cartaoCreditoCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cartaoCreditoCreateInput.setId("cartaoCreditoCreateInput");
        cartaoCreditoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.cartaoCredito}", Double.class));
        cartaoCreditoCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(cartaoCreditoCreateInput);
        
        Message cartaoCreditoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cartaoCreditoCreateInputMessage.setId("cartaoCreditoCreateInputMessage");
        cartaoCreditoCreateInputMessage.setFor("cartaoCreditoCreateInput");
        cartaoCreditoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cartaoCreditoCreateInputMessage);
        
        OutputLabel cartaoTicketCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cartaoTicketCreateOutput.setFor("cartaoTicketCreateInput");
        cartaoTicketCreateOutput.setId("cartaoTicketCreateOutput");
        cartaoTicketCreateOutput.setValue("Cartao Ticket:");
        htmlPanelGrid.getChildren().add(cartaoTicketCreateOutput);
        
        InputText cartaoTicketCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cartaoTicketCreateInput.setId("cartaoTicketCreateInput");
        cartaoTicketCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.cartaoTicket}", Double.class));
        cartaoTicketCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(cartaoTicketCreateInput);
        
        Message cartaoTicketCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cartaoTicketCreateInputMessage.setId("cartaoTicketCreateInputMessage");
        cartaoTicketCreateInputMessage.setFor("cartaoTicketCreateInput");
        cartaoTicketCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cartaoTicketCreateInputMessage);
        
        OutputLabel chequeCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        chequeCreateOutput.setFor("chequeCreateInput");
        chequeCreateOutput.setId("chequeCreateOutput");
        chequeCreateOutput.setValue("Cheque:");
        htmlPanelGrid.getChildren().add(chequeCreateOutput);
        
        InputText chequeCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        chequeCreateInput.setId("chequeCreateInput");
        chequeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.cheque}", Double.class));
        chequeCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(chequeCreateInput);
        
        Message chequeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        chequeCreateInputMessage.setId("chequeCreateInputMessage");
        chequeCreateInputMessage.setFor("chequeCreateInput");
        chequeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(chequeCreateInputMessage);
        
        OutputLabel contaReceberCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        contaReceberCreateOutput.setFor("contaReceberCreateInput");
        contaReceberCreateOutput.setId("contaReceberCreateOutput");
        contaReceberCreateOutput.setValue("Conta Receber:");
        htmlPanelGrid.getChildren().add(contaReceberCreateOutput);
        
        AutoComplete contaReceberCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        contaReceberCreateInput.setId("contaReceberCreateInput");
        contaReceberCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.contaReceber}", Receber.class));
        contaReceberCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{diariaBean.completeContaReceber}", List.class, new Class[] { String.class }));
        contaReceberCreateInput.setDropdown(true);
        contaReceberCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "contaReceber", String.class));
        contaReceberCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{contaReceber.periodo} #{contaReceber.numeroPedido} #{contaReceber.telefone} #{contaReceber.valor}", String.class));
        contaReceberCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{contaReceber}", Receber.class));
        contaReceberCreateInput.setConverter(new ReceberConverter());
        contaReceberCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(contaReceberCreateInput);
        
        Message contaReceberCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        contaReceberCreateInputMessage.setId("contaReceberCreateInputMessage");
        contaReceberCreateInputMessage.setFor("contaReceberCreateInput");
        contaReceberCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(contaReceberCreateInputMessage);
        
        OutputLabel contaPagarCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        contaPagarCreateOutput.setFor("contaPagarCreateInput");
        contaPagarCreateOutput.setId("contaPagarCreateOutput");
        contaPagarCreateOutput.setValue("Conta Pagar:");
        htmlPanelGrid.getChildren().add(contaPagarCreateOutput);
        
        AutoComplete contaPagarCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        contaPagarCreateInput.setId("contaPagarCreateInput");
        contaPagarCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.contaPagar}", Cobrar.class));
        contaPagarCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{diariaBean.completeContaPagar}", List.class, new Class[] { String.class }));
        contaPagarCreateInput.setDropdown(true);
        contaPagarCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "contaPagar", String.class));
        contaPagarCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{contaPagar.periodo} #{contaPagar.numeroPedido} #{contaPagar.telefone} #{contaPagar.valor}", String.class));
        contaPagarCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{contaPagar}", Cobrar.class));
        contaPagarCreateInput.setConverter(new CobrarConverter());
        contaPagarCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(contaPagarCreateInput);
        
        Message contaPagarCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        contaPagarCreateInputMessage.setId("contaPagarCreateInputMessage");
        contaPagarCreateInputMessage.setFor("contaPagarCreateInput");
        contaPagarCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(contaPagarCreateInputMessage);
        
        OutputLabel balcaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        balcaoCreateOutput.setFor("balcaoCreateInput");
        balcaoCreateOutput.setId("balcaoCreateOutput");
        balcaoCreateOutput.setValue("Balcao:");
        htmlPanelGrid.getChildren().add(balcaoCreateOutput);
        
        InputText balcaoCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        balcaoCreateInput.setId("balcaoCreateInput");
        balcaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.balcao}", Double.class));
        balcaoCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(balcaoCreateInput);
        
        Message balcaoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        balcaoCreateInputMessage.setId("balcaoCreateInputMessage");
        balcaoCreateInputMessage.setFor("balcaoCreateInput");
        balcaoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(balcaoCreateInputMessage);
        
        OutputLabel caixaFinalCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        caixaFinalCreateOutput.setFor("caixaFinalCreateInput");
        caixaFinalCreateOutput.setId("caixaFinalCreateOutput");
        caixaFinalCreateOutput.setValue("Caixa Final:");
        htmlPanelGrid.getChildren().add(caixaFinalCreateOutput);
        
        InputText caixaFinalCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        caixaFinalCreateInput.setId("caixaFinalCreateInput");
        caixaFinalCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.caixaFinal}", Double.class));
        caixaFinalCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(caixaFinalCreateInput);
        
        Message caixaFinalCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        caixaFinalCreateInputMessage.setId("caixaFinalCreateInputMessage");
        caixaFinalCreateInputMessage.setFor("caixaFinalCreateInput");
        caixaFinalCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(caixaFinalCreateInputMessage);
        
        OutputLabel totalFechamentoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        totalFechamentoCreateOutput.setFor("totalFechamentoCreateInput");
        totalFechamentoCreateOutput.setId("totalFechamentoCreateOutput");
        totalFechamentoCreateOutput.setValue("Total Fechamento:");
        htmlPanelGrid.getChildren().add(totalFechamentoCreateOutput);
        
        InputText totalFechamentoCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        totalFechamentoCreateInput.setId("totalFechamentoCreateInput");
        totalFechamentoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.totalFechamento}", Double.class));
        totalFechamentoCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(totalFechamentoCreateInput);
        
        Message totalFechamentoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        totalFechamentoCreateInputMessage.setId("totalFechamentoCreateInputMessage");
        totalFechamentoCreateInputMessage.setFor("totalFechamentoCreateInput");
        totalFechamentoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(totalFechamentoCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        OutputLabel periodoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        periodoEditOutput.setFor("periodoEditInput");
        periodoEditOutput.setId("periodoEditOutput");
        periodoEditOutput.setValue("Periodo:");
        htmlPanelGrid.getChildren().add(periodoEditOutput);
        
        Calendar periodoEditInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        periodoEditInput.setId("periodoEditInput");
        periodoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.periodo}", Date.class));
        periodoEditInput.setNavigator(true);
        periodoEditInput.setEffect("slideDown");
        periodoEditInput.setPattern("dd/MM/yyyy");
        periodoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(periodoEditInput);
        
        Message periodoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        periodoEditInputMessage.setId("periodoEditInputMessage");
        periodoEditInputMessage.setFor("periodoEditInput");
        periodoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(periodoEditInputMessage);
        
        OutputLabel caixaResponsavelEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        caixaResponsavelEditOutput.setFor("caixaResponsavelEditInput");
        caixaResponsavelEditOutput.setId("caixaResponsavelEditOutput");
        caixaResponsavelEditOutput.setValue("Caixa Responsavel:");
        htmlPanelGrid.getChildren().add(caixaResponsavelEditOutput);
        
        AutoComplete caixaResponsavelEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        caixaResponsavelEditInput.setId("caixaResponsavelEditInput");
        caixaResponsavelEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.caixaResponsavel}", Funcionarios.class));
        caixaResponsavelEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{diariaBean.completeCaixaResponsavel}", List.class, new Class[] { String.class }));
        caixaResponsavelEditInput.setDropdown(true);
        caixaResponsavelEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "caixaResponsavel", String.class));
        caixaResponsavelEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{caixaResponsavel.nome} #{caixaResponsavel.apelido} #{caixaResponsavel.salario} #{caixaResponsavel.cpf}", String.class));
        caixaResponsavelEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{caixaResponsavel}", Funcionarios.class));
        caixaResponsavelEditInput.setConverter(new FuncionariosConverter());
        caixaResponsavelEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(caixaResponsavelEditInput);
        
        Message caixaResponsavelEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        caixaResponsavelEditInputMessage.setId("caixaResponsavelEditInputMessage");
        caixaResponsavelEditInputMessage.setFor("caixaResponsavelEditInput");
        caixaResponsavelEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(caixaResponsavelEditInputMessage);
        
        OutputLabel valorBalcaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorBalcaoEditOutput.setFor("valorBalcaoEditInput");
        valorBalcaoEditOutput.setId("valorBalcaoEditOutput");
        valorBalcaoEditOutput.setValue("Valor Balcao:");
        htmlPanelGrid.getChildren().add(valorBalcaoEditOutput);
        
        InputText valorBalcaoEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorBalcaoEditInput.setId("valorBalcaoEditInput");
        valorBalcaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorBalcao}", Double.class));
        valorBalcaoEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorBalcaoEditInput);
        
        Message valorBalcaoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorBalcaoEditInputMessage.setId("valorBalcaoEditInputMessage");
        valorBalcaoEditInputMessage.setFor("valorBalcaoEditInput");
        valorBalcaoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorBalcaoEditInputMessage);
        
        OutputLabel qtdeValorBalcaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        qtdeValorBalcaoEditOutput.setFor("qtdeValorBalcaoEditInput");
        qtdeValorBalcaoEditOutput.setId("qtdeValorBalcaoEditOutput");
        qtdeValorBalcaoEditOutput.setValue("Qtde Valor Balcao:");
        htmlPanelGrid.getChildren().add(qtdeValorBalcaoEditOutput);
        
        Spinner qtdeValorBalcaoEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        qtdeValorBalcaoEditInput.setId("qtdeValorBalcaoEditInput");
        qtdeValorBalcaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorBalcao}", Integer.class));
        qtdeValorBalcaoEditInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(qtdeValorBalcaoEditInput);
        
        Message qtdeValorBalcaoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        qtdeValorBalcaoEditInputMessage.setId("qtdeValorBalcaoEditInputMessage");
        qtdeValorBalcaoEditInputMessage.setFor("qtdeValorBalcaoEditInput");
        qtdeValorBalcaoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(qtdeValorBalcaoEditInputMessage);
        
        OutputLabel valorWebEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorWebEditOutput.setFor("valorWebEditInput");
        valorWebEditOutput.setId("valorWebEditOutput");
        valorWebEditOutput.setValue("Valor Web:");
        htmlPanelGrid.getChildren().add(valorWebEditOutput);
        
        InputText valorWebEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorWebEditInput.setId("valorWebEditInput");
        valorWebEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorWeb}", Double.class));
        valorWebEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorWebEditInput);
        
        Message valorWebEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorWebEditInputMessage.setId("valorWebEditInputMessage");
        valorWebEditInputMessage.setFor("valorWebEditInput");
        valorWebEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorWebEditInputMessage);
        
        OutputLabel qtdeValorWebEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        qtdeValorWebEditOutput.setFor("qtdeValorWebEditInput");
        qtdeValorWebEditOutput.setId("qtdeValorWebEditOutput");
        qtdeValorWebEditOutput.setValue("Qtde Valor Web:");
        htmlPanelGrid.getChildren().add(qtdeValorWebEditOutput);
        
        Spinner qtdeValorWebEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        qtdeValorWebEditInput.setId("qtdeValorWebEditInput");
        qtdeValorWebEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorWeb}", Integer.class));
        qtdeValorWebEditInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(qtdeValorWebEditInput);
        
        Message qtdeValorWebEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        qtdeValorWebEditInputMessage.setId("qtdeValorWebEditInputMessage");
        qtdeValorWebEditInputMessage.setFor("qtdeValorWebEditInput");
        qtdeValorWebEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(qtdeValorWebEditInputMessage);
        
        OutputLabel valorCentralEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorCentralEditOutput.setFor("valorCentralEditInput");
        valorCentralEditOutput.setId("valorCentralEditOutput");
        valorCentralEditOutput.setValue("Valor Central:");
        htmlPanelGrid.getChildren().add(valorCentralEditOutput);
        
        InputText valorCentralEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorCentralEditInput.setId("valorCentralEditInput");
        valorCentralEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorCentral}", Double.class));
        valorCentralEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorCentralEditInput);
        
        Message valorCentralEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorCentralEditInputMessage.setId("valorCentralEditInputMessage");
        valorCentralEditInputMessage.setFor("valorCentralEditInput");
        valorCentralEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorCentralEditInputMessage);
        
        OutputLabel qtdeValorCentralEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        qtdeValorCentralEditOutput.setFor("qtdeValorCentralEditInput");
        qtdeValorCentralEditOutput.setId("qtdeValorCentralEditOutput");
        qtdeValorCentralEditOutput.setValue("Qtde Valor Central:");
        htmlPanelGrid.getChildren().add(qtdeValorCentralEditOutput);
        
        Spinner qtdeValorCentralEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        qtdeValorCentralEditInput.setId("qtdeValorCentralEditInput");
        qtdeValorCentralEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorCentral}", Integer.class));
        qtdeValorCentralEditInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(qtdeValorCentralEditInput);
        
        Message qtdeValorCentralEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        qtdeValorCentralEditInputMessage.setId("qtdeValorCentralEditInputMessage");
        qtdeValorCentralEditInputMessage.setFor("qtdeValorCentralEditInput");
        qtdeValorCentralEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(qtdeValorCentralEditInputMessage);
        
        OutputLabel valorMesaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorMesaEditOutput.setFor("valorMesaEditInput");
        valorMesaEditOutput.setId("valorMesaEditOutput");
        valorMesaEditOutput.setValue("Valor Mesa:");
        htmlPanelGrid.getChildren().add(valorMesaEditOutput);
        
        InputText valorMesaEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorMesaEditInput.setId("valorMesaEditInput");
        valorMesaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorMesa}", Double.class));
        valorMesaEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorMesaEditInput);
        
        Message valorMesaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorMesaEditInputMessage.setId("valorMesaEditInputMessage");
        valorMesaEditInputMessage.setFor("valorMesaEditInput");
        valorMesaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorMesaEditInputMessage);
        
        OutputLabel qtdeValorMesaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        qtdeValorMesaEditOutput.setFor("qtdeValorMesaEditInput");
        qtdeValorMesaEditOutput.setId("qtdeValorMesaEditOutput");
        qtdeValorMesaEditOutput.setValue("Qtde Valor Mesa:");
        htmlPanelGrid.getChildren().add(qtdeValorMesaEditOutput);
        
        Spinner qtdeValorMesaEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        qtdeValorMesaEditInput.setId("qtdeValorMesaEditInput");
        qtdeValorMesaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorMesa}", Integer.class));
        qtdeValorMesaEditInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(qtdeValorMesaEditInput);
        
        Message qtdeValorMesaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        qtdeValorMesaEditInputMessage.setId("qtdeValorMesaEditInputMessage");
        qtdeValorMesaEditInputMessage.setFor("qtdeValorMesaEditInput");
        qtdeValorMesaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(qtdeValorMesaEditInputMessage);
        
        OutputLabel valorTaxaEntregaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorTaxaEntregaEditOutput.setFor("valorTaxaEntregaEditInput");
        valorTaxaEntregaEditOutput.setId("valorTaxaEntregaEditOutput");
        valorTaxaEntregaEditOutput.setValue("Valor Taxa Entrega:");
        htmlPanelGrid.getChildren().add(valorTaxaEntregaEditOutput);
        
        InputText valorTaxaEntregaEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorTaxaEntregaEditInput.setId("valorTaxaEntregaEditInput");
        valorTaxaEntregaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorTaxaEntrega}", Double.class));
        valorTaxaEntregaEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorTaxaEntregaEditInput);
        
        Message valorTaxaEntregaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorTaxaEntregaEditInputMessage.setId("valorTaxaEntregaEditInputMessage");
        valorTaxaEntregaEditInputMessage.setFor("valorTaxaEntregaEditInput");
        valorTaxaEntregaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorTaxaEntregaEditInputMessage);
        
        OutputLabel qtdeValorTaxaEntregaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        qtdeValorTaxaEntregaEditOutput.setFor("qtdeValorTaxaEntregaEditInput");
        qtdeValorTaxaEntregaEditOutput.setId("qtdeValorTaxaEntregaEditOutput");
        qtdeValorTaxaEntregaEditOutput.setValue("Qtde Valor Taxa Entrega:");
        htmlPanelGrid.getChildren().add(qtdeValorTaxaEntregaEditOutput);
        
        Spinner qtdeValorTaxaEntregaEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        qtdeValorTaxaEntregaEditInput.setId("qtdeValorTaxaEntregaEditInput");
        qtdeValorTaxaEntregaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorTaxaEntrega}", Integer.class));
        qtdeValorTaxaEntregaEditInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(qtdeValorTaxaEntregaEditInput);
        
        Message qtdeValorTaxaEntregaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        qtdeValorTaxaEntregaEditInputMessage.setId("qtdeValorTaxaEntregaEditInputMessage");
        qtdeValorTaxaEntregaEditInputMessage.setFor("qtdeValorTaxaEntregaEditInput");
        qtdeValorTaxaEntregaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(qtdeValorTaxaEntregaEditInputMessage);
        
        OutputLabel valorSevicoMesaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorSevicoMesaEditOutput.setFor("valorSevicoMesaEditInput");
        valorSevicoMesaEditOutput.setId("valorSevicoMesaEditOutput");
        valorSevicoMesaEditOutput.setValue("Valor Sevico Mesa:");
        htmlPanelGrid.getChildren().add(valorSevicoMesaEditOutput);
        
        InputText valorSevicoMesaEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorSevicoMesaEditInput.setId("valorSevicoMesaEditInput");
        valorSevicoMesaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorSevicoMesa}", Double.class));
        valorSevicoMesaEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorSevicoMesaEditInput);
        
        Message valorSevicoMesaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorSevicoMesaEditInputMessage.setId("valorSevicoMesaEditInputMessage");
        valorSevicoMesaEditInputMessage.setFor("valorSevicoMesaEditInput");
        valorSevicoMesaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorSevicoMesaEditInputMessage);
        
        OutputLabel qtdeValorSevicoMesaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        qtdeValorSevicoMesaEditOutput.setFor("qtdeValorSevicoMesaEditInput");
        qtdeValorSevicoMesaEditOutput.setId("qtdeValorSevicoMesaEditOutput");
        qtdeValorSevicoMesaEditOutput.setValue("Qtde Valor Sevico Mesa:");
        htmlPanelGrid.getChildren().add(qtdeValorSevicoMesaEditOutput);
        
        Spinner qtdeValorSevicoMesaEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        qtdeValorSevicoMesaEditInput.setId("qtdeValorSevicoMesaEditInput");
        qtdeValorSevicoMesaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorSevicoMesa}", Integer.class));
        qtdeValorSevicoMesaEditInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(qtdeValorSevicoMesaEditInput);
        
        Message qtdeValorSevicoMesaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        qtdeValorSevicoMesaEditInputMessage.setId("qtdeValorSevicoMesaEditInputMessage");
        qtdeValorSevicoMesaEditInputMessage.setFor("qtdeValorSevicoMesaEditInput");
        qtdeValorSevicoMesaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(qtdeValorSevicoMesaEditInputMessage);
        
        OutputLabel valorTotalFitaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorTotalFitaEditOutput.setFor("valorTotalFitaEditInput");
        valorTotalFitaEditOutput.setId("valorTotalFitaEditOutput");
        valorTotalFitaEditOutput.setValue("Valor Total Fita:");
        htmlPanelGrid.getChildren().add(valorTotalFitaEditOutput);
        
        InputText valorTotalFitaEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorTotalFitaEditInput.setId("valorTotalFitaEditInput");
        valorTotalFitaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorTotalFita}", Double.class));
        valorTotalFitaEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(valorTotalFitaEditInput);
        
        Message valorTotalFitaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorTotalFitaEditInputMessage.setId("valorTotalFitaEditInputMessage");
        valorTotalFitaEditInputMessage.setFor("valorTotalFitaEditInput");
        valorTotalFitaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorTotalFitaEditInputMessage);
        
        OutputLabel caixaInicialEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        caixaInicialEditOutput.setFor("caixaInicialEditInput");
        caixaInicialEditOutput.setId("caixaInicialEditOutput");
        caixaInicialEditOutput.setValue("Caixa Inicial:");
        htmlPanelGrid.getChildren().add(caixaInicialEditOutput);
        
        InputText caixaInicialEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        caixaInicialEditInput.setId("caixaInicialEditInput");
        caixaInicialEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.caixaInicial}", Double.class));
        caixaInicialEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(caixaInicialEditInput);
        
        Message caixaInicialEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        caixaInicialEditInputMessage.setId("caixaInicialEditInputMessage");
        caixaInicialEditInputMessage.setFor("caixaInicialEditInput");
        caixaInicialEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(caixaInicialEditInputMessage);
        
        OutputLabel trocadoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        trocadoEditOutput.setFor("trocadoEditInput");
        trocadoEditOutput.setId("trocadoEditOutput");
        trocadoEditOutput.setValue("Trocado:");
        htmlPanelGrid.getChildren().add(trocadoEditOutput);
        
        AutoComplete trocadoEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        trocadoEditInput.setId("trocadoEditInput");
        trocadoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.trocado}", Sangria.class));
        trocadoEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{diariaBean.completeTrocado}", List.class, new Class[] { String.class }));
        trocadoEditInput.setDropdown(true);
        trocadoEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "trocado", String.class));
        trocadoEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{trocado.periodo} #{trocado.valor} #{trocado.origem}", String.class));
        trocadoEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{trocado}", Sangria.class));
        trocadoEditInput.setConverter(new SangriaConverter());
        trocadoEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(trocadoEditInput);
        
        Message trocadoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        trocadoEditInputMessage.setId("trocadoEditInputMessage");
        trocadoEditInputMessage.setFor("trocadoEditInput");
        trocadoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(trocadoEditInputMessage);
        
        OutputLabel sangriaCaixaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        sangriaCaixaEditOutput.setFor("sangriaCaixaEditInput");
        sangriaCaixaEditOutput.setId("sangriaCaixaEditOutput");
        sangriaCaixaEditOutput.setValue("Sangria Caixa:");
        htmlPanelGrid.getChildren().add(sangriaCaixaEditOutput);
        
        InputText sangriaCaixaEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        sangriaCaixaEditInput.setId("sangriaCaixaEditInput");
        sangriaCaixaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.sangriaCaixa}", Double.class));
        sangriaCaixaEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(sangriaCaixaEditInput);
        
        Message sangriaCaixaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        sangriaCaixaEditInputMessage.setId("sangriaCaixaEditInputMessage");
        sangriaCaixaEditInputMessage.setFor("sangriaCaixaEditInput");
        sangriaCaixaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(sangriaCaixaEditInputMessage);
        
        OutputLabel cartaoDebitoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cartaoDebitoEditOutput.setFor("cartaoDebitoEditInput");
        cartaoDebitoEditOutput.setId("cartaoDebitoEditOutput");
        cartaoDebitoEditOutput.setValue("Cartao Debito:");
        htmlPanelGrid.getChildren().add(cartaoDebitoEditOutput);
        
        InputText cartaoDebitoEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cartaoDebitoEditInput.setId("cartaoDebitoEditInput");
        cartaoDebitoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.cartaoDebito}", Double.class));
        cartaoDebitoEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(cartaoDebitoEditInput);
        
        Message cartaoDebitoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cartaoDebitoEditInputMessage.setId("cartaoDebitoEditInputMessage");
        cartaoDebitoEditInputMessage.setFor("cartaoDebitoEditInput");
        cartaoDebitoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cartaoDebitoEditInputMessage);
        
        OutputLabel cartaoCreditoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cartaoCreditoEditOutput.setFor("cartaoCreditoEditInput");
        cartaoCreditoEditOutput.setId("cartaoCreditoEditOutput");
        cartaoCreditoEditOutput.setValue("Cartao Credito:");
        htmlPanelGrid.getChildren().add(cartaoCreditoEditOutput);
        
        InputText cartaoCreditoEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cartaoCreditoEditInput.setId("cartaoCreditoEditInput");
        cartaoCreditoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.cartaoCredito}", Double.class));
        cartaoCreditoEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(cartaoCreditoEditInput);
        
        Message cartaoCreditoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cartaoCreditoEditInputMessage.setId("cartaoCreditoEditInputMessage");
        cartaoCreditoEditInputMessage.setFor("cartaoCreditoEditInput");
        cartaoCreditoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cartaoCreditoEditInputMessage);
        
        OutputLabel cartaoTicketEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        cartaoTicketEditOutput.setFor("cartaoTicketEditInput");
        cartaoTicketEditOutput.setId("cartaoTicketEditOutput");
        cartaoTicketEditOutput.setValue("Cartao Ticket:");
        htmlPanelGrid.getChildren().add(cartaoTicketEditOutput);
        
        InputText cartaoTicketEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        cartaoTicketEditInput.setId("cartaoTicketEditInput");
        cartaoTicketEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.cartaoTicket}", Double.class));
        cartaoTicketEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(cartaoTicketEditInput);
        
        Message cartaoTicketEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        cartaoTicketEditInputMessage.setId("cartaoTicketEditInputMessage");
        cartaoTicketEditInputMessage.setFor("cartaoTicketEditInput");
        cartaoTicketEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(cartaoTicketEditInputMessage);
        
        OutputLabel chequeEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        chequeEditOutput.setFor("chequeEditInput");
        chequeEditOutput.setId("chequeEditOutput");
        chequeEditOutput.setValue("Cheque:");
        htmlPanelGrid.getChildren().add(chequeEditOutput);
        
        InputText chequeEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        chequeEditInput.setId("chequeEditInput");
        chequeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.cheque}", Double.class));
        chequeEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(chequeEditInput);
        
        Message chequeEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        chequeEditInputMessage.setId("chequeEditInputMessage");
        chequeEditInputMessage.setFor("chequeEditInput");
        chequeEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(chequeEditInputMessage);
        
        OutputLabel contaReceberEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        contaReceberEditOutput.setFor("contaReceberEditInput");
        contaReceberEditOutput.setId("contaReceberEditOutput");
        contaReceberEditOutput.setValue("Conta a Receber:");
        htmlPanelGrid.getChildren().add(contaReceberEditOutput);
        
        AutoComplete contaReceberEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        contaReceberEditInput.setId("contaReceberEditInput");
//        contaReceberEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.contaReceber}", Receber.class));
//        contaReceberEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{diariaBean.completeContaReceber}", List.class, new Class[] { String.class }));
//        contaReceberEditInput.setDropdown(true);
//        contaReceberEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "contaReceber", String.class));
//        contaReceberEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{contaReceber.periodo} #{contaReceber.numeroPedido} #{contaReceber.telefone} #{contaReceber.valor}", String.class));
//        contaReceberEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{contaReceber}", Receber.class));
//        contaReceberEditInput.setConverter(new ReceberConverter());
//        contaReceberEditInput.setRequired(false);
        contaReceberEditInput.setDisabled(true);
        htmlPanelGrid.getChildren().add(contaReceberEditInput);
        
        Message contaReceberEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        contaReceberEditInputMessage.setId("contaReceberEditInputMessage");
        contaReceberEditInputMessage.setFor("contaReceberEditInput");
        contaReceberEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(contaReceberEditInputMessage);
        
        OutputLabel contaPagarEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        contaPagarEditOutput.setFor("contaPagarEditInput");
        contaPagarEditOutput.setId("contaPagarEditOutput");
        contaPagarEditOutput.setValue("Conta a Pagar:");
        htmlPanelGrid.getChildren().add(contaPagarEditOutput);
        
        AutoComplete contaPagarEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        contaPagarEditInput.setId("contaPagarEditInput");
        contaPagarEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.contaPagar}", Cobrar.class));
        contaPagarEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{diariaBean.completeContaPagar}", List.class, new Class[] { String.class }));
        contaPagarEditInput.setDropdown(true);
        contaPagarEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "contaPagar", String.class));
        contaPagarEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{contaPagar.periodo} #{contaPagar.numeroPedido} #{contaPagar.telefone} #{contaPagar.valor}", String.class));
        contaPagarEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{contaPagar}", Cobrar.class));
        contaPagarEditInput.setConverter(new CobrarConverter());
        contaPagarEditInput.setRequired(false);
        contaPagarEditInput.setDisabled(true);
        htmlPanelGrid.getChildren().add(contaPagarEditInput);
        
        Message contaPagarEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        contaPagarEditInputMessage.setId("contaPagarEditInputMessage");
        contaPagarEditInputMessage.setFor("contaPagarEditInput");
        contaPagarEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(contaPagarEditInputMessage);
        
        OutputLabel balcaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        balcaoEditOutput.setFor("balcaoEditInput");
        balcaoEditOutput.setId("balcaoEditOutput");
        balcaoEditOutput.setValue("Balcao:");
        htmlPanelGrid.getChildren().add(balcaoEditOutput);
        
        InputText balcaoEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        balcaoEditInput.setId("balcaoEditInput");
        balcaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.balcao}", Double.class));
        balcaoEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(balcaoEditInput);
        
        Message balcaoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        balcaoEditInputMessage.setId("balcaoEditInputMessage");
        balcaoEditInputMessage.setFor("balcaoEditInput");
        balcaoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(balcaoEditInputMessage);
        
        OutputLabel caixaFinalEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        caixaFinalEditOutput.setFor("caixaFinalEditInput");
        caixaFinalEditOutput.setId("caixaFinalEditOutput");
        caixaFinalEditOutput.setValue("Caixa Final:");
        htmlPanelGrid.getChildren().add(caixaFinalEditOutput);
        
        InputText caixaFinalEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        caixaFinalEditInput.setId("caixaFinalEditInput");
        caixaFinalEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.caixaFinal}", Double.class));
        caixaFinalEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(caixaFinalEditInput);
        
        Message caixaFinalEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        caixaFinalEditInputMessage.setId("caixaFinalEditInputMessage");
        caixaFinalEditInputMessage.setFor("caixaFinalEditInput");
        caixaFinalEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(caixaFinalEditInputMessage);
        
        OutputLabel totalFechamentoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        totalFechamentoEditOutput.setFor("totalFechamentoEditInput");
        totalFechamentoEditOutput.setId("totalFechamentoEditOutput");
        totalFechamentoEditOutput.setValue("Total Fechamento:");
        htmlPanelGrid.getChildren().add(totalFechamentoEditOutput);
        
        InputText totalFechamentoEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        totalFechamentoEditInput.setId("totalFechamentoEditInput");
        totalFechamentoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.totalFechamento}", Double.class));
        totalFechamentoEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(totalFechamentoEditInput);
        
        Message totalFechamentoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        totalFechamentoEditInputMessage.setId("totalFechamentoEditInputMessage");
        totalFechamentoEditInputMessage.setFor("totalFechamentoEditInput");
        totalFechamentoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(totalFechamentoEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText periodoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        periodoLabel.setId("periodoLabel");
        periodoLabel.setValue("Periodo:");
        htmlPanelGrid.getChildren().add(periodoLabel);
        
        HtmlOutputText periodoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        periodoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.periodo}", Calendar.class));
        DateTimeConverter periodoValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        periodoValueConverter.setPattern("dd/MM/yyyy");
        periodoValue.setConverter(periodoValueConverter);
        htmlPanelGrid.getChildren().add(periodoValue);
        
        HtmlOutputText caixaResponsavelLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        caixaResponsavelLabel.setId("caixaResponsavelLabel");
        caixaResponsavelLabel.setValue("Caixa Responsavel:");
        htmlPanelGrid.getChildren().add(caixaResponsavelLabel);
        
        HtmlOutputText caixaResponsavelValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        caixaResponsavelValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.caixaResponsavel}", Funcionarios.class));
        caixaResponsavelValue.setConverter(new FuncionariosConverter());
        htmlPanelGrid.getChildren().add(caixaResponsavelValue);
        
        HtmlOutputText valorBalcaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorBalcaoLabel.setId("valorBalcaoLabel");
        valorBalcaoLabel.setValue("Valor Balcao:");
        htmlPanelGrid.getChildren().add(valorBalcaoLabel);
        
        HtmlOutputText valorBalcaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorBalcaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorBalcao}", String.class));
        htmlPanelGrid.getChildren().add(valorBalcaoValue);
        
        HtmlOutputText qtdeValorBalcaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        qtdeValorBalcaoLabel.setId("qtdeValorBalcaoLabel");
        qtdeValorBalcaoLabel.setValue("Qtde Valor Balcao:");
        htmlPanelGrid.getChildren().add(qtdeValorBalcaoLabel);
        
        HtmlOutputText qtdeValorBalcaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        qtdeValorBalcaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorBalcao}", String.class));
        htmlPanelGrid.getChildren().add(qtdeValorBalcaoValue);
        
        HtmlOutputText valorWebLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorWebLabel.setId("valorWebLabel");
        valorWebLabel.setValue("Valor Web:");
        htmlPanelGrid.getChildren().add(valorWebLabel);
        
        HtmlOutputText valorWebValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorWebValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorWeb}", String.class));
        htmlPanelGrid.getChildren().add(valorWebValue);
        
        HtmlOutputText qtdeValorWebLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        qtdeValorWebLabel.setId("qtdeValorWebLabel");
        qtdeValorWebLabel.setValue("Qtde Valor Web:");
        htmlPanelGrid.getChildren().add(qtdeValorWebLabel);
        
        HtmlOutputText qtdeValorWebValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        qtdeValorWebValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorWeb}", String.class));
        htmlPanelGrid.getChildren().add(qtdeValorWebValue);
        
        HtmlOutputText valorCentralLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorCentralLabel.setId("valorCentralLabel");
        valorCentralLabel.setValue("Valor Central:");
        htmlPanelGrid.getChildren().add(valorCentralLabel);
        
        HtmlOutputText valorCentralValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorCentralValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorCentral}", String.class));
        htmlPanelGrid.getChildren().add(valorCentralValue);
        
        HtmlOutputText qtdeValorCentralLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        qtdeValorCentralLabel.setId("qtdeValorCentralLabel");
        qtdeValorCentralLabel.setValue("Qtde Valor Central:");
        htmlPanelGrid.getChildren().add(qtdeValorCentralLabel);
        
        HtmlOutputText qtdeValorCentralValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        qtdeValorCentralValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorCentral}", String.class));
        htmlPanelGrid.getChildren().add(qtdeValorCentralValue);
        
        HtmlOutputText valorMesaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorMesaLabel.setId("valorMesaLabel");
        valorMesaLabel.setValue("Valor Mesa:");
        htmlPanelGrid.getChildren().add(valorMesaLabel);
        
        HtmlOutputText valorMesaValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorMesaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorMesa}", String.class));
        htmlPanelGrid.getChildren().add(valorMesaValue);
        
        HtmlOutputText qtdeValorMesaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        qtdeValorMesaLabel.setId("qtdeValorMesaLabel");
        qtdeValorMesaLabel.setValue("Qtde Valor Mesa:");
        htmlPanelGrid.getChildren().add(qtdeValorMesaLabel);
        
        HtmlOutputText qtdeValorMesaValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        qtdeValorMesaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorMesa}", String.class));
        htmlPanelGrid.getChildren().add(qtdeValorMesaValue);
        
        HtmlOutputText valorTaxaEntregaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorTaxaEntregaLabel.setId("valorTaxaEntregaLabel");
        valorTaxaEntregaLabel.setValue("Valor Taxa Entrega:");
        htmlPanelGrid.getChildren().add(valorTaxaEntregaLabel);
        
        HtmlOutputText valorTaxaEntregaValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorTaxaEntregaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorTaxaEntrega}", String.class));
        htmlPanelGrid.getChildren().add(valorTaxaEntregaValue);
        
        HtmlOutputText qtdeValorTaxaEntregaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        qtdeValorTaxaEntregaLabel.setId("qtdeValorTaxaEntregaLabel");
        qtdeValorTaxaEntregaLabel.setValue("Qtde Valor Taxa Entrega:");
        htmlPanelGrid.getChildren().add(qtdeValorTaxaEntregaLabel);
        
        HtmlOutputText qtdeValorTaxaEntregaValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        qtdeValorTaxaEntregaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorTaxaEntrega}", String.class));
        htmlPanelGrid.getChildren().add(qtdeValorTaxaEntregaValue);
        
        HtmlOutputText valorSevicoMesaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorSevicoMesaLabel.setId("valorSevicoMesaLabel");
        valorSevicoMesaLabel.setValue("Valor Sevico Mesa:");
        htmlPanelGrid.getChildren().add(valorSevicoMesaLabel);
        
        HtmlOutputText valorSevicoMesaValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorSevicoMesaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorSevicoMesa}", String.class));
        htmlPanelGrid.getChildren().add(valorSevicoMesaValue);
        
        HtmlOutputText qtdeValorSevicoMesaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        qtdeValorSevicoMesaLabel.setId("qtdeValorSevicoMesaLabel");
        qtdeValorSevicoMesaLabel.setValue("Qtde Valor Sevico Mesa:");
        htmlPanelGrid.getChildren().add(qtdeValorSevicoMesaLabel);
        
        HtmlOutputText qtdeValorSevicoMesaValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        qtdeValorSevicoMesaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.qtdeValorSevicoMesa}", String.class));
        htmlPanelGrid.getChildren().add(qtdeValorSevicoMesaValue);
        
        HtmlOutputText valorTotalFitaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorTotalFitaLabel.setId("valorTotalFitaLabel");
        valorTotalFitaLabel.setValue("Valor Total Fita:");
        htmlPanelGrid.getChildren().add(valorTotalFitaLabel);
        
        HtmlOutputText valorTotalFitaValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorTotalFitaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.valorTotalFita}", String.class));
        htmlPanelGrid.getChildren().add(valorTotalFitaValue);
        
        HtmlOutputText caixaInicialLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        caixaInicialLabel.setId("caixaInicialLabel");
        caixaInicialLabel.setValue("Caixa Inicial:");
        htmlPanelGrid.getChildren().add(caixaInicialLabel);
        
        HtmlOutputText caixaInicialValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        caixaInicialValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.caixaInicial}", String.class));
        htmlPanelGrid.getChildren().add(caixaInicialValue);
        
        HtmlOutputText trocadoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        trocadoLabel.setId("trocadoLabel");
        trocadoLabel.setValue("Trocado:");
        htmlPanelGrid.getChildren().add(trocadoLabel);
        
        HtmlOutputText trocadoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        trocadoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.trocado}", Sangria.class));
        trocadoValue.setConverter(new SangriaConverter());
        htmlPanelGrid.getChildren().add(trocadoValue);
        
        HtmlOutputText sangriaCaixaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        sangriaCaixaLabel.setId("sangriaCaixaLabel");
        sangriaCaixaLabel.setValue("Sangria Caixa:");
        htmlPanelGrid.getChildren().add(sangriaCaixaLabel);
        
        HtmlOutputText sangriaCaixaValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        sangriaCaixaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.sangriaCaixa}", String.class));
        htmlPanelGrid.getChildren().add(sangriaCaixaValue);
        
        HtmlOutputText cartaoDebitoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cartaoDebitoLabel.setId("cartaoDebitoLabel");
        cartaoDebitoLabel.setValue("Cartao Debito:");
        htmlPanelGrid.getChildren().add(cartaoDebitoLabel);
        
        HtmlOutputText cartaoDebitoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cartaoDebitoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.cartaoDebito}", String.class));
        htmlPanelGrid.getChildren().add(cartaoDebitoValue);
        
        HtmlOutputText cartaoCreditoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cartaoCreditoLabel.setId("cartaoCreditoLabel");
        cartaoCreditoLabel.setValue("Cartao Credito:");
        htmlPanelGrid.getChildren().add(cartaoCreditoLabel);
        
        HtmlOutputText cartaoCreditoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cartaoCreditoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.cartaoCredito}", String.class));
        htmlPanelGrid.getChildren().add(cartaoCreditoValue);
        
        HtmlOutputText cartaoTicketLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cartaoTicketLabel.setId("cartaoTicketLabel");
        cartaoTicketLabel.setValue("Cartao Ticket:");
        htmlPanelGrid.getChildren().add(cartaoTicketLabel);
        
        HtmlOutputText cartaoTicketValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        cartaoTicketValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.cartaoTicket}", String.class));
        htmlPanelGrid.getChildren().add(cartaoTicketValue);
        
        HtmlOutputText chequeLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        chequeLabel.setId("chequeLabel");
        chequeLabel.setValue("Cheque:");
        htmlPanelGrid.getChildren().add(chequeLabel);
        
        HtmlOutputText chequeValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        chequeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.cheque}", String.class));
        htmlPanelGrid.getChildren().add(chequeValue);
        
        HtmlOutputText contaReceberLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        contaReceberLabel.setId("contaReceberLabel");
        contaReceberLabel.setValue("Conta a Receber:");
        htmlPanelGrid.getChildren().add(contaReceberLabel);
        
        HtmlOutputText contaReceberValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        contaReceberValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.contaReceber}", Receber.class));
        contaReceberValue.setConverter(new ReceberConverter());
        htmlPanelGrid.getChildren().add(contaReceberValue);
        
        HtmlOutputText contaPagarLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        contaPagarLabel.setId("contaPagarLabel");
        contaPagarLabel.setValue("Conta Pagar:");
        htmlPanelGrid.getChildren().add(contaPagarLabel);
        
        HtmlOutputText contaPagarValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        contaPagarValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.contaPagar}", Cobrar.class));
        contaPagarValue.setConverter(new CobrarConverter());
        htmlPanelGrid.getChildren().add(contaPagarValue);
        
        HtmlOutputText balcaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        balcaoLabel.setId("balcaoLabel");
        balcaoLabel.setValue("Balcao:");
        htmlPanelGrid.getChildren().add(balcaoLabel);
        
        HtmlOutputText balcaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        balcaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.balcao}", String.class));
        htmlPanelGrid.getChildren().add(balcaoValue);
        
        HtmlOutputText caixaFinalLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        caixaFinalLabel.setId("caixaFinalLabel");
        caixaFinalLabel.setValue("Caixa Final:");
        htmlPanelGrid.getChildren().add(caixaFinalLabel);
        
        HtmlOutputText caixaFinalValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        caixaFinalValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.caixaFinal}", String.class));
        htmlPanelGrid.getChildren().add(caixaFinalValue);
        
        HtmlOutputText totalFechamentoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        totalFechamentoLabel.setId("totalFechamentoLabel");
        totalFechamentoLabel.setValue("Total Fechamento:");
        htmlPanelGrid.getChildren().add(totalFechamentoLabel);
        
        HtmlOutputText totalFechamentoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        totalFechamentoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{diariaBean.diaria.totalFechamento}", String.class));
        htmlPanelGrid.getChildren().add(totalFechamentoValue);
        
        return htmlPanelGrid;
    }

	public Diaria getDiaria() {
        if (diaria == null) {
            diaria = new Diaria();
        }
        return diaria;
    }

	public void setDiaria(Diaria diaria) {
        this.diaria = diaria;
    }

	public List<Funcionarios> completeCaixaResponsavel(String query) {
        List<Funcionarios> suggestions = new ArrayList<Funcionarios>();
        for (Funcionarios funcionarios : Funcionarios.findAllFuncionarioses()) {
            String funcionariosStr = String.valueOf(funcionarios.getNome() +  " "  + funcionarios.getApelido() +  " "  + funcionarios.getSalario() +  " "  + funcionarios.getCpf());
            if (funcionariosStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(funcionarios);
            }
        }
        return suggestions;
    }

	public List<Sangria> completeTrocado(String query) {
        List<Sangria> suggestions = new ArrayList<Sangria>();
        for (Sangria sangria : Sangria.findAllSangrias()) {
            String sangriaStr = String.valueOf(sangria.getPeriodo() +  " "  + sangria.getValor() +  " "  + sangria.getOrigem());
            if (sangriaStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(sangria);
            }
        }
        return suggestions;
    }

	public List<Receber> completeContaReceber(String query) {
        List<Receber> suggestions = new ArrayList<Receber>();
        for (Receber receber : Receber.findAllRecebers()) {
            String receberStr = String.valueOf(receber.getPeriodo() +  " "  + receber.getNumeroPedido() +  " "  + receber.getTelefone() +  " "  + receber.getValor());
            if (receberStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(receber);
            }
        }
        return suggestions;
    }

	public List<Cobrar> completeContaPagar(String query) {
        List<Cobrar> suggestions = new ArrayList<Cobrar>();
        for (Cobrar cobrar : Cobrar.findAllCobrars()) {
            String cobrarStr = String.valueOf(cobrar.getPeriodo() +  " "  + cobrar.getNumeroPedido() +  " "  + cobrar.getTelefone() +  " "  + cobrar.getValor());
            if (cobrarStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(cobrar);
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
        findAllDiarias();
        return "diaria";
    }

	public String displayCreateDialog() {
        diaria = new Diaria();
        createDialogVisible = true;
        return "diaria";
    }

	public String persist() {
        String message = "";
        if (diaria.getId() != null) {
            diaria.merge();
            message = "message_successfully_updated";
        } else {
            diaria.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Diaria");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllDiarias();
    }

	public String delete() {
        diaria.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Diaria");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllDiarias();
    }

	public void reset() {
        diaria = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}
