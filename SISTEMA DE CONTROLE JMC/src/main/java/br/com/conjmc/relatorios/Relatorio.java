package br.com.conjmc.relatorios;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.relatorioImpl;
import br.com.conjmc.relatorios.relatoriodiadodes.RelatorioDiaDoMes;

@ManagedBean(name = "relatorioBean")
@ViewScoped
public class Relatorio extends relatorioImpl{
	private List<ClassificacaoVO> classificacaoItens;
	private List<Sangria> allSangrias;	
	private String dataLabel;
	private SimpleDateFormat sdf;
	private int mesTemp;
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
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
    	this.classificacaoItens = new RelatorioDiaDoMes(dataTemp).criarRelatorio();
    	if(mesTemp==0)
    		mesTemp=dataTemp.getMonth();
    	return null;
	}
	
	public void anterior(){
		Calendar c = Calendar.getInstance();
		dataTemp.setMonth(mesTemp-1);
		dataLabel = sdf.format(dataTemp).toString();
		this.classificacaoItens = new RelatorioDiaDoMes(dataTemp).criarRelatorio();
		mesTemp=dataTemp.getMonth();
		c.setTime(dataTemp);
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public void proximo(){
		Calendar c = Calendar.getInstance();
		dataTemp.setMonth(mesTemp+1);
		dataLabel = sdf.format(dataTemp).toString();
		this.classificacaoItens = new RelatorioDiaDoMes(dataTemp).criarRelatorio();	
		mesTemp=dataTemp.getMonth();
		c.setTime(dataTemp);
		ultimoDiaDoMes = c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public void gerarRelatorio() throws MalformedURLException {
		this.gerarRelatorio(this.classificacaoItens, "despesas.jrxml",new HashMap<String, Object>());
	}
	
	public List<ClassificacaoVO> getClassificacaoItens() {
		return classificacaoItens;
	}
	
	public void setClassificacaoItens(List<ClassificacaoVO> classificacaoItens) {
		this.classificacaoItens = classificacaoItens;
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

	public Integer getUltimoDiaDoMes() {
		return ultimoDiaDoMes;
	}

	public void setUltimoDiaDoMes(Integer ultimoDiaDoMes) {
		this.ultimoDiaDoMes = ultimoDiaDoMes;
	}
}
