package br.com.conjmc.relatorios;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.com.conjmc.cadastrobasico.Faturamento;
import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;
import br.com.conjmc.relatorios.relatoriodiadodes.RelatorioDoMes;

@ManagedBean(name = "relatorioMesBean")
public class RelatorioMes {
	private List<ResumoVO> resumos;
	private List<Sangria> allSangrias;	
	private String dataLabel;
	private SimpleDateFormat sdf;
	private static int mesTemp;
	private Date dataTemp;
	private Faturamento faturamento;
	private Integer ultimoDiaDoMes;
	
	@PostConstruct
    public void init() {
		sdf = new SimpleDateFormat("MM/yyyy");
		faturamento = new Faturamento();
		relatorio();
	}
	
	public String relatorio(){
		Calendar c = Calendar.getInstance();
		setDataTemp(c.getTime());
		dataLabel = sdf.format(getDataTemp()).toString();
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
    	this.resumos = new RelatorioDoMes(getDataTemp()).criarRelatorio();
    	if(mesTemp==0)
    		mesTemp=getDataTemp().getMonth();
    	faturamento.setPeriodo(dataTemp);
    	return null;
	}
	
	public void anterior(){
		Calendar c = Calendar.getInstance();
		getDataTemp().setMonth(mesTemp-1);
		dataLabel = sdf.format(getDataTemp()).toString();
		this.resumos = new RelatorioDoMes(getDataTemp()).criarRelatorio();
		mesTemp=getDataTemp().getMonth();
		c.setTime(getDataTemp());
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		faturamento.setPeriodo(dataTemp);
	}
	
	public void proximo(){
		Calendar c = Calendar.getInstance();
		getDataTemp().setMonth(mesTemp+1);
		dataLabel = sdf.format(getDataTemp()).toString();
		this.resumos = new RelatorioDoMes(getDataTemp()).criarRelatorio();	
		mesTemp=getDataTemp().getMonth();
		c.setTime(getDataTemp());
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);	
		faturamento.setPeriodo(dataTemp);
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
        return "/relatorios/RelatorioMensal.xhtml";
    }	
	
	public List<Sangria> getAllSangrias() {
		return allSangrias;
	}
	public void setAllSangrias(List<Sangria> allSangrias) {
		this.allSangrias = allSangrias;
	}

	public String getDataLabel() {
		return dataLabel;
	}

	public void setDataLabel(String dataLabel) {
		this.dataLabel = dataLabel;
	}

	public List<ResumoVO> getResumos() {
		return resumos;
	}

	public void setResumos(List<ResumoVO> resumos) {
		this.resumos = resumos;
	}

	public Integer getUltimoDiaDoMes() {
		return ultimoDiaDoMes;
	}

	public void setUltimoDiaDoMes(Integer ultimoDiaDoMes) {
		this.ultimoDiaDoMes = ultimoDiaDoMes;
	}

	public Date getDataTemp() {
		return dataTemp;
	}

	public void setDataTemp(Date dataTemp) {
		this.dataTemp = dataTemp;
	}

	public Faturamento getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(Faturamento faturamento) {
		this.faturamento = faturamento;
	}
}
