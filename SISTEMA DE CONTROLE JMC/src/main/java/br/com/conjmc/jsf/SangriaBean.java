package br.com.conjmc.jsf;
import java.io.Serializable;
import java.text.DecimalFormat;
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

import org.apache.commons.collections.bidimap.DualHashBidiMap;
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

import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.despesa.DespesasLoja;
import br.com.conjmc.jsf.converter.DespesasGastosConverter;
import br.com.conjmc.jsf.converter.FuncionariosConverter;
import br.com.conjmc.jsf.util.DataUltil;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.relatorios.Classificacao;
import br.com.conjmc.relatorios.relatoriodiadodes.RelatorioDiaDoMes;

@ManagedBean(name = "sangriaBean")
@SessionScoped
@Configurable
@RooSerializable
@RooJsfManagedBean(entity = Sangria.class, beanName = "sangriaBean")
public class SangriaBean implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String name = "Sangrias";

	private Sangria sangria;

	private List<Sangria> allSangrias;
	
	private List<Sangria> allDespesaLoja;
	
	private List<DespesasGastos> allDespesasGatos;
	
	private boolean dataVisible = false;

	private List<String> columns;

	private List<DespesasGastos> completeItem;
	
	private List<Funcionarios> completeFuncionario;
	
	
	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;
	
	private String turno;
	
	private String classificacao;
	private List<Classificacao> classificacaoItens;
	
	/*----------- Search ------------*/
	private Date dataInicial;
	
	private Date dataFinal;
	
	private String mesAnterior;
	
	private String dataAtual;
	
	private DespesasGastos itemSearch;
	
	private List<String[]> relatorioDiaDoMes;
	private final int QTD_CAMPOS = 33; 
	private String[] campo;
	private double totalLinha;
	private String[] resultadoTotal;
	private String[] resultadoTotalPorItens;
	private Double total = 0.0;
	
	public List<DespesasGastos> completeItem(String query) {
        List<DespesasGastos> suggestions = new ArrayList<DespesasGastos>();
        for (DespesasGastos despesasGastos : DespesasGastos.findAllDespesasGastoses()) {
            String despesasGastosStr = String.valueOf(despesasGastos.getDescrisao());
            if (despesasGastosStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(despesasGastos);
            }
        }
        return suggestions;
    }

	public List<Funcionarios> completeFuncionario(String query) {
        List<Funcionarios> suggestions = new ArrayList<Funcionarios>();
        for (Funcionarios funcionarios : Funcionarios.findAllFuncionarioses()) {
            String funcionariosStr = String.valueOf(funcionarios.getNome() +  " "  + funcionarios.getApelido());
            if (funcionariosStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(funcionarios);
            }
        }
        return suggestions;
    }
	
	public void carregaClassificacao(){
		Long idClassific = sangria.getItem().getId();
		DespesasGastos despesasGastos = DespesasGastos.findDespesasGastos(idClassific);
		sangria.getItem().setClassificacao(despesasGastos.getClassificacao());
	}
	
	public void carregaDespesasGastos(){
		long idClassific = Long.parseLong(classificacao);
		allDespesasGatos = DespesasGastos.findAllClassificaco(idClassific);
	}	
	
	@PostConstruct
    public void init() {
		setCompleteItem(DespesasGastos.findAllDespesasGastoses());
		setCompleteFuncionario(Funcionarios.findAllFuncionarioses());
        columns = new ArrayList<String>();
        columns.add("periodo");
        columns.add("valor");
        columns.add("origem");
        sangria = new Sangria();
        initRealatorio();
        initResultadoTotalProItens();
        initResultadoTotal();
        findAllSangrias();
        findAllDespesaLoja();
        allRelatorioDiaDoMes();
    	RelatorioDiaDoMes relatorioDiaDoMes = new RelatorioDiaDoMes();
    	classificacaoItens = relatorioDiaDoMes.criarRelatorio();
        dataAtual = DataUltil.dataAtual();
        mesAnterior = DataUltil.mesAnterior();
       // sangria.setPeriodo(DataUltil.mesAnterior(new Date()));
    }

	/**
	 * Método para inicializar com 0,00 a linhas de cada itens do relatorio
	 */	
	private void initResultadoTotalProItens() {
		resultadoTotalPorItens = new String[QTD_CAMPOS];
		for(int y =1; y < QTD_CAMPOS; y++){
			resultadoTotalPorItens[y] = "0";
		}
		resultadoTotalPorItens[0] ="TOTAL DO ITEM";
	}

	/**
	 * Método para inicializar com 0,00 a linhas do relatorio
	 */	
	private void initRealatorio() {
		campo = new String[QTD_CAMPOS];		
		for(int i =1; i < QTD_CAMPOS; i++){
			campo[i] = "####";
		}
	}

	/**
	 * Método para inicializar com 0,00 a ultima linha do relatorio
	 */
	private void initResultadoTotal() {
		resultadoTotal = new String[QTD_CAMPOS];
		for(int y =1; y < QTD_CAMPOS; y++){
			resultadoTotal[y] = "0";
		}
		resultadoTotal[0] ="TOTAL GERAL:";
	}

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Sangria> getAllSangrias() {
		findAllSangrias();
        return allSangrias;
    }

	public void setAllSangrias(List<Sangria> allSangrias) {
        this.allSangrias = allSangrias;
    }
	
	public String findAllSangrias() {
        allSangrias = Sangria.findAllSangriasAtivas();
        return null;
    }
	
	public String findAllDespesaLoja() {
        allDespesaLoja = Sangria.findAllSangrias();
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
        periodoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.periodo}", Date.class));
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
        
        OutputLabel valorCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorCreateOutput.setFor("valorCreateInput");
        valorCreateOutput.setId("valorCreateOutput");
        valorCreateOutput.setValue("Valor:");
        htmlPanelGrid.getChildren().add(valorCreateOutput);
        
        InputText valorCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorCreateInput.setId("valorCreateInput");
        valorCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.valor}", Double.class));
        valorCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(valorCreateInput);
        
        Message valorCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorCreateInputMessage.setId("valorCreateInputMessage");
        valorCreateInputMessage.setFor("valorCreateInput");
        valorCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorCreateInputMessage);
        
        OutputLabel origemCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        origemCreateOutput.setFor("origemCreateInput");
        origemCreateOutput.setId("origemCreateOutput");
        origemCreateOutput.setValue("Origem:");
        htmlPanelGrid.getChildren().add(origemCreateOutput);
        
        InputText origemCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        origemCreateInput.setId("origemCreateInput");
        origemCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.origem}", String.class));
        origemCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(origemCreateInput);
        
        Message origemCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        origemCreateInputMessage.setId("origemCreateInputMessage");
        origemCreateInputMessage.setFor("origemCreateInput");
        origemCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(origemCreateInputMessage);
        
        OutputLabel itemCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        itemCreateOutput.setFor("itemCreateInput");
        itemCreateOutput.setId("itemCreateOutput");
        itemCreateOutput.setValue("Item:");
        htmlPanelGrid.getChildren().add(itemCreateOutput);
        
        AutoComplete itemCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        itemCreateInput.setId("itemCreateInput");
        itemCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.item}", DespesasGastos.class));
        itemCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{sangriaBean.completeItem}", List.class, new Class[] { String.class }));
        itemCreateInput.setDropdown(true);
        itemCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "item", String.class));
        itemCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{item.descrisao}", String.class));
        itemCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{item}", DespesasGastos.class));
        itemCreateInput.setConverter(new DespesasGastosConverter());
        itemCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(itemCreateInput);
        
        Message itemCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        itemCreateInputMessage.setId("itemCreateInputMessage");
        itemCreateInputMessage.setFor("itemCreateInput");
        itemCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(itemCreateInputMessage);
        
        OutputLabel funcionarioCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        funcionarioCreateOutput.setFor("funcionarioCreateInput");
        funcionarioCreateOutput.setId("funcionarioCreateOutput");
        funcionarioCreateOutput.setValue("Funcionario:");
        htmlPanelGrid.getChildren().add(funcionarioCreateOutput);
        
        AutoComplete funcionarioCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        funcionarioCreateInput.setId("funcionarioCreateInput");
        funcionarioCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.funcionario}", Funcionarios.class));
        funcionarioCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{sangriaBean.completeFuncionario}", List.class, new Class[] { String.class }));
        funcionarioCreateInput.setDropdown(true);
        funcionarioCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "funcionario", String.class));
        funcionarioCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{funcionario.nome} #{funcionario.apelido} #{funcionario.salario} #{funcionario.cpf}", String.class));
        funcionarioCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{funcionario}", Funcionarios.class));
        funcionarioCreateInput.setConverter(new FuncionariosConverter());
        funcionarioCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(funcionarioCreateInput);
        
        Message funcionarioCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        funcionarioCreateInputMessage.setId("funcionarioCreateInputMessage");
        funcionarioCreateInputMessage.setFor("funcionarioCreateInput");
        funcionarioCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(funcionarioCreateInputMessage);
        
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
        periodoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.periodo}", Date.class));
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
        
        OutputLabel valorEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorEditOutput.setFor("valorEditInput");
        valorEditOutput.setId("valorEditOutput");
        valorEditOutput.setValue("Valor:");
        htmlPanelGrid.getChildren().add(valorEditOutput);
        
        InputText valorEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorEditInput.setId("valorEditInput");
        valorEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.valor}", Double.class));
        valorEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(valorEditInput);
        
        Message valorEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorEditInputMessage.setId("valorEditInputMessage");
        valorEditInputMessage.setFor("valorEditInput");
        valorEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorEditInputMessage);
        
        OutputLabel origemEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        origemEditOutput.setFor("origemEditInput");
        origemEditOutput.setId("origemEditOutput");
        origemEditOutput.setValue("Origem:");
        htmlPanelGrid.getChildren().add(origemEditOutput);
        
        InputText origemEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        origemEditInput.setId("origemEditInput");
        origemEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.origem}", String.class));
        origemEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(origemEditInput);
        
        Message origemEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        origemEditInputMessage.setId("origemEditInputMessage");
        origemEditInputMessage.setFor("origemEditInput");
        origemEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(origemEditInputMessage);
        
        OutputLabel itemEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        itemEditOutput.setFor("itemEditInput");
        itemEditOutput.setId("itemEditOutput");
        itemEditOutput.setValue("Item:");
        htmlPanelGrid.getChildren().add(itemEditOutput);
        
        AutoComplete itemEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        itemEditInput.setId("itemEditInput");
        itemEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.item}", DespesasGastos.class));
        itemEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{sangriaBean.completeItem}", List.class, new Class[] { String.class }));
        itemEditInput.setDropdown(true);
        itemEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "item", String.class));
        itemEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{item.descrisao}", String.class));
        itemEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{item}", DespesasGastos.class));
        itemEditInput.setConverter(new DespesasGastosConverter());
        itemEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(itemEditInput);
        
        Message itemEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        itemEditInputMessage.setId("itemEditInputMessage");
        itemEditInputMessage.setFor("itemEditInput");
        itemEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(itemEditInputMessage);
        
        OutputLabel funcionarioEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        funcionarioEditOutput.setFor("funcionarioEditInput");
        funcionarioEditOutput.setId("funcionarioEditOutput");
        funcionarioEditOutput.setValue("Funcionario:");
        htmlPanelGrid.getChildren().add(funcionarioEditOutput);
        
        AutoComplete funcionarioEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        funcionarioEditInput.setId("funcionarioEditInput");
        funcionarioEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.funcionario}", Funcionarios.class));
        funcionarioEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{sangriaBean.completeFuncionario}", List.class, new Class[] { String.class }));
        funcionarioEditInput.setDropdown(true);
        funcionarioEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "funcionario", String.class));
        funcionarioEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{funcionario.nome} #{funcionario.apelido}", String.class));
        funcionarioEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{funcionario}", Funcionarios.class));
        funcionarioEditInput.setConverter(new FuncionariosConverter());
        funcionarioEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(funcionarioEditInput);
        
        Message funcionarioEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        funcionarioEditInputMessage.setId("funcionarioEditInputMessage");
        funcionarioEditInputMessage.setFor("funcionarioEditInput");
        funcionarioEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(funcionarioEditInputMessage);
        
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
        periodoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.periodo}", Calendar.class));
        DateTimeConverter periodoValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        periodoValueConverter.setPattern("dd/MM/yyyy");
        periodoValue.setConverter(periodoValueConverter);
        htmlPanelGrid.getChildren().add(periodoValue);
        
        HtmlOutputText valorLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorLabel.setId("valorLabel");
        valorLabel.setValue("Valor:");
        htmlPanelGrid.getChildren().add(valorLabel);
        
        HtmlOutputText valorValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.valor}", String.class));
        htmlPanelGrid.getChildren().add(valorValue);
        
        HtmlOutputText origemLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        origemLabel.setId("origemLabel");
        origemLabel.setValue("Origem:");
        htmlPanelGrid.getChildren().add(origemLabel);
        
        HtmlOutputText origemValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        origemValue.setId("origemValue");
        origemValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.origem}", String.class));
        htmlPanelGrid.getChildren().add(origemValue);
        
        HtmlOutputText itemLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        itemLabel.setId("itemLabel");
        itemLabel.setValue("Item:");
        htmlPanelGrid.getChildren().add(itemLabel);
        
        HtmlOutputText itemValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        itemValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.item}", DespesasGastos.class));
        itemValue.setConverter(new DespesasGastosConverter());
        htmlPanelGrid.getChildren().add(itemValue);
        
        HtmlOutputText funcionarioLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        funcionarioLabel.setId("funcionarioLabel");
        funcionarioLabel.setValue("Funcionario:");
        htmlPanelGrid.getChildren().add(funcionarioLabel);
        
        HtmlOutputText funcionarioValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        funcionarioValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{sangriaBean.sangria.funcionario}", Funcionarios.class));
        funcionarioValue.setConverter(new FuncionariosConverter());
        htmlPanelGrid.getChildren().add(funcionarioValue);
        
        return htmlPanelGrid;
    }

	public Sangria getSangria() {
        if (sangria == null) {
            sangria = new Sangria();
        }
        return sangria;
    }

	public void setSangria(Sangria sangria) {
        this.sangria = sangria;
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
        findAllSangrias();
        return "sangria";
    }

	public String displayCreateDialog() {
        sangria = new Sangria();
        createDialogVisible = true;
        return "sangria";
    }

	public String persist() {
        String message = "";
        if (sangria.getId() != null) {
        	sangria.setPeriodo(new Date());
            sangria.merge();
            message = "message_successfully_updated";
        } else {
        	if(sangria.getOrigem() != null) {
        		sangria.setPeriodo(new Date());
        		sangria.setSangria(true);
        	} else {
        		sangria.setSangria(false);
        	}
            sangria.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Sangria");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        init();
        return findAllSangrias();
    }

	public String delete() {
		try {
			sangria.remove();
			FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "DespesasGastos");
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        reset();
	        init();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O item não pode ser deletado porque possui dependências em outros módulos", "O item não pode ser deletado porque possui dependências em outros módulos"));
		}
        return findAllSangrias();
    }

	public void reset() {
        sangria = null;
        createDialogVisible = false;
        dataInicial = null;
        dataFinal = null;
        itemSearch = null;
        findAllDespesaLoja();
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	public List<Funcionarios> getCompleteFuncionario() {
		return completeFuncionario;
	}

	public void setCompleteFuncionario(List<Funcionarios> completeFuncionario) {
		this.completeFuncionario = completeFuncionario;
	}

	public List<DespesasGastos> getCompleteItem() {
		return completeItem;
	}

	public void setCompleteItem(List<DespesasGastos> completeItem) {
		this.completeItem = completeItem;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
	
	public List<Sangria> getAllDespesaLoja() {
		return allDespesaLoja;
	}

	public void setAllDespesaLoja(List<Sangria> allDespesaLoja) {
		this.allDespesaLoja = allDespesaLoja;
	}

	public List<Despesas> completeClasssificacao(String query) {
        List<Despesas> suggestions = new ArrayList<Despesas>();
        for (Despesas despesas : Despesas.findAllDespesases()) {
            String despesasStr = String.valueOf(despesas.getCodigo() +  " "  + despesas.getDescricao() +  " "  + despesas.getIdResumo());
            if (despesasStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(despesas);
            }
        }
        return suggestions;
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

	public List<DespesasGastos> getAllDespesasGatos() {
		return allDespesasGatos;
	}

	public void setAllDespesasGatos(List<DespesasGastos> allDespesasGatos) {
		this.allDespesasGatos = allDespesasGatos;
	}
	
	public String relatorioDespesas() {
        allSangrias = Sangria.findAllSangriasAtivas();
        return null;
    }

	public List<String[]> getRelatorioDiaDoMes() {
		return relatorioDiaDoMes;
	}

	public void setRelatorioDiaDoMes(List<String[]> relatorioDiaDoMes) {
		this.relatorioDiaDoMes = relatorioDiaDoMes;
	}
	
	/**
	 * Método que alimenta todos dados do relatorio
	 * 
	 * @param 
	 *            
	 */	
	public List<String[]> allRelatorioDiaDoMes() {
		List<String[]>  linha = new ArrayList<String[]>();
		for(Despesas classificacao :findAllClassificacao()){
			campo = new String[QTD_CAMPOS];
			campo[0]=classificacao.getCodigo()+" - "+classificacao.getDescricao();
			linha.add(campo);
			dadosItens(classificacao.getId(),linha);
			//Necessario para fazer calculo para cada classificação de itens.
			initResultadoTotalProItens();
		}
		linha.add(resultadoTotal);
		relatorioDiaDoMes = linha;
		return linha;
	}
	/**
	 * Método que carrega do dados do itens.
	 * @param id -- Id da classificação
	 * @param linha -- Linha do relatorio.
	 */		
	private void dadosItens(Long id, List<String[]> linha) {
		for (DespesasGastos itens : findAllDespasGastosByClassificao(id)) {
			String[] campo = new String[QTD_CAMPOS];
			campo[0] = " - "+itens.getDescrisao();
			linha.add(valoresItens(campo,itens.getId()));
		}
		linha.add(resultadoTotalPorItens);
	}
	
	/**
	 * Método que retorna valores de uma linha.
	 * @param String[] campo  -- valor de cada coluna
	 * @param Long idItens 	  -- id dos itens
	 */	
	private String[] valoresItens(String[] campo, Long idItens) {
		List<Sangria> dadosItens = findAllSangriaByItens(idItens);
		totalLinha = 0;
		for(int i =1; i < QTD_CAMPOS; i++){
			campo[i] = "0";
			if(dadosItens.size() != 0)
				dados(i, campo, idItens,dadosItens);
		}
		campo[QTD_CAMPOS-1]= String.valueOf(totalLinha);
		total = total + totalLinha;
	
		return campo;
	}

	/**
	 * Método que retorna dado de um iten.
	 * @param int i --Valor do dia
	 * @param String[] campo  -- valor de cada coluna
	 * @param Long idItens 	  -- id dos itens
	 * @param List<Sangria> dadosItens --dados da despesas            
	 */		
	private void dados(int i, String[] campo, Long idItens, List<Sangria> dadosItens) {
		for (Sangria dado : dadosItens) {
			if(dado.getPeriodo().getDate() == i && dado.getValor()!=null){
				campo[i] = String.valueOf(dado.getValor());
				totalLinha = totalLinha + dado.getValor();
				linhaTotalFinalPorItem(i,dado.getValor());
				linhaTotalFinal(i,dado.getValor());
			}
		}
	}

	/**
	 * Método que cria a ultima linha de cada item.
	 * @param int i - Numero da posição do campo no relatorio.
	 * @param int i - Valor do campo. 
	 */	
	private String[] linhaTotalFinalPorItem(int i, Double valor) {
		Double tempValor = Double.parseDouble(resultadoTotalPorItens[i]);
		DecimalFormat df = new DecimalFormat("#.##");
		resultadoTotalPorItens[i] = df.format(tempValor + valor).replace(",", ".");
		resultadoTotalPorItens[QTD_CAMPOS-1] = df.format( Double.parseDouble(resultadoTotalPorItens[QTD_CAMPOS-1] ) + Double.parseDouble(resultadoTotalPorItens[i] )).replace(",", ".");
		return resultadoTotalPorItens;
	}
	/**
	 * Método que cria a ultima linha do relatorio.
	 * @param int i - Numero da posição do campo no relatorio.
	 * @param int i - Valor do campo. 
	 */	
	private String[] linhaTotalFinal(int i, Double valor) {
		Double tempValor =  Double.parseDouble(resultadoTotal[i]);
		DecimalFormat df = new DecimalFormat("#.##");
		resultadoTotal[i] = df.format(tempValor + valor).replace(",", ".");
		resultadoTotal[QTD_CAMPOS-1] = df.format( Double.parseDouble(resultadoTotal[QTD_CAMPOS-1] ) + Double.parseDouble(resultadoTotal[i] )).replace(",", ".");
		return resultadoTotal;
	}

	/**
	 * Método que retorna todas clasificação.
	 * 
	 * @param 
	 *            
	 */		
	public List<Despesas> findAllClassificacao() {
        return  Despesas.findAllDespesases();
    }
	
	/**
	 * Método para encontrar dados dos itens por id da classificação.
	 * 
	 * @param Long id
	 *            -- Id da classificação.
	 */	
	public List<DespesasGastos> findAllDespasGastosByClassificao(Long id) {
		return DespesasGastos.findAllClassificaco(id);
	}
	
	/**
	 * Método para encontrar valores do sangria por id dos itens
	 * 
	 * @param Long id
	 *            -- Id dos itens.
	 */
	public List<Sangria> findAllSangriaByItens(Long id) {
		//allSangrias =  Sangria.findSangriaByItens(id);
		allSangrias = Sangria.paginaPorMes(new Date(), id);
		return allSangrias;
	}
	
	/*----------- Search ------------*/
	public String busca(){
		allDespesaLoja =  sangria.encontrarPorData(dataInicial, dataFinal, itemSearch);
		return "/pages/ExclusaoDespesasLoja.xhtml";
	}
	
	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public DespesasGastos getItemSearch() {
		return itemSearch;
	}

	public void setItemSearch(DespesasGastos itemSearch) {
		this.itemSearch = itemSearch;
	}
	
	public List<Classificacao> getClassificacaoItens() {
		return classificacaoItens;
	}

	public void setClassificacaoItens(List<Classificacao> classificacaoItens) {
		this.classificacaoItens = classificacaoItens;
	}

	public String getMesAnterior() {
		return mesAnterior;
	}

	public void setMesAnterior(String mesAnterior) {
		this.mesAnterior = mesAnterior;
	}

	public String getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(String dataAtual) {
		this.dataAtual = dataAtual;
	}
}