package br.com.conjmc.jsf;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Configurable;

import br.com.conjmc.cadastrobasico.Faturamento;
import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean(name = "faturamentoBean")
@Configurable
@SessionScoped
public class FaturamentoBean implements Serializable  {

	private static final long serialVersionUID = 1L;
	private Faturamento faturamento;
	private List<Faturamento> faturamentos;
	@PostConstruct
    public void init() {
		faturamento = new Faturamento();
		carregaFaturamentos();
    }
	
	public void carregarPeriodo(String tempData){
		Calendar c = Calendar.getInstance();
		c.set(c.MONTH, Integer.valueOf(tempData.substring(1, 2).trim()));
		c.set(c.YEAR, Integer.valueOf(tempData.substring(3, 7).trim()));
		faturamento.setPeriodo(c.getTime());
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
        faturamento = faturamentoPorData(faturamento.getPeriodo(),faturamento.getFaturamentoBruto());
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
        reset();
        carregaFaturamentos();
        return "relatorios/RelatorioMensal.xhtml";
    }
	
	private Faturamento faturamentoPorData(Date periodo, Double valorBruto) {
		Faturamento faturamentotmp = new Faturamento();
		if(periodo!=null && faturamento.getId() == null){
			faturamentotmp = faturamento.findFaturamentoPorData(periodo);
			if(faturamentotmp!=null){
				faturamentotmp.setFaturamentoBruto(valorBruto);
			}
			if(faturamentotmp==null){
				faturamentotmp = faturamento;
			}
		}else
			faturamentotmp = faturamento;
		return faturamentotmp;
	}

	public String delete() {
		faturamento.remove();
		FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Faturamento");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "relatorios/RelatorioMensal.xhtml";
    }
	
	public void reset() {
		faturamento = new Faturamento();
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }	
	public List<Faturamento> getFaturamentos() {
		return faturamentos;
	}

	public void setFaturamentos(List<Faturamento> faturamentos) {
		this.faturamentos = faturamentos;
	}
	
}