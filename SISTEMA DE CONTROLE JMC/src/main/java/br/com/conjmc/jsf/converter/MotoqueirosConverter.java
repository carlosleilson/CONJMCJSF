package br.com.conjmc.jsf.converter;
import br.com.conjmc.cadastrobasico.Motoqueiros;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@FacesConverter("motoqueirosConverter")
public class MotoqueirosConverter implements Converter{

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Motoqueiros.findMotoqueiros(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Motoqueiros ? ((Motoqueiros) value).getId().toString() : "";
    }
}
