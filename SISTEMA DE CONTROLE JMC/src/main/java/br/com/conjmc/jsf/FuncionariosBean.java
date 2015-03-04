package br.com.conjmc.jsf;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;
import br.com.conjmc.cadastrobasico.Cargos;
import br.com.conjmc.cadastrobasico.Funcionarios;
import br.com.conjmc.jsf.util.MessageFactory;

@Configurable
@ManagedBean(name = "funcionariosBean")
@SessionScoped
public class FuncionariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = "Funcionarioses";

	private Funcionarios funcionarios;
	
	private List<Funcionarios> allFuncionariosAtivos;

	private boolean dataVisible = false;

	private List<String> columns;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("nome");
        columns.add("apelido");
        columns.add("salario");
        columns.add("cpf");
        columns.add("identidade");
        funcionarios = new Funcionarios();
        funcionarios.setSituacao(true);
        findAllFuncionariosAtivos();
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }
	
	public List<Funcionarios> getAllFuncionariosAtivos() {
		return allFuncionariosAtivos;
	}

	public void setAllFuncionariosAtivos(List<Funcionarios> allFuncionariosAtivos) {
		this.allFuncionariosAtivos = allFuncionariosAtivos;
	}
	
	public void findAllFuncionariosAtivos() {
        allFuncionariosAtivos = Funcionarios.findAllFuncionariosAtivos();
    }

	public boolean isDataVisible() {
        return dataVisible;
    }

	public void setDataVisible(boolean dataVisible) {
        this.dataVisible = dataVisible;
    }

	public Funcionarios getFuncionarios() {
        if (funcionarios == null) {
            funcionarios = new Funcionarios();
        }
        return funcionarios;
    }

	public void setFuncionarios(Funcionarios funcionarios) {
        this.funcionarios = funcionarios;
    }

	public List<Cargos> completeCargo(String query) {
        List<Cargos> suggestions = new ArrayList<Cargos>();
        for (Cargos cargos : Cargos.findAllCargoses()) {
            String cargosStr = String.valueOf(cargos.getNome());
            if (cargosStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(cargos);
            }
        }
        return suggestions;
    }

	public String onEdit() {
        return null;
    }

	public boolean isCreateDialogVisible() {
        return createDialogVisible;
    }

	public void setCreateDialogVisible(boolean createDialogVisible) {
        this.createDialogVisible = createDialogVisible;
    }

	public String displayList() {
        createDialogVisible = false;
        findAllFuncionariosAtivos();
        return "funcionarios";
    }

	public String displayCreateDialog() {
        funcionarios = new Funcionarios();
        createDialogVisible = true;
        return "funcionarios";
    }

	public String persist() {
		boolean existe = true;
		String message = "";
		for (Funcionarios func : allFuncionariosAtivos) {
			if (func.getApelido().equals(funcionarios.getApelido())) {
				existe = false;
				message = "já existe funcionário com apelido cadastrado";
			} else if (func.getCpf().equals(funcionarios.getCpf())) {
				existe = false;
				message = "Já existe CPF com funcionário cadastrado";
			}
		}
		
		if (funcionarios.getId() != null) {
			boolean merge = true;
			for (Funcionarios func : allFuncionariosAtivos) {
				if ((func.getApelido().equals(funcionarios.getApelido()) && (func.getId() != funcionarios.getId()))) {
					merge = false;
					message = "já existe funcionário com apelido cadastrado";
				} else if ((func.getCpf().equals(funcionarios.getCpf()) && (func.getId() != funcionarios.getId()))) {
					merge = false;
					message = "Já existe CPF com funcionário cadastrado";
				}
			}
			if (merge) {
				funcionarios.merge();
				message = "message_successfully_updated";
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("createDialogWidget.hide()");
				context.execute("editDialogWidget.hide()");			
				FacesMessage facesMessage = MessageFactory.getMessage(message, "Funcionarios");
				FacesContext.getCurrentInstance().addMessage(null, facesMessage);
				init();
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, " "));
				findAllFuncionariosAtivos();
			}
		} else {
			if (existe) {
				funcionarios.persist();
				message = "message_successfully_created";
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("createDialogWidget.hide()");
				context.execute("editDialogWidget.hide()");			
				FacesMessage facesMessage = MessageFactory.getMessage(message, "Funcionarios");
				FacesContext.getCurrentInstance().addMessage(null, facesMessage);
				init();
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, " "));
			}
		}
        return "/pages/funcionarios.xhtml";
    }

	public String delete() {
		try {
			funcionarios.setSituacao(false);
			funcionarios.merge();
			FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "DespesasGastos");
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        init();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O item não pode ser deletado porque possui dependências em outros módulos", "O item não pode ser deletado porque possui dependências em outros módulos"));
		}
        return "funcionarios.xhtml";
    }

	public void reset() {
        funcionarios = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}
