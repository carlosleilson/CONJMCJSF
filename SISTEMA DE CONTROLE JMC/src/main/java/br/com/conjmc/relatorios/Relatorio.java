package br.com.conjmc.relatorios;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.relatorios.relatoriodiadodes.RelatorioDiaDoMes;

@ManagedBean(name = "relatorioBean")
public class Relatorio {
	private List<Classificacao> classificacaoItens;
	private List<Sangria> allSangrias;	
	
	@PostConstruct
    public void init() {
    	RelatorioDiaDoMes relatorioDiaDoMes = new RelatorioDiaDoMes();
    	this.classificacaoItens = relatorioDiaDoMes.criarRelatorio();		
	}
	
	public void anterior(){
		
	}
	
	public void proximo(){
		
	}
	
	public List<Classificacao> getClassificacaoItens() {
		return classificacaoItens;
	}
	public void setClassificacaoItens(List<Classificacao> classificacaoItens) {
		this.classificacaoItens = classificacaoItens;
	}
	public List<Sangria> getAllSangrias() {
		return allSangrias;
	}
	public void setAllSangrias(List<Sangria> allSangrias) {
		this.allSangrias = allSangrias;
	}
}
