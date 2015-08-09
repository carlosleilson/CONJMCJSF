package br.com.conjmc.cadastrobasico;

import java.util.Date;


public class Alerta {

	private Date data;
	
	private String descricao;
	
	private String modulo;
	
	private Double Valor;

	//Getter and Setter
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public Double getValor() {
		return Valor;
	}

	public void setValor(Double valor) {
		Valor = valor;
	}
	
}
