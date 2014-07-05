package br.com.conjmc.jsf.converter;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.converter.RooJsfConverter;

@FacesConverter("sangriaConverter")
@Configurable
@RooJsfConverter(entity = Sangria.class)
public class SangriaConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Sangria.findSangria(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Sangria ? ((Sangria) value).getId().toString() : "";
    }
}
