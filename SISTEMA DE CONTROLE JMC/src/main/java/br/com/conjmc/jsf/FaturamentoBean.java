package br.com.conjmc.jsf;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.conjmc.cadastrobasico.Faturamento;
import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean(name = "faturamentoBean")
@Configurable
public class FaturamentoBean implements Serializable  {

	private static final long serialVersionUID = 1L;
	private Faturamento faturamento;
	private List<Faturamento> faturamentos;
	@PostConstruct
    public void init() {
		faturamento = new Faturamento();
		carregaFaturamentos();
    }
	
	public void carregarPeriodo(Date tempData){
		faturamento.setPeriodo(tempData);
	}
	
	public Faturamento carregaFaturamento(Date data){
		return Faturamento.findFaturamentoPorData(data);
	}
	
	private void carregaFaturamentos() {
		faturamentos = Faturamento.findAllMFaturamentoes();
	}
	public Faturamento getFaturamento() {
		return faturamento;
	}
	public void setFaturamento(Faturamento faturamento) {
		this.faturamento = faturamento;
	}
	
	public String persist() {
        String message = "";
        if (faturamento.getId() != null) {
        	faturamento.setLoja(new Lojas().findLojas(ObejctSession.idLoja()));
            faturamento.merge();
            message = "message_successfully_updated";
        } else {
        	faturamento.setLoja(new Lojas().findLojas(ObejctSession.idLoja()));
            faturamento.persist();
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogfaturamentoWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Faturamento");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "/pages/faturamento.xhtml";
    }

	public List<Faturamento> getFaturamentos() {
		return faturamentos;
	}

	public void setFaturamentos(List<Faturamento> faturamentos) {
		this.faturamentos = faturamentos;
	}
	
}