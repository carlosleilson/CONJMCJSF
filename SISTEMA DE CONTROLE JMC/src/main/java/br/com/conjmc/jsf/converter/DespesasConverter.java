package br.com.conjmc.jsf.converter;
import br.com.conjmc.cadastrobasico.Despesas;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.converter.RooJsfConverter;

@Configurable
@FacesConverter("br.com.conjmc.jsf.converter.DespesasConverter")
@RooJsfConverter(entity = Despesas.class)
public class DespesasConverter implements Converter{

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Despesas.findDespesas(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Despesas ? ((Despesas) value).getId().toString() : "";
    }
}
