package br.com.conjmc.jsf;
import br.com.conjmc.cadastrobasico.Motoqueiros;
import br.com.conjmc.controlediario.fechamento.Receber;
import br.com.conjmc.jsf.converter.MotoqueirosConverter;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.managedbean.RooJsfManagedBean;
import org.springframework.roo.addon.serializable.RooSerializable;

@ManagedBean(name = "receberBean")
@SessionScoped
@Configurable
@RooSerializable
@RooJsfManagedBean(entity = Receber.class, beanName = "receberBean")
public class ReceberBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name = "Recebers";

	private Receber receber;

	private List<Receber> allRecebers;

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
        columns.add("numeroPedido");
        columns.add("telefone");
        columns.add("valor");
        columns.add("tipoPagamento");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Receber> getAllRecebers() {
        return allRecebers;
    }

	public void setAllRecebers(List<Receber> allRecebers) {
        this.allRecebers = allRecebers;
    }

	public String findAllRecebers() {
        allRecebers = Receber.findAllRecebers();
        dataVisible = !allRecebers.isEmpty();
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
        periodoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.periodo}", Date.class));
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
        
        OutputLabel motoqueiroCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        motoqueiroCreateOutput.setFor("motoqueiroCreateInput");
        motoqueiroCreateOutput.setId("motoqueiroCreateOutput");
        motoqueiroCreateOutput.setValue("Motoqueiro:");
        htmlPanelGrid.getChildren().add(motoqueiroCreateOutput);
        
        AutoComplete motoqueiroCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        motoqueiroCreateInput.setId("motoqueiroCreateInput");
        motoqueiroCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.motoqueiro}", Motoqueiros.class));
        motoqueiroCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{receberBean.completeMotoqueiro}", List.class, new Class[] { String.class }));
        motoqueiroCreateInput.setDropdown(true);
        motoqueiroCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "motoqueiro", String.class));
        motoqueiroCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{motoqueiro.apelido} #{motoqueiro.nome}", String.class));
        motoqueiroCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{motoqueiro}", Motoqueiros.class));
        motoqueiroCreateInput.setConverter(new MotoqueirosConverter());
        motoqueiroCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(motoqueiroCreateInput);
        
        Message motoqueiroCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        motoqueiroCreateInputMessage.setId("motoqueiroCreateInputMessage");
        motoqueiroCreateInputMessage.setFor("motoqueiroCreateInput");
        motoqueiroCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(motoqueiroCreateInputMessage);
        
        OutputLabel numeroPedidoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        numeroPedidoCreateOutput.setFor("numeroPedidoCreateInput");
        numeroPedidoCreateOutput.setId("numeroPedidoCreateOutput");
        numeroPedidoCreateOutput.setValue("Numero Pedido:");
        htmlPanelGrid.getChildren().add(numeroPedidoCreateOutput);
        
        InputText numeroPedidoCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        numeroPedidoCreateInput.setId("numeroPedidoCreateInput");
        numeroPedidoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.numeroPedido}", String.class));
        numeroPedidoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(numeroPedidoCreateInput);
        
        Message numeroPedidoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        numeroPedidoCreateInputMessage.setId("numeroPedidoCreateInputMessage");
        numeroPedidoCreateInputMessage.setFor("numeroPedidoCreateInput");
        numeroPedidoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(numeroPedidoCreateInputMessage);
        
        OutputLabel telefoneCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        telefoneCreateOutput.setFor("telefoneCreateInput");
        telefoneCreateOutput.setId("telefoneCreateOutput");
        telefoneCreateOutput.setValue("Telefone:");
        htmlPanelGrid.getChildren().add(telefoneCreateOutput);
        
        InputText telefoneCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        telefoneCreateInput.setId("telefoneCreateInput");
        telefoneCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.telefone}", String.class));
        telefoneCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(telefoneCreateInput);
        
        Message telefoneCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        telefoneCreateInputMessage.setId("telefoneCreateInputMessage");
        telefoneCreateInputMessage.setFor("telefoneCreateInput");
        telefoneCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(telefoneCreateInputMessage);
        
        OutputLabel valorCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorCreateOutput.setFor("valorCreateInput");
        valorCreateOutput.setId("valorCreateOutput");
        valorCreateOutput.setValue("Valor:");
        htmlPanelGrid.getChildren().add(valorCreateOutput);
        
        InputText valorCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorCreateInput.setId("valorCreateInput");
        valorCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.valor}", Double.class));
        valorCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(valorCreateInput);
        
        Message valorCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorCreateInputMessage.setId("valorCreateInputMessage");
        valorCreateInputMessage.setFor("valorCreateInput");
        valorCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorCreateInputMessage);
        
        OutputLabel tipoPagamentoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        tipoPagamentoCreateOutput.setFor("tipoPagamentoCreateInput");
        tipoPagamentoCreateOutput.setId("tipoPagamentoCreateOutput");
        tipoPagamentoCreateOutput.setValue("Tipo Pagamento:");
        htmlPanelGrid.getChildren().add(tipoPagamentoCreateOutput);
        
        InputText tipoPagamentoCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        tipoPagamentoCreateInput.setId("tipoPagamentoCreateInput");
        tipoPagamentoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.tipoPagamento}", String.class));
        tipoPagamentoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(tipoPagamentoCreateInput);
        
        Message tipoPagamentoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        tipoPagamentoCreateInputMessage.setId("tipoPagamentoCreateInputMessage");
        tipoPagamentoCreateInputMessage.setFor("tipoPagamentoCreateInput");
        tipoPagamentoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(tipoPagamentoCreateInputMessage);
        
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
        periodoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.periodo}", Date.class));
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
        
        OutputLabel motoqueiroEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        motoqueiroEditOutput.setFor("motoqueiroEditInput");
        motoqueiroEditOutput.setId("motoqueiroEditOutput");
        motoqueiroEditOutput.setValue("Motoqueiro:");
        htmlPanelGrid.getChildren().add(motoqueiroEditOutput);
        
        AutoComplete motoqueiroEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        motoqueiroEditInput.setId("motoqueiroEditInput");
        motoqueiroEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.motoqueiro}", Motoqueiros.class));
        motoqueiroEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{receberBean.completeMotoqueiro}", List.class, new Class[] { String.class }));
        motoqueiroEditInput.setDropdown(true);
        motoqueiroEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "motoqueiro", String.class));
        motoqueiroEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{motoqueiro.apelido} #{motoqueiro.nome}", String.class));
        motoqueiroEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{motoqueiro}", Motoqueiros.class));
        motoqueiroEditInput.setConverter(new MotoqueirosConverter());
        motoqueiroEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(motoqueiroEditInput);
        
        Message motoqueiroEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        motoqueiroEditInputMessage.setId("motoqueiroEditInputMessage");
        motoqueiroEditInputMessage.setFor("motoqueiroEditInput");
        motoqueiroEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(motoqueiroEditInputMessage);
        
        OutputLabel numeroPedidoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        numeroPedidoEditOutput.setFor("numeroPedidoEditInput");
        numeroPedidoEditOutput.setId("numeroPedidoEditOutput");
        numeroPedidoEditOutput.setValue("Numero Pedido:");
        htmlPanelGrid.getChildren().add(numeroPedidoEditOutput);
        
        InputText numeroPedidoEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        numeroPedidoEditInput.setId("numeroPedidoEditInput");
        numeroPedidoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.numeroPedido}", String.class));
        numeroPedidoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(numeroPedidoEditInput);
        
        Message numeroPedidoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        numeroPedidoEditInputMessage.setId("numeroPedidoEditInputMessage");
        numeroPedidoEditInputMessage.setFor("numeroPedidoEditInput");
        numeroPedidoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(numeroPedidoEditInputMessage);
        
        OutputLabel telefoneEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        telefoneEditOutput.setFor("telefoneEditInput");
        telefoneEditOutput.setId("telefoneEditOutput");
        telefoneEditOutput.setValue("Telefone:");
        htmlPanelGrid.getChildren().add(telefoneEditOutput);
        
        InputText telefoneEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        telefoneEditInput.setId("telefoneEditInput");
        telefoneEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.telefone}", String.class));
        telefoneEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(telefoneEditInput);
        
        Message telefoneEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        telefoneEditInputMessage.setId("telefoneEditInputMessage");
        telefoneEditInputMessage.setFor("telefoneEditInput");
        telefoneEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(telefoneEditInputMessage);
        
        OutputLabel valorEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorEditOutput.setFor("valorEditInput");
        valorEditOutput.setId("valorEditOutput");
        valorEditOutput.setValue("Valor:");
        htmlPanelGrid.getChildren().add(valorEditOutput);
        
        InputText valorEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorEditInput.setId("valorEditInput");
        valorEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.valor}", Double.class));
        valorEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(valorEditInput);
        
        Message valorEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorEditInputMessage.setId("valorEditInputMessage");
        valorEditInputMessage.setFor("valorEditInput");
        valorEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorEditInputMessage);
        
        OutputLabel tipoPagamentoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        tipoPagamentoEditOutput.setFor("tipoPagamentoEditInput");
        tipoPagamentoEditOutput.setId("tipoPagamentoEditOutput");
        tipoPagamentoEditOutput.setValue("Tipo Pagamento:");
        htmlPanelGrid.getChildren().add(tipoPagamentoEditOutput);
        
        InputText tipoPagamentoEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        tipoPagamentoEditInput.setId("tipoPagamentoEditInput");
        tipoPagamentoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.tipoPagamento}", String.class));
        tipoPagamentoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(tipoPagamentoEditInput);
        
        Message tipoPagamentoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        tipoPagamentoEditInputMessage.setId("tipoPagamentoEditInputMessage");
        tipoPagamentoEditInputMessage.setFor("tipoPagamentoEditInput");
        tipoPagamentoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(tipoPagamentoEditInputMessage);
        
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
        periodoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.periodo}", Calendar.class));
        DateTimeConverter periodoValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        periodoValueConverter.setPattern("dd/MM/yyyy");
        periodoValue.setConverter(periodoValueConverter);
        htmlPanelGrid.getChildren().add(periodoValue);
        
        HtmlOutputText motoqueiroLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        motoqueiroLabel.setId("motoqueiroLabel");
        motoqueiroLabel.setValue("Motoqueiro:");
        htmlPanelGrid.getChildren().add(motoqueiroLabel);
        
        HtmlOutputText motoqueiroValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        motoqueiroValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.motoqueiro}", Motoqueiros.class));
        motoqueiroValue.setConverter(new MotoqueirosConverter());
        htmlPanelGrid.getChildren().add(motoqueiroValue);
        
        HtmlOutputText numeroPedidoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        numeroPedidoLabel.setId("numeroPedidoLabel");
        numeroPedidoLabel.setValue("Numero Pedido:");
        htmlPanelGrid.getChildren().add(numeroPedidoLabel);
        
        HtmlOutputText numeroPedidoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        numeroPedidoValue.setId("numeroPedidoValue");
        numeroPedidoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.numeroPedido}", String.class));
        htmlPanelGrid.getChildren().add(numeroPedidoValue);
        
        HtmlOutputText telefoneLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        telefoneLabel.setId("telefoneLabel");
        telefoneLabel.setValue("Telefone:");
        htmlPanelGrid.getChildren().add(telefoneLabel);
        
        HtmlOutputText telefoneValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        telefoneValue.setId("telefoneValue");
        telefoneValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.telefone}", String.class));
        htmlPanelGrid.getChildren().add(telefoneValue);
        
        HtmlOutputText valorLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorLabel.setId("valorLabel");
        valorLabel.setValue("Valor:");
        htmlPanelGrid.getChildren().add(valorLabel);
        
        HtmlOutputText valorValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.valor}", String.class));
        htmlPanelGrid.getChildren().add(valorValue);
        
        HtmlOutputText tipoPagamentoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        tipoPagamentoLabel.setId("tipoPagamentoLabel");
        tipoPagamentoLabel.setValue("Tipo Pagamento:");
        htmlPanelGrid.getChildren().add(tipoPagamentoLabel);
        
        HtmlOutputText tipoPagamentoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        tipoPagamentoValue.setId("tipoPagamentoValue");
        tipoPagamentoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{receberBean.receber.tipoPagamento}", String.class));
        htmlPanelGrid.getChildren().add(tipoPagamentoValue);
        
        return htmlPanelGrid;
    }

	public Receber getReceber() {
        if (receber == null) {
            receber = new Receber();
        }
        return receber;
    }

	public void setReceber(Receber receber) {
        this.receber = receber;
    }

	public List<Motoqueiros> completeMotoqueiro(String query) {
        List<Motoqueiros> suggestions = new ArrayList<Motoqueiros>();
        for (Motoqueiros motoqueiros : Motoqueiros.findAllMotoqueiroses()) {
            String motoqueirosStr = String.valueOf(motoqueiros.getApelido() +  " "  + motoqueiros.getNome());
            if (motoqueirosStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(motoqueiros);
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
        findAllRecebers();
        return "receber";
    }

	public String displayCreateDialog() {
        receber = new Receber();
        createDialogVisible = true;
        return "receber";
    }

	public String persist() {
        String message = "";
        if (receber.getId() != null) {
            receber.merge();
            message = "message_successfully_updated";
        } else {
            receber.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Receber");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllRecebers();
    }

	public String delete() {
        receber.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Receber");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllRecebers();
    }

	public void reset() {
        receber = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}
