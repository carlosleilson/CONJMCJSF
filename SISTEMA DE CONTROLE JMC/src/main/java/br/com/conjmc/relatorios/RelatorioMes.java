package br.com.conjmc.relatorios;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.relatorios.relatoriodiadodes.RelatorioDoMes;

@ManagedBean(name = "relatorioMesBean")
public class RelatorioMes {
	private List<Resumo> resumos;
	private List<Sangria> allSangrias;	
	private String dataLabel;
	private SimpleDateFormat sdf;
	private static int mesTemp;
	private Date dataTemp;
	private Integer ultimoDiaDoMes;
	
	@PostConstruct
    public void init() {
		sdf = new SimpleDateFormat("MM/yyyy");
		relatorio();
	}
	
	public String relatorio(){
		Calendar c = Calendar.getInstance();
		dataTemp =c.getTime();
		dataLabel = sdf.format(dataTemp).toString();
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH)-1;
    	this.resumos = new RelatorioDoMes(dataTemp).criarRelatorio();
    	if(mesTemp==0)
    		mesTemp=dataTemp.getMonth();
    	return null;
	}
	
	public void anterior(){
		Calendar c = Calendar.getInstance();
		dataTemp.setMonth(mesTemp-1);
		dataLabel = sdf.format(dataTemp).toString();
		this.resumos = new RelatorioDoMes(dataTemp).criarRelatorio();
		mesTemp=dataTemp.getMonth();
		c.setTime(dataTemp);
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH)-1;
	}
	
	public void proximo(){
		Calendar c = Calendar.getInstance();
		dataTemp.setMonth(mesTemp+1);
		dataLabel = sdf.format(dataTemp).toString();
		this.resumos = new RelatorioDoMes(dataTemp).criarRelatorio();	
		mesTemp=dataTemp.getMonth();
		c.setTime(dataTemp);
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH)-1;		
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

	public List<Resumo> getResumos() {
		return resumos;
	}

	public void setResumos(List<Resumo> resumos) {
		this.resumos = resumos;
	}

	public Integer getUltimoDiaDoMes() {
		return ultimoDiaDoMes;
	}

	public void setUltimoDiaDoMes(Integer ultimoDiaDoMes) {
		this.ultimoDiaDoMes = ultimoDiaDoMes;
	}
}
