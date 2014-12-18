package br.com.conjmc.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.ItemFaturamento;
import br.com.conjmc.cadastrobasico.ItemFaturamentoDescricao;
import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.cadastrobasico.Turno;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean
@SessionScoped
public class ItemFaturamentoDescricaoBean {

	private Date data;
	private Turno turno;
	private ItemFaturamentoDescricao itemFaturamentoDescricao;
	private List<ItemFaturamentoDescricao> itensFaturamento;
	private List<ItemFaturamentoDescricao> itensFaturamentoAtivos;
	private List<ItemFaturamento> itemFaturamentoNovo;

	@PostConstruct
	public void init() {
		itemFaturamentoDescricao = new ItemFaturamentoDescricao();
		itemFaturamentoDescricao.setAtivo(true);
		itemFaturamentoNovo = new ArrayList<ItemFaturamento>();
		carregarItens();
	}

	private void carregarItens() {
		new ItemFaturamentoDescricao();
		itensFaturamento = ItemFaturamentoDescricao.findAllItemFaturmento();
		itensFaturamentoAtivos = ItemFaturamentoDescricao.findAllItemFaturmentoAtivo();
		List<ItemFaturamento> item = new ArrayList<ItemFaturamento>();
		for (ItemFaturamentoDescricao itemFaturamentoDescricaoFor : itensFaturamentoAtivos) {
			ItemFaturamento itemFaturamentoFor = new ItemFaturamento();
			itemFaturamentoFor.setFaturamentoDescricao(itemFaturamentoDescricaoFor);
			item.add(itemFaturamentoFor);  
			/*itemFaturamentoDescricaoFor.setItemFaturamento(new ItemFaturamento());*/
		}
		setItemFaturamentoNovo(item);
		
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
		return "itemFaturamentoDecricao.xhtml";
	}
	
	public String persistFatumento(){
		Lojas loja = ObejctSession.loja();
		for (ItemFaturamentoDescricao itemFaturamentoDescricaoFor : itensFaturamentoAtivos) {
			/*ItemFaturamento it = itemFaturamentoDescricaoFor.getItemFaturamento();
			it.setLoja(ObejctSession.loja());
			it.setPeriodo(data);
			it.setTurno(turno);
			it.persist();


			itemFaturamentoDescricaoFor.setItemFaturamento(it);
			itemFaturamentoDescricaoFor.merge();*/
		}
		init();
		return "/faturameto.xhtml";
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

	public List<ItemFaturamentoDescricao> getItensFaturamentoAtivos() {
		return itensFaturamentoAtivos;
	}

	public void setItensFaturamentoAtivos(
			List<ItemFaturamentoDescricao> itensFaturamentoAtivos) {
		this.itensFaturamentoAtivos = itensFaturamentoAtivos;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public List<ItemFaturamento> getItemFaturamentoNovo() {
		return itemFaturamentoNovo;
	}

	public void setItemFaturamentoNovo(List<ItemFaturamento> itemFaturamentoNovo) {
		this.itemFaturamentoNovo = itemFaturamentoNovo;
	}

}
