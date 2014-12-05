package br.com.conjmc.jsf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.Faturamento;
import br.com.conjmc.cadastrobasico.ItemFaturamento;
import br.com.conjmc.cadastrobasico.ItemFaturamentoDescricao;
import br.com.conjmc.jsf.util.MessageFactory;

@ManagedBean
@SessionScoped
public class ItemFaturamentoDescricaoBean {

	private Faturamento faturamento;
	private ItemFaturamentoDescricao itemFaturamentoDescricao;
	private List<ItemFaturamentoDescricao> itensFaturamento;

	@PostConstruct
	public void init() {
		faturamento = new Faturamento();
		itemFaturamentoDescricao = new ItemFaturamentoDescricao();
		carregarItens();
	}

	private void carregarItens() {
		new ItemFaturamentoDescricao();
		itensFaturamento = ItemFaturamentoDescricao.findAllItemFaturmento();
		for (ItemFaturamentoDescricao itemFaturamentoDescricaoFor : itensFaturamento) {
			itemFaturamentoDescricaoFor.setItemFaturamento(new ItemFaturamento());
		}
	}

	public String persist() {
		String message = "";
		if (itemFaturamentoDescricao.getId() != null) {
			itemFaturamentoDescricao.merge();
			message = "message_successfully_updated";
		} else {
			itemFaturamentoDescricao.persist();
			message = "message_successfully_created";
		}

		FacesMessage facesMessage = MessageFactory.getMessage(message,
				"Faturamento");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		init();
		return "/itemFaturamentoDescricao.xhtml";
	}
	
	public void persistFatumento(){
		faturamento.setItemFaturamentoDescricao(itensFaturamento);
		faturamento.persist();
	}

	public String delete() {
		itemFaturamentoDescricao.remove();
		FacesMessage facesMessage = MessageFactory.getMessage(
				"message_successfully_deleted", "ItemFaturamentoDescricao");
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		init();
		return "/relatorios/RelatorioMensal.xhtml";
	}

	// Getters and Setters
	public Faturamento getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(Faturamento faturamento) {
		this.faturamento = faturamento;
	}

	public ItemFaturamentoDescricao getItemFaturamentoDescricao() {
		return itemFaturamentoDescricao;
	}

	public void setItemFaturamentoDescricao(
			ItemFaturamentoDescricao itemFaturamentoDescricao) {
		this.itemFaturamentoDescricao = itemFaturamentoDescricao;
	}

	public List<ItemFaturamentoDescricao> getItensFaturamento() {
		return itensFaturamento;
	}

	public void setItensFaturamento(
			List<ItemFaturamentoDescricao> itensFaturamento) {
		this.itensFaturamento = itensFaturamento;
	}

}
