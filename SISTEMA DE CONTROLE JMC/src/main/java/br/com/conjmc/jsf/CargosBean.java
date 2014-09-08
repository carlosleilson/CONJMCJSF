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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.conjmc.cadastrobasico.Cargos;
import br.com.conjmc.cadastrobasico.Setor;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.service.impl.CargosServiceImpl;

@Configurable
@ManagedBean(name = "cargosBean")
@SessionScoped
public class CargosBean implements Serializable {

	private String name = "Cargoses";

	private Cargos cargos;

	private List<Cargos> allCargoses;
	
	private Setor setor;
	
	private List<Setor> allSetor;

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public List<Setor> getAllSetor() {
		return Arrays.asList(Setor.values());
	}

	public void setAllSetor(List<Setor> allSetor) {
		this.allSetor = allSetor;
	}

	@PostConstruct
    public void init() {
        cargos = new Cargos();
       /* findAllCargoses();*/
    }

	public String getName() {
        return name;
    }

	public List<Cargos> getAllCargoses() {
        return allCargoses;
    }

	public void setAllCargoses(List<Cargos> allCargoses) {
        this.allCargoses = allCargoses;
    }

	public Cargos getCargos() {
        if (cargos == null) {
            cargos = new Cargos();
        }
        return cargos;
    }

	public void setCargos(Cargos cargos) {
        this.cargos = cargos;
    }

	public String persist() {
		CargosServiceImpl cargoService = new CargosServiceImpl();
        String message = "";
        if (cargos.getId() != null) {
            message = "message_successfully_updated";
        } else {
            message = "message_successfully_created";
        }
        cargoService.persist(cargos);
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Cargos");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "cargos.xhtml";
    }

	public String delete() {
		CargosServiceImpl cargoService = new CargosServiceImpl();
		try {
			cargoService.excluir(cargos);
			FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "DespesasGastos");
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        init();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O item não pode ser deletado porque possui dependências em outros módulos", "O item não pode ser deletado porque possui dependências em outros módulos"));
		}
        return "cargos.xhtml";
    }

	public void reset() {
        cargos = null;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }

	private static final long serialVersionUID = 1L;
}
