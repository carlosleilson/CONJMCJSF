package br.com.conjmc.jsf.converter;
import br.com.conjmc.controlediario.fechamento.Receber;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.converter.RooJsfConverter;

@FacesConverter("feceberConverter")
@Configurable
@RooJsfConverter(entity = Receber.class)
public class ReceberConverter implements Converter{

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Receber.findReceber(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Receber ? ((Receber) value).getId().toString() : "";
    }
}
