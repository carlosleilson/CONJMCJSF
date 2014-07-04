package br.com.conjmc.jsf.converter;
import br.com.conjmc.cadastrobasico.Funcionarios;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jsf.converter.RooJsfConverter;

@Configurable
@FacesConverter("funcionariosConverter")
@RooJsfConverter(entity = Funcionarios.class)
public class FuncionariosConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Funcionarios.findFuncionarios(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Funcionarios ? ((Funcionarios) value).getId().toString() : "";
    }
}
