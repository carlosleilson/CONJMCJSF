package br.com.conjmc.relatorios;

import java.util.ArrayList;
import java.util.List;

public class Classificacao {
	private String name;
	private String resumo;
	private String codigo;
	private List<Itens> itens;
	
	public Classificacao() {
		this.itens = new ArrayList<Itens>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public List<Itens> getItens() {
		return itens;
	}
	public void setItens(List<Itens> itens) {
		this.itens = itens;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
