package br.com.conjmc.jsf;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.ItemFaturamento;
import br.com.conjmc.cadastrobasico.Turno;
import br.com.conjmc.jsf.util.MessageFactory;

@ManagedBean
@SessionScoped
public class ConsultaFaturamentoBean {

	private Date dataInicial;
	private Date dataFinal;
	private Turno turno;
	private ItemFaturamento itemFaturamento;
	private long quantidade;
	private Double total;
	private List<ItemFaturamento> faturamentos;
	private String item;
	
	@PostConstruct
	public void init() {
		itemFaturamento = new ItemFaturamento();
	}
	
	public String carregarTotal() {
		total = new ItemFaturamento().valorTotal(dataInicial, dataFinal, turno, item);
		quantidade = new ItemFaturamento().quantidadeTotal(dataInicial, dataFinal, turno, item);
		faturamentos = new ItemFaturamento().findAllItemFaturmento(dataInicial, dataFinal, turno, item);
		for (int i = 0; i < faturamentos.size(); i++) {
			if(faturamentos.get(i).getValor() < 0){
				faturamentos.get(i).setValor(faturamentos.get(i).getValor() * (-1));
			}
		}
		return "consultaFaturamento.xhtml";
	}
	
	public void salvar(){
		if(itemFaturamento.getId() != null) {
			itemFaturamento.merge();
			carregarTotal();
			FacesMessage facesMessage = MessageFactory.getMessage("Faturamento alterado com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		}
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

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<ItemFaturamento> getFaturamentos() {
		return faturamentos;
	}

	public void setFaturamentos(List<ItemFaturamento> faturamentos) {
		this.faturamentos = faturamentos;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

}
