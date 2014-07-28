package br.com.conjmc.relatorios;

import java.util.ArrayList;
import java.util.List;

public class Classificacao {
	private String name;
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
}
