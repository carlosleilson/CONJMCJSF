package br.com.conjmc.jsf;

import java.util.ArrayList;
import java.util.Date;
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
public class ContasEditBean {

	private Contas conta;
	private Sangria sangria;
	private List<Sangria> sangrias;
	private Date periodo;
	private String origem;
	private boolean pagarAgora;
	
	@PostConstruct
	public void init() {
		conta = new Contas();
		sangria = new Sangria();
		sangrias = new ArrayList<Sangria>();
	}

	//Generate getters and setters
	public Contas getConta() {
		return conta;
	}

	public void setConta(Contas conta) {
		this.conta = conta;
	}
	
	public Sangria getSangria() {
		return sangria;
	}

	public void setSangria(Sangria sangria) {
		this.sangria = sangria;
	}

	public List<Sangria> getSangrias() {
		return sangrias;
	}

	public void setSangrias(List<Sangria> sangrias) {
		this.sangrias = sangrias;
	}
	
	public Date getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public boolean isPagarAgora() {
		return pagarAgora;
	}

	public void setPagarAgora(boolean pagarAgora) {
		this.pagarAgora = pagarAgora;
	}

	public void persist() {
        String message = "";
        if (conta.getId() != null) {
            conta.merge();
            FacesMessage facesMessage = MessageFactory.getMessage(message, "Contas");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } 
        this.origem = null;
        this.pagarAgora = false;
        init();
    }

	
	public void editarConta(){
		if(conta.getDataPagamento() !=null) {
			this.pagarAgora = true;
		} 
		sangrias = sangria.findSangriaByConta(conta.getId());
		this.origem = sangrias.get(0) .getOrigem();
	}
	
		
}
