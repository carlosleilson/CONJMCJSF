package br.com.conjmc.jsf.converter;
import br.com.conjmc.cadastrobasico.Usuarios;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Configurable;

@FacesConverter("usuariosConverter")
@Configurable
public class UsuariosConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(value);
        return Usuarios.findUsuarios(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Usuarios ? ((Usuarios) value).getId().toString() : "";
    }
}
