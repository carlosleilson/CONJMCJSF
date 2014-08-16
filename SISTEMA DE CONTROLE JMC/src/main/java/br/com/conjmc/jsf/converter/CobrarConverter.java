package br.com.conjmc.jsf.converter;
import br.com.conjmc.controlediario.fechamento.Cobrar;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;

@FacesConverter("cobrarConverter")
@Configurable
public class CobrarConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Cobrar.findCobrar(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Cobrar ? ((Cobrar) value).getId().toString() : "";
    }
}
