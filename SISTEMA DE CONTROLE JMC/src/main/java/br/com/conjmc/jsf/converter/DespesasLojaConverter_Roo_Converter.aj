// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.conjmc.jsf.converter;

import br.com.conjmc.despesa.DespesasLoja;
import br.com.conjmc.jsf.converter.DespesasLojaConverter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

privileged aspect DespesasLojaConverter_Roo_Converter {
    
    declare parents: DespesasLojaConverter implements Converter;
    
    declare @type: DespesasLojaConverter: @FacesConverter("br.com.conjmc.jsf.converter.DespesasLojaConverter");
    
    public Object DespesasLojaConverter.getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return DespesasLoja.findDespesasLoja(id);
    }
    
    public String DespesasLojaConverter.getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof DespesasLoja ? ((DespesasLoja) value).getId().toString() : "";
    }
    
}
