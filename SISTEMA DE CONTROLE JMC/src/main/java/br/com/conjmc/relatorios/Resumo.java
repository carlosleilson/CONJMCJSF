package br.com.conjmc.relatorios;

import java.util.ArrayList;
import java.util.List;

public class Resumo {
	private String name;
	private List<Classificacao> classificacoes;
	public Resumo() {
		this.classificacoes =new ArrayList<Classificacao>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Classificacao> getClassificacoes() {
		return classificacoes;
	}
	public void setClassificacoes(List<Classificacao> classificacoes) {
		this.classificacoes = classificacoes;
	}
}
