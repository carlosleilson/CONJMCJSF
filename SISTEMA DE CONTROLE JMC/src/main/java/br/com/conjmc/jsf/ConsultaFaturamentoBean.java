package br.com.conjmc.jsf;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.conjmc.cadastrobasico.ItemFaturamento;
import br.com.conjmc.cadastrobasico.ItemFaturamentoDescricao;

@ManagedBean
@SessionScoped
public class ConsultaFaturamentoBean {

	private Date dataInicial;
	private Date dataFinal;
	private ItemFaturamento itemFaturamento;
	private long quantidade;
	private long numeroComandas;
	private Double total;
	
	@PostConstruct
	public void init() {
		itemFaturamento = new ItemFaturamento();
	}
	
	public String carregarTotal() {
		total = new ItemFaturamentoDescricao().valorTotal(dataInicial, dataFinal, null);
		quantidade = new ItemFaturamentoDescricao().quantidadeTotal(dataInicial, dataFinal, null);
		numeroComandas = new ItemFaturamentoDescricao().numeroComandasTotal(dataInicial, dataFinal, null);
		return "consultaFaturamento.xhtml";
	}

	//Getters and Setters
	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public ItemFaturamento getItemFaturamento() {
		return itemFaturamento;
	}

	public void setItemFaturamento(ItemFaturamento itemFaturamento) {
		this.itemFaturamento = itemFaturamento;
	}

	public long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public long getNumeroComandas() {
		return numeroComandas;
	}

	public void setNumeroComandas(Integer numeroComandas) {
		this.numeroComandas = numeroComandas;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
