package br.com.conjmc.jsf;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.validator.LengthValidator;
import javax.inject.Inject;

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.cadastrobasico.Perfil;
import br.com.conjmc.cadastrobasico.Usuarios;
import br.com.conjmc.jsf.converter.FuncionariosConverter;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;
import br.com.conjmc.service.impl.UsuariosServiceImpl;

@ManagedBean(name = "usuariosBean")
@SessionScoped
@Configurable
public class UsuariosBean implements Serializable {

	@Inject
	private UsuariosServiceImpl usuariosService; 
	
	private static final long serialVersionUID = 1L;

	private String name = "Usuarioses";

	private Usuarios usuarios;

	private List<Usuarios> allUsuarioses;

	private boolean dataVisible = false;

	private Perfil perfil;
	
	private List<Perfil> allTypes;
	
	private Usuarios usuarioLogado;	
	
	public Usuarios getUsuarioLogado() {
		return (Usuarios) ObejctSession.getObjectSession("usuarioLogado");
	}
	
	public void setUsuarioLogado(Usuarios usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	} 
	
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getAllTypes() {
		return Arrays.asList(Perfil.values());
	}

	public void setAllTypes(List<Perfil> allTypes) {
		this.allTypes = allTypes;
	}

	public String getName() {
        return name;
    }

	public List<Usuarios> getAllUsuarioses() {
        return usuariosService.findAllUsuarios();
    }

	public void setAllUsuarioses(List<Usuarios> allUsuarioses) {
        this.allUsuarioses = allUsuarioses;
    }

	public String findAllUsuarioses() {
        allUsuarioses = usuariosService.findAllUsuarios();//  Usuarios.findAllUsuarioses();
        dataVisible = !allUsuarioses.isEmpty();
        return null;
    }

	public boolean isDataVisible() {
        return dataVisible;
    }

	public void setDataVisible(boolean dataVisible) {
        this.dataVisible = dataVisible;
    }

	public Usuarios getUsuarios() {
        if (usuarios == null) {
            usuarios = new Usuarios();
        }
        return usuarios;
    }

	public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

	public List<Funcionarios> completeNome(String query) {
        List<Funcionarios> suggestions = new ArrayList<Funcionarios>();
        for (Funcionarios funcionarios : Funcionarios.findAllFuncionarioses()) {
            String funcionariosStr = String.valueOf(funcionarios.getNome() +  " "  + funcionarios.getApelido() +  " "  + funcionarios.getSalario() +  " "  + funcionarios.getCpf());
            if (funcionariosStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(funcionarios);
            }
        }
        return suggestions;
    }

	public List<Perfil> completePerfil(String query) {
        List<Perfil> suggestions = new ArrayList<Perfil>();
        for (Perfil perfil : Perfil.values()) {
            if (perfil.name().toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(perfil);
            }
        }
        return suggestions;
    }

	public String onEdit() {
        return null;
    }

	public String persist() {
        String message = "";
        if (usuarios.getId() != null) {
        	//usuarios.merge();
        	usuariosService.alterar(usuarios);
            message = "message_successfully_updated";
        } else {
           // usuarios.persist();
        	usuariosService.gravar();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Usuarios");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        findAllUsuarioses();
        return "/pages/usuarios.xhtml";
    }

	public String delete() {
        //usuarios.remove();
		usuariosService.excluir();
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Usuarios");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllUsuarioses();
    }

	public void reset() {
        usuarios = null;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}
