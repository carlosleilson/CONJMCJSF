package br.com.conjmc.jsf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.Contas;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.MessageFactory;

@ManagedBean
@SessionScoped
public class ContasPendentesBean {

	private Contas conta;
	private String origem;
	private boolean pagarAgora;
	private List<Contas> contas;
	
	@PostConstruct
	public void init() {
		conta = new Contas();
	}
	
	public List<Contas> carregarContas() {
		contas = new Contas().findAllContas();
		return contas;
	}
	
	public String persist() {
		conta.merge();
		List<Sangria> sangria = Sangria.findSangriaConta(conta);
		
		
		for (Sangria sangria2 : sangria) {
			sangria2.setOrigem(origem);
			sangria2.setPeriodo(conta.getDataPagamento());
			sangria2.setSangria(true);
			sangria2.persist();
		}
		return "contasPendentes.xhtml";
	}

	public String delete() {
		try {
			conta.remove();
			FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Conta");
	        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	        init();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O item não pode ser deletado porque possui dependencias em outros módulos", "O item não pode ser deletado porque possui dependencias em outros módulos"));
		}
        return "contasPendentes.xhtml";
    }
	
	public void edit(){
		Sangria contaEdit = (Sangria) conta.getSangria().get(1);
		origem = contaEdit.getOrigem();
		if(contaEdit.getPeriodo() != null){
			this.pagarAgora = true;
		}
	}

	//Generate getters and setters
	public Contas getConta() {
		return conta;
	}

	public void setConta(Contas conta) {
		this.conta = conta;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
	public List<Contas> getContas() {
		return contas;
	}

	public void setContas(List<Contas> contas) {
		this.contas = contas;
	}

	public boolean isPagarAgora() {
		return pagarAgora;
	}

	public void setPagarAgora(boolean pagarAgora) {
		this.pagarAgora = pagarAgora;
	}
	
}
