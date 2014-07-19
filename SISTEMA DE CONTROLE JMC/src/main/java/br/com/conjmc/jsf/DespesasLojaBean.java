package br.com.conjmc.jsf;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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

import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.despesa.DespesasLoja;
import br.com.conjmc.jsf.converter.DespesasConverter;
import br.com.conjmc.jsf.converter.DespesasGastosConverter;
import br.com.conjmc.jsf.util.DataUltil;
import br.com.conjmc.jsf.util.MessageFactory;

@Configurable
@ManagedBean(name = "despesasLojaBean")
@SessionScoped
@RooSerializable
@RooJsfManagedBean(entity = DespesasLoja.class, beanName = "despesasLojaBean")
public class DespesasLojaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name = "DespesasLojas";

//	private DespesasLoja despesasLoja;
//
//	private List<DespesasLoja> allDespesasLojas;
	private Sangria despesasLoja;

	private List<Sangria> allDespesasLojas;
	
	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;
	
	private String mesAnterior;
	
	private String dataAtual;
	
	private Date dataAgora;
	private Date atedata;
	
	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("mes_ano");
        columns.add("valor");
        findAllDespesasLojas();
        //Definir que os dados são da despesas. 
        despesasLoja = new Sangria();
        despesasLoja.setSangria(false);
        dataAtual = DataUltil.dataAtual();
        mesAnterior = DataUltil.mesAnterior();
    }
	
	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Sangria> getAllDespesasLojas() {
        return allDespesasLojas;
    }

	public void setAllDespesasLojas(List<Sangria> allDespesasLojas) {
        this.allDespesasLojas = allDespesasLojas;
    }

	public String findAllDespesasLojas() {
        allDespesasLojas =  Sangria.findAllSangrias();// DespesasLoja.findAllDespesasLojas();
        dataVisible = !allDespesasLojas.isEmpty();
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
        
        OutputLabel mes_anoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        mes_anoCreateOutput.setFor("mes_anoCreateInput");
        mes_anoCreateOutput.setId("mes_anoCreateOutput");
        mes_anoCreateOutput.setValue("Mes_ano:");
        htmlPanelGrid.getChildren().add(mes_anoCreateOutput);
        
        Calendar mes_anoCreateInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        mes_anoCreateInput.setId("mes_anoCreateInput");
        mes_anoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.mes_ano}", Date.class));
        mes_anoCreateInput.setNavigator(true);
        mes_anoCreateInput.setEffect("slideDown");
        mes_anoCreateInput.setPattern("dd/MM/yyyy");
        mes_anoCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(mes_anoCreateInput);
        
        Message mes_anoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        mes_anoCreateInputMessage.setId("mes_anoCreateInputMessage");
        mes_anoCreateInputMessage.setFor("mes_anoCreateInput");
        mes_anoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(mes_anoCreateInputMessage);
        
        OutputLabel valorCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorCreateOutput.setFor("valorCreateInput");
        valorCreateOutput.setId("valorCreateOutput");
        valorCreateOutput.setValue("Valor:");
        htmlPanelGrid.getChildren().add(valorCreateOutput);
        
        InputText valorCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorCreateInput.setId("valorCreateInput");
        valorCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.valor}", Double.class));
        valorCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(valorCreateInput);
        
        Message valorCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorCreateInputMessage.setId("valorCreateInputMessage");
        valorCreateInputMessage.setFor("valorCreateInput");
        valorCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorCreateInputMessage);
        
        OutputLabel classificacaoCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        classificacaoCreateOutput.setFor("classificacaoCreateInput");
        classificacaoCreateOutput.setId("classificacaoCreateOutput");
        classificacaoCreateOutput.setValue("Classificacao:");
        htmlPanelGrid.getChildren().add(classificacaoCreateOutput);
        
        AutoComplete classificacaoCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        classificacaoCreateInput.setId("classificacaoCreateInput");
        classificacaoCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.classificacao}", Despesas.class));
        classificacaoCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{despesasLojaBean.completeClassificacao}", List.class, new Class[] { String.class }));
        classificacaoCreateInput.setDropdown(true);
        classificacaoCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "classificacao", String.class));
        classificacaoCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{classificacao.codigo} #{classificacao.descricao} #{classificacao.idResumo}", String.class));
        classificacaoCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{classificacao}", Despesas.class));
        classificacaoCreateInput.setConverter(new DespesasConverter());
        classificacaoCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(classificacaoCreateInput);
        
        Message classificacaoCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        classificacaoCreateInputMessage.setId("classificacaoCreateInputMessage");
        classificacaoCreateInputMessage.setFor("classificacaoCreateInput");
        classificacaoCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(classificacaoCreateInputMessage);
        
        OutputLabel itemCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        itemCreateOutput.setFor("itemCreateInput");
        itemCreateOutput.setId("itemCreateOutput");
        itemCreateOutput.setValue("Item:");
        htmlPanelGrid.getChildren().add(itemCreateOutput);
        
        AutoComplete itemCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        itemCreateInput.setId("itemCreateInput");
        itemCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.item}", DespesasGastos.class));
        itemCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{despesasLojaBean.completeItem}", List.class, new Class[] { String.class }));
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
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        OutputLabel mes_anoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        mes_anoEditOutput.setFor("mes_anoEditInput");
        mes_anoEditOutput.setId("mes_anoEditOutput");
        mes_anoEditOutput.setValue("Mes_ano:");
        htmlPanelGrid.getChildren().add(mes_anoEditOutput);
        
        Calendar mes_anoEditInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        mes_anoEditInput.setId("mes_anoEditInput");
        mes_anoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.mes_ano}", Date.class));
        mes_anoEditInput.setNavigator(true);
        mes_anoEditInput.setEffect("slideDown");
        mes_anoEditInput.setPattern("dd/MM/yyyy");
        mes_anoEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(mes_anoEditInput);
        
        Message mes_anoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        mes_anoEditInputMessage.setId("mes_anoEditInputMessage");
        mes_anoEditInputMessage.setFor("mes_anoEditInput");
        mes_anoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(mes_anoEditInputMessage);
        
        OutputLabel valorEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        valorEditOutput.setFor("valorEditInput");
        valorEditOutput.setId("valorEditOutput");
        valorEditOutput.setValue("Valor:");
        htmlPanelGrid.getChildren().add(valorEditOutput);
        
        InputText valorEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        valorEditInput.setId("valorEditInput");
        valorEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.valor}", Double.class));
        valorEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(valorEditInput);
        
        Message valorEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        valorEditInputMessage.setId("valorEditInputMessage");
        valorEditInputMessage.setFor("valorEditInput");
        valorEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(valorEditInputMessage);
        
        OutputLabel classificacaoEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        classificacaoEditOutput.setFor("classificacaoEditInput");
        classificacaoEditOutput.setId("classificacaoEditOutput");
        classificacaoEditOutput.setValue("Classificacao:");
        htmlPanelGrid.getChildren().add(classificacaoEditOutput);
        
        AutoComplete classificacaoEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        classificacaoEditInput.setId("classificacaoEditInput");
        classificacaoEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.classificacao}", Despesas.class));
        classificacaoEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{despesasLojaBean.completeClassificacao}", List.class, new Class[] { String.class }));
        classificacaoEditInput.setDropdown(true);
        classificacaoEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "classificacao", String.class));
        classificacaoEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{classificacao.codigo} #{classificacao.descricao} #{classificacao.idResumo}", String.class));
        classificacaoEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{classificacao}", Despesas.class));
        classificacaoEditInput.setConverter(new DespesasConverter());
        classificacaoEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(classificacaoEditInput);
        
        Message classificacaoEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        classificacaoEditInputMessage.setId("classificacaoEditInputMessage");
        classificacaoEditInputMessage.setFor("classificacaoEditInput");
        classificacaoEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(classificacaoEditInputMessage);
        
        OutputLabel itemEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        itemEditOutput.setFor("itemEditInput");
        itemEditOutput.setId("itemEditOutput");
        itemEditOutput.setValue("Item:");
        htmlPanelGrid.getChildren().add(itemEditOutput);
        
        AutoComplete itemEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        itemEditInput.setId("itemEditInput");
        itemEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.item}", DespesasGastos.class));
        itemEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{despesasLojaBean.completeItem}", List.class, new Class[] { String.class }));
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
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText mes_anoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        mes_anoLabel.setId("mes_anoLabel");
        mes_anoLabel.setValue("Mes_ano:");
        htmlPanelGrid.getChildren().add(mes_anoLabel);
        
        HtmlOutputText mes_anoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        mes_anoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.mes_ano}", Calendar.class));
        DateTimeConverter mes_anoValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        mes_anoValueConverter.setPattern("dd/MM/yyyy");
        mes_anoValue.setConverter(mes_anoValueConverter);
        htmlPanelGrid.getChildren().add(mes_anoValue);
        
        HtmlOutputText valorLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorLabel.setId("valorLabel");
        valorLabel.setValue("Valor:");
        htmlPanelGrid.getChildren().add(valorLabel);
        
        HtmlOutputText valorValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        valorValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.valor}", String.class));
        htmlPanelGrid.getChildren().add(valorValue);
        
        HtmlOutputText classificacaoLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        classificacaoLabel.setId("classificacaoLabel");
        classificacaoLabel.setValue("Classificacao:");
        htmlPanelGrid.getChildren().add(classificacaoLabel);
        
        HtmlOutputText classificacaoValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        classificacaoValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.classificacao}", Despesas.class));
        classificacaoValue.setConverter(new DespesasConverter());
        htmlPanelGrid.getChildren().add(classificacaoValue);
        
        HtmlOutputText itemLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        itemLabel.setId("itemLabel");
        itemLabel.setValue("Item:");
        htmlPanelGrid.getChildren().add(itemLabel);
        
        HtmlOutputText itemValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        itemValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.item}", DespesasGastos.class));
        itemValue.setConverter(new DespesasGastosConverter());
        htmlPanelGrid.getChildren().add(itemValue);
        
        return htmlPanelGrid;
    }

	public Sangria getDespesasLoja() {
        if (despesasLoja == null) {
            despesasLoja = new Sangria();
        }
        return despesasLoja;
    }

	public void setDespesasLoja(Sangria despesasLoja) {
        this.despesasLoja = despesasLoja;
    }

	public List<Despesas> desespesaMes() {
        List<Despesas> suggestions = new ArrayList<Despesas>();
        for (Despesas despesas : Despesas.findAllDespesases()) {
        }
        return Despesas.findAllDespesases();
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
        findAllDespesasLojas();
        return "despesasLoja";
    }

	public String displayCreateDialog() {
        despesasLoja = new Sangria();
        createDialogVisible = true;
        return "despesasLoja";
    }

	public String persist() {
        String message = "";
        try {
			if (despesasLoja.getId() != null) {
			    despesasLoja.merge();
			    message = "message_successfully_updated";
			} else {
			    despesasLoja.persist();
			    message = "message_successfully_created";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
		}finally{
	        RequestContext context = RequestContext.getCurrentInstance();
	        context.execute("createDialogWidget.hide()");
	        context.execute("editDialogWidget.hide()");
	        
	        FacesMessage facesMessage = MessageFactory.getMessage(message, "Despesas");
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        reset();
		}
        return findAllDespesasLojas();
    }

	public String delete() {
		String message = "";
        try {
			despesasLoja.remove();
			message = "message_successfully_deleted";
		} catch (Exception e) {
			e.printStackTrace();
			message = "Error: "+e.getMessage();
		}finally{
			FacesMessage facesMessage = MessageFactory.getMessage(message, "Despesas");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
			reset();
		}
        return findAllDespesasLojas();
    }

	public void reset() {
        despesasLoja = null;
        createDialogVisible = false;
    }

	public String busca(){
		allDespesasLojas =  Sangria.encontrarPorData(getDataAgora(), getAtedata(), getDespesasLoja().getItem()); //DespesasLoja.encontrarPorData(getDataAgora(), getAtedata(), getDespesasLoja().getItem());
		return "/pages/ExclusaoDespesasLoja.xhtml";
	}
	
	public String limpar(){
		this.reset();
		return findAllDespesasLojas();
	}
	
	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	public String getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(String dataAtual) {
		this.dataAtual = dataAtual;
	}

	public String getMesAnterior() {
		return mesAnterior;
	}

	public void setMesAnterior(String mesAnterior) {
		this.mesAnterior = mesAnterior;
	}

	public Date getDataAgora() {
		return dataAgora;
	}

	public void setDataAgora(Date dataAgora) {
		this.dataAgora = dataAgora;
	}

	public Date getAtedata() {
		return atedata;
	}

	public void setAtedata(Date atedata) {
		this.atedata = atedata;
	}
}
