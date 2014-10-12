package br.com.conjmc.relatorios;

import java.util.ArrayList;
import java.util.List;

public class ResumoVO {
	private String name;
	private String titulo;
	private String porcentagem;
	private String valorTemp;
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
	public String getPorcentagem() {
		return porcentagem;
	}
	public void setPorcentagem(String porcentagem) {
		this.porcentagem = porcentagem;
	}
	public String getValorTemp() {
		return valorTemp;
	}
	public void setValorTemp(String valorTemp) {
		this.valorTemp = valorTemp;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
