package br.com.conjmc.jsf.converter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Configurable;

import br.com.conjmc.cadastrobasico.Lojas;

@Configurable
@FacesConverter("lojasConverter")
public class LojasConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Lojas.findLojas(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Lojas ? ((Lojas) value).getId().toString() : "";
    }
}
