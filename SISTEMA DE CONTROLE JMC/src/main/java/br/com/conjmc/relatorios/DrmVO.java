package br.com.conjmc.relatorios;

public class DrmVO {
	private String name;
	private String titulo;
	private String porcentagem;
	private String valorTemp;
	private String dataLabel;
	private String loja;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
	public String getDataLabel() {
		return dataLabel;
	}
	public void setDataLabel(String dataLabel) {
		this.dataLabel = dataLabel;
	}
	public String getLoja() {
		return loja;
	}
	public void setLoja(String loja) {
		this.loja = loja;
	}
}
