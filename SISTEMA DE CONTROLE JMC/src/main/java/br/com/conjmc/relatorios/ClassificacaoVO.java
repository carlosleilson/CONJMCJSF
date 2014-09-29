package br.com.conjmc.relatorios;

import java.util.ArrayList;
import java.util.List;

import br.com.conjmc.cadastrobasico.Resumos;

public class ClassificacaoVO {
	private String name;
	private Resumos resumo;
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

	public Resumos getResumo() {
		return resumo;
	}

	public void setResumo(Resumos resumo) {
		this.resumo = resumo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
