package br.com.conjmc.jsf.converter;
import br.com.conjmc.cadastrobasico.Despesa_Gastos;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.converter.RooJsfConverter;

@Configurable
@FacesConverter("despesa_GastosConverter")
@RooJsfConverter(entity = Despesa_Gastos.class)
public class Despesa_GastosConverter implements Converter{

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Despesa_Gastos.findDespesa_Gastos(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Despesa_Gastos ? ((Despesa_Gastos) value).getId().toString() : "";
    }
}
