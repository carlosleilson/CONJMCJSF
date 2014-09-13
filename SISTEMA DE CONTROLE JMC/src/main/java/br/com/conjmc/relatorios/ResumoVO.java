package br.com.conjmc.relatorios;

import java.util.ArrayList;
import java.util.List;

public class ResumoVO {
	private String name;
	private List<ClassificacaoVO> classificacoes;
	public ResumoVO() {
		this.classificacoes =new ArrayList<ClassificacaoVO>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ClassificacaoVO> getClassificacoes() {
		return classificacoes;
	}
	public void setClassificacoes(List<ClassificacaoVO> classificacoes) {
		this.classificacoes = classificacoes;
	}
}
