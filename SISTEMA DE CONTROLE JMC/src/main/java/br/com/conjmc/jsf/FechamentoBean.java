package br.com.conjmc.jsf;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.Fechamento;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean
@SessionScoped
public class FechamentoBean {

	private Fechamento fechamento;
	
	@PostConstruct
	public void init() {
		fechamento = new Fechamento();
	}
	
	public String persist() {
		String message = "";
		if(fechamento.getId() != null) {
			fechamento.setFechado(true);
			fechamento.merge();
		} else {
			fechamento.setLoja(ObejctSession.loja());
			fechamento.setUsuarios(ObejctSession.getUsuarioLogado());
			fechamento.setFechado(false);
			fechamento.setData(new Date());
			fechamento.persist();
			return "fechamento.xhtml";
		}
        message = "message_successfully_created";        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Fechamento");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "fechamento.xhtml";
    }
	
	public String abrirCaixa() {
		long qtdCaixasAberto = Fechamento.qtdCaixaAberto();
		if(qtdCaixasAberto > 0) {
			Fechamento recuperaCaixa = Fechamento.caixaAberto();
			if(recuperaCaixa.getUsuarios().getNome().getApelido().equals(ObejctSession.getUsuarioLogado().getNome().getApelido())) {
				this.fechamento = recuperaCaixa;
				return "fechamento.xhtml";
			}
		} else {
			return "aberturaCaixa.xhtml";
		}
		return "main.xhtml";
	}
	
	public void totalContasReceber(){
		
	}
	
	public void totalContasCobrar(){
		
	}

	//Getters and Setters
	public Fechamento getFechamento() {
		return fechamento;
	}

	public void setFechamento(Fechamento fechamento) {
		this.fechamento = fechamento;
	}	
	
}
