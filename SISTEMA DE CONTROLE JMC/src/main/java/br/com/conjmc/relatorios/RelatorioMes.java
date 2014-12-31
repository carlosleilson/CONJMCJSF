package br.com.conjmc.relatorios;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.conjmc.cadastrobasico.ItemFaturamento;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.DataUltil;
import br.com.conjmc.relatorios.relatoriodiadodes.RelatorioDoMes;

@ManagedBean(name = "relatorioMesBean")
@ViewScoped
public class RelatorioMes {
	private List<ResumoVO> resumos;
	private List<Sangria> allSangrias;	
	private String dataLabel;
	private SimpleDateFormat sdf;
	private int mesTemp;
	private Date dataTemp;
	private Integer ultimoDiaDoMes;
	private double taxaEntrega;
	private double servicoMesa;
	
	@PostConstruct
    public void init() {
		sdf = new SimpleDateFormat("MM/yyyy");
		relatorio();
		itemFaturamento();
	}
	
	private void itemFaturamento() {
		this.taxaEntrega =  new ItemFaturamento().faturamentoByDateAndId(DataUltil.primeiroDiaMes(DataUltil.porMes(dataTemp)), DataUltil.ultimoDiaMes(DataUltil.porMes(dataTemp)), 7);
		this.servicoMesa = new ItemFaturamento().faturamentoByDateAndId(DataUltil.primeiroDiaMes(DataUltil.porMes(dataTemp)), DataUltil.ultimoDiaMes(DataUltil.porMes(dataTemp)), 6);
	}
	
	public String relatorio(){
		Calendar c = Calendar.getInstance();
		setDataTemp(c.getTime());
		dataLabel = sdf.format(getDataTemp()).toString();
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
    	this.resumos = new RelatorioDoMes(getDataTemp()).criarRelatorio();
    	if(mesTemp==0)
    		mesTemp=getDataTemp().getMonth();
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
		itemFaturamento();
	}
	
	public void proximo(){
		Calendar c = Calendar.getInstance();
		getDataTemp().setMonth(mesTemp+1);
		dataLabel = sdf.format(getDataTemp()).toString();
		this.resumos = new RelatorioDoMes(getDataTemp()).criarRelatorio();	
		mesTemp=getDataTemp().getMonth();
		c.setTime(getDataTemp());
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		itemFaturamento();
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

	public double getTaxaEntrega() {
		return taxaEntrega;
	}

	public void setTaxaEntrega(double taxaEntrega) {
		this.taxaEntrega = taxaEntrega;
	}

	public double getServicoMesa() {
		return servicoMesa;
	}

	public void setServicoMesa(double servicoMesa) {
		this.servicoMesa = servicoMesa;
	}
	
}
