package br.com.conjmc.jsf.converter;
import br.com.conjmc.cadastrobasico.Fornecedores;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.converter.RooJsfConverter;

@FacesConverter("fornecedoresConverter")
@Configurable
@RooJsfConverter(entity = Fornecedores.class)
public class FornecedoresConverter implements Converter{

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Fornecedores.findFornecedores(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Fornecedores ? ((Fornecedores) value).getId().toString() : "";
    }
}
