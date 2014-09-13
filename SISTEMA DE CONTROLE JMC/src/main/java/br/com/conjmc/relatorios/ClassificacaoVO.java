package br.com.conjmc.relatorios;

import java.util.ArrayList;
import java.util.List;

public class ClassificacaoVO {
	private String name;
	private String resumo;
	private String codigo;
	private List<ItensVO> itens;
	
	public ClassificacaoVO() {
		this.itens = new ArrayList<ItensVO>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public List<ItensVO> getItens() {
		return itens;
	}
	public void setItens(List<ItensVO> itens) {
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
