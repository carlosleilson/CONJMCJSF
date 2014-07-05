package br.com.conjmc.jsf.converter;
import br.com.conjmc.despesa.DespesasLoja;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.converter.RooJsfConverter;

@Configurable
@FacesConverter("despesasLojaConverter")
@RooJsfConverter(entity = DespesasLoja.class)
public class DespesasLojaConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return DespesasLoja.findDespesasLoja(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof DespesasLoja ? ((DespesasLoja) value).getId().toString() : "";
    }
}
