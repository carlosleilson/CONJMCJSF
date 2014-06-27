// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.conjmc.jsf;

import br.com.conjmc.cadastrobasico.Despesa_Gastos;
import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.despesa.DespesasLoja;
import br.com.conjmc.jsf.DespesasLojaBean;
import br.com.conjmc.jsf.converter.Despesa_GastosConverter;
import br.com.conjmc.jsf.converter.DespesasConverter;
import br.com.conjmc.jsf.util.MessageFactory;
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

privileged aspect DespesasLojaBean_Roo_ManagedBean {
    
    declare @type: DespesasLojaBean: @ManagedBean(name = "despesasLojaBean");
    
    declare @type: DespesasLojaBean: @SessionScoped;
    
    private String DespesasLojaBean.name = "DespesasLojas";
    
    private DespesasLoja DespesasLojaBean.despesasLoja;
    
    private List<DespesasLoja> DespesasLojaBean.allDespesasLojas;
    
    private boolean DespesasLojaBean.dataVisible = false;
    
    private List<String> DespesasLojaBean.columns;
    
    private HtmlPanelGrid DespesasLojaBean.createPanelGrid;
    
    private HtmlPanelGrid DespesasLojaBean.editPanelGrid;
    
    private HtmlPanelGrid DespesasLojaBean.viewPanelGrid;
    
    private boolean DespesasLojaBean.createDialogVisible = false;
    
    @PostConstruct
    public void DespesasLojaBean.init() {
        columns = new ArrayList<String>();
        columns.add("mes_ano");
        columns.add("dia");
        columns.add("valor");
    }
    
    public String DespesasLojaBean.getName() {
        return name;
    }
    
    public List<String> DespesasLojaBean.getColumns() {
        return columns;
    }
    
    public List<DespesasLoja> DespesasLojaBean.getAllDespesasLojas() {
        return allDespesasLojas;
    }
    
    public void DespesasLojaBean.setAllDespesasLojas(List<DespesasLoja> allDespesasLojas) {
        this.allDespesasLojas = allDespesasLojas;
    }
    
    public String DespesasLojaBean.findAllDespesasLojas() {
        allDespesasLojas = DespesasLoja.findAllDespesasLojas();
        dataVisible = !allDespesasLojas.isEmpty();
        return null;
    }
    
    public boolean DespesasLojaBean.isDataVisible() {
        return dataVisible;
    }
    
    public void DespesasLojaBean.setDataVisible(boolean dataVisible) {
        this.dataVisible = dataVisible;
    }
    
    public HtmlPanelGrid DespesasLojaBean.getCreatePanelGrid() {
        if (createPanelGrid == null) {
            createPanelGrid = populateCreatePanel();
        }
        return createPanelGrid;
    }
    
    public void DespesasLojaBean.setCreatePanelGrid(HtmlPanelGrid createPanelGrid) {
        this.createPanelGrid = createPanelGrid;
    }
    
    public HtmlPanelGrid DespesasLojaBean.getEditPanelGrid() {
        if (editPanelGrid == null) {
            editPanelGrid = populateEditPanel();
        }
        return editPanelGrid;
    }
    
    public void DespesasLojaBean.setEditPanelGrid(HtmlPanelGrid editPanelGrid) {
        this.editPanelGrid = editPanelGrid;
    }
    
    public HtmlPanelGrid DespesasLojaBean.getViewPanelGrid() {
        return populateViewPanel();
    }
    
    public void DespesasLojaBean.setViewPanelGrid(HtmlPanelGrid viewPanelGrid) {
        this.viewPanelGrid = viewPanelGrid;
    }
    
    public HtmlPanelGrid DespesasLojaBean.populateCreatePanel() {
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
        
        OutputLabel diaCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        diaCreateOutput.setFor("diaCreateInput");
        diaCreateOutput.setId("diaCreateOutput");
        diaCreateOutput.setValue("Dia:");
        htmlPanelGrid.getChildren().add(diaCreateOutput);
        
        Calendar diaCreateInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        diaCreateInput.setId("diaCreateInput");
        diaCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.dia}", Date.class));
        diaCreateInput.setNavigator(true);
        diaCreateInput.setEffect("slideDown");
        diaCreateInput.setPattern("dd/MM/yyyy");
        diaCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(diaCreateInput);
        
        Message diaCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        diaCreateInputMessage.setId("diaCreateInputMessage");
        diaCreateInputMessage.setFor("diaCreateInput");
        diaCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(diaCreateInputMessage);
        
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
        itemCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.item}", Despesa_Gastos.class));
        itemCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{despesasLojaBean.completeItem}", List.class, new Class[] { String.class }));
        itemCreateInput.setDropdown(true);
        itemCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "item", String.class));
        itemCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{item.descrisao}", String.class));
        itemCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{item}", Despesa_Gastos.class));
        itemCreateInput.setConverter(new Despesa_GastosConverter());
        itemCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(itemCreateInput);
        
        Message itemCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        itemCreateInputMessage.setId("itemCreateInputMessage");
        itemCreateInputMessage.setFor("itemCreateInput");
        itemCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(itemCreateInputMessage);
        
        return htmlPanelGrid;
    }
    
    public HtmlPanelGrid DespesasLojaBean.populateEditPanel() {
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
        
        OutputLabel diaEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        diaEditOutput.setFor("diaEditInput");
        diaEditOutput.setId("diaEditOutput");
        diaEditOutput.setValue("Dia:");
        htmlPanelGrid.getChildren().add(diaEditOutput);
        
        Calendar diaEditInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        diaEditInput.setId("diaEditInput");
        diaEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.dia}", Date.class));
        diaEditInput.setNavigator(true);
        diaEditInput.setEffect("slideDown");
        diaEditInput.setPattern("dd/MM/yyyy");
        diaEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(diaEditInput);
        
        Message diaEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        diaEditInputMessage.setId("diaEditInputMessage");
        diaEditInputMessage.setFor("diaEditInput");
        diaEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(diaEditInputMessage);
        
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
        itemEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.item}", Despesa_Gastos.class));
        itemEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{despesasLojaBean.completeItem}", List.class, new Class[] { String.class }));
        itemEditInput.setDropdown(true);
        itemEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "item", String.class));
        itemEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{item.descrisao}", String.class));
        itemEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{item}", Despesa_Gastos.class));
        itemEditInput.setConverter(new Despesa_GastosConverter());
        itemEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(itemEditInput);
        
        Message itemEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        itemEditInputMessage.setId("itemEditInputMessage");
        itemEditInputMessage.setFor("itemEditInput");
        itemEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(itemEditInputMessage);
        
        return htmlPanelGrid;
    }
    
    public HtmlPanelGrid DespesasLojaBean.populateViewPanel() {
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
        
        HtmlOutputText diaLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        diaLabel.setId("diaLabel");
        diaLabel.setValue("Dia:");
        htmlPanelGrid.getChildren().add(diaLabel);
        
        HtmlOutputText diaValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        diaValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.dia}", Calendar.class));
        DateTimeConverter diaValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        diaValueConverter.setPattern("dd/MM/yyyy");
        diaValue.setConverter(diaValueConverter);
        htmlPanelGrid.getChildren().add(diaValue);
        
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
        itemValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{despesasLojaBean.despesasLoja.item}", Despesa_Gastos.class));
        itemValue.setConverter(new Despesa_GastosConverter());
        htmlPanelGrid.getChildren().add(itemValue);
        
        return htmlPanelGrid;
    }
    
    public DespesasLoja DespesasLojaBean.getDespesasLoja() {
        if (despesasLoja == null) {
            despesasLoja = new DespesasLoja();
        }
        return despesasLoja;
    }
    
    public void DespesasLojaBean.setDespesasLoja(DespesasLoja despesasLoja) {
        this.despesasLoja = despesasLoja;
    }
    
    public List<Despesas> DespesasLojaBean.completeClassificacao(String query) {
        List<Despesas> suggestions = new ArrayList<Despesas>();
        for (Despesas despesas : Despesas.findAllDespesases()) {
            String despesasStr = String.valueOf(despesas.getCodigo() +  " "  + despesas.getDescricao() +  " "  + despesas.getIdResumo());
            if (despesasStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(despesas);
            }
        }
        return suggestions;
    }
    
    public List<Despesa_Gastos> DespesasLojaBean.completeItem(String query) {
        List<Despesa_Gastos> suggestions = new ArrayList<Despesa_Gastos>();
        for (Despesa_Gastos despesa_Gastos : Despesa_Gastos.findAllDespesa_Gastoses()) {
            String despesa_GastosStr = String.valueOf(despesa_Gastos.getDescrisao());
            if (despesa_GastosStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(despesa_Gastos);
            }
        }
        return suggestions;
    }
    
    public String DespesasLojaBean.onEdit() {
        return null;
    }
    
    public boolean DespesasLojaBean.isCreateDialogVisible() {
        return createDialogVisible;
    }
    
    public void DespesasLojaBean.setCreateDialogVisible(boolean createDialogVisible) {
        this.createDialogVisible = createDialogVisible;
    }
    
    public String DespesasLojaBean.displayList() {
        createDialogVisible = false;
        findAllDespesasLojas();
        return "despesasLoja";
    }
    
    public String DespesasLojaBean.displayCreateDialog() {
        despesasLoja = new DespesasLoja();
        createDialogVisible = true;
        return "despesasLoja";
    }
    
    public String DespesasLojaBean.persist() {
        String message = "";
        if (despesasLoja.getId() != null) {
            despesasLoja.merge();
            message = "message_successfully_updated";
        } else {
            despesasLoja.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "DespesasLoja");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllDespesasLojas();
    }
    
    public String DespesasLojaBean.delete() {
        despesasLoja.remove();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "DespesasLoja");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllDespesasLojas();
    }
    
    public void DespesasLojaBean.reset() {
        despesasLoja = null;
        createDialogVisible = false;
    }
    
    public void DespesasLojaBean.handleDialogClose(CloseEvent event) {
        reset();
    }
    
}
