package br.com.conjmc.relatorios;

import java.util.ArrayList;
import java.util.List;

public class Classificacao {
	private String name;
	private List<Itens> itens;
	public String getName() {
		return name;
	}
	
	public Classificacao() {
		this.itens = new ArrayList<Itens>();
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
