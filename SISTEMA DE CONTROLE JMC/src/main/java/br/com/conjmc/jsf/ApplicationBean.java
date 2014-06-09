package br.com.conjmc.jsf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.application.RooJsfApplicationBean;

@Configurable
@ManagedBean
@RequestScoped
@RooJsfApplicationBean
public class ApplicationBean {

    public String getColumnName(String column) {
        if (column == null || column.length() == 0) {
            return column;
        }
        final Pattern p = Pattern.compile("[A-Z][^A-Z]*");
        final Matcher m = p.matcher(Character.toUpperCase(column.charAt(0)) + column.substring(1));
        final StringBuilder builder = new StringBuilder();
        while (m.find()) {
            builder.append(m.group()).append(" ");
        }
        return builder.toString().trim();
    }

	private MenuModel menuModel;

//	@PostConstruct
//    public void init() {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        Application application = facesContext.getApplication();
//        ExpressionFactory expressionFactory = application.getExpressionFactory();
//        ELContext elContext = facesContext.getELContext();
//        
//        menuModel = new DefaultMenuModel();
//        Submenu submenu;
//        MenuItem item;
//        
//        submenu = new Submenu();
//        submenu.setId("cargosSubmenu");
//        submenu.setLabel("Cargos");
//        item = new MenuItem();
//        item.setId("createCargosMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{cargosBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listCargosMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{cargosBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
//        
//        submenu = new Submenu();
//        submenu.setId("cobrarSubmenu");
//        submenu.setLabel("Cobrar");
//        item = new MenuItem();
//        item.setId("createCobrarMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{cobrarBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listCobrarMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{cobrarBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
//        
//        submenu = new Submenu();
//        submenu.setId("despesa_GastosSubmenu");
//        submenu.setLabel("Despesa_Gastos");
//        item = new MenuItem();
//        item.setId("createDespesa_GastosMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{despesa_GastosBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listDespesa_GastosMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{despesa_GastosBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
//        
//        submenu = new Submenu();
//        submenu.setId("despesasSubmenu");
//        submenu.setLabel("Despesas");
//        item = new MenuItem();
//        item.setId("createDespesasMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{despesasBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listDespesasMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{despesasBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
//        
//        submenu = new Submenu();
//        submenu.setId("diariaSubmenu");
//        submenu.setLabel("Diaria");
//        item = new MenuItem();
//        item.setId("createDiariaMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{diariaBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listDiariaMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{diariaBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
//        
//        submenu = new Submenu();
//        submenu.setId("fornecedoresSubmenu");
//        submenu.setLabel("Fornecedores");
//        item = new MenuItem();
//        item.setId("createFornecedoresMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{fornecedoresBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listFornecedoresMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{fornecedoresBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
//        
//        submenu = new Submenu();
//        submenu.setId("funcionariosSubmenu");
//        submenu.setLabel("Funcionarios");
//        item = new MenuItem();
//        item.setId("createFuncionariosMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{funcionariosBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listFuncionariosMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{funcionariosBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
//        
//        submenu = new Submenu();
//        submenu.setId("lancamentosFuncionariosSubmenu");
//        submenu.setLabel("LancamentosFuncionarios");
//        item = new MenuItem();
//        item.setId("createLancamentosFuncionariosMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{lancamentosFuncionariosBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listLancamentosFuncionariosMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{lancamentosFuncionariosBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
//        
//        submenu = new Submenu();
//        submenu.setId("motoqueirosSubmenu");
//        submenu.setLabel("Motoqueiros");
//        item = new MenuItem();
//        item.setId("createMotoqueirosMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{motoqueirosBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listMotoqueirosMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{motoqueirosBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
//        
//        submenu = new Submenu();
//        submenu.setId("receberSubmenu");
//        submenu.setLabel("Receber");
//        item = new MenuItem();
//        item.setId("createReceberMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{receberBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listReceberMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{receberBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
//        
//        submenu = new Submenu();
//        submenu.setId("sangriaSubmenu");
//        submenu.setLabel("Sangria");
//        item = new MenuItem();
//        item.setId("createSangriaMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{sangriaBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listSangriaMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{sangriaBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
//        
//        submenu = new Submenu();
//        submenu.setId("usuariosSubmenu");
//        submenu.setLabel("Usuarios");
//        item = new MenuItem();
//        item.setId("createUsuariosMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_create}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{usuariosBean.displayCreateDialog}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-document");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        item = new MenuItem();
//        item.setId("listUsuariosMenuItem");
//        item.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{messages.label_list}", String.class));
//        item.setActionExpression(expressionFactory.createMethodExpression(elContext, "#{usuariosBean.displayList}", String.class, new Class[0]));
//        item.setIcon("ui-icon ui-icon-folder-open");
//        item.setAjax(false);
//        item.setAsync(false);
//        item.setUpdate(":dataForm:data");
//        submenu.getChildren().add(item);
//        menuModel.addSubmenu(submenu);
//    }
//
//	public MenuModel getMenuModel() {
//        return menuModel;
//    }
//
//	public String getAppName() {
//        return "Conjmc";
//    }
}