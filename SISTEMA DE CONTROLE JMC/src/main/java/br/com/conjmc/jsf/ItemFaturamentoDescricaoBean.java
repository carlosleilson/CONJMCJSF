package br.com.conjmc.jsf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.conjmc.cadastrobasico.ItemFaturamentoDescricao;
import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.jsf.util.MessageFactory;
import br.com.conjmc.jsf.util.ObejctSession;

@ManagedBean
@SessionScoped
public class ItemFaturamentoDescricaoBean {

	private ItemFaturamentoDescricao itemFaturamentoDescricao;
	private List<ItemFaturamentoDescricao> itensFaturamento;
	
	@PostConstruct
	public void init(){
		itemFaturamentoDescricao = new ItemFaturamentoDescricao();
		carregarItens();
	}
	
	private void carregarItens() {
		new ItemFaturamentoDescricao();
		itensFaturamento = ItemFaturamentoDescricao.findAllItemFaturmento();
	}

	public String persist() {
        String message = "";
        if (itemFaturamentoDescricao.getId() != null) {
        	itemFaturamentoDescricao.merge();
            message = "message_successfully_updated";
        } else {
        	itemFaturamentoDescricao.setLoja(new Lojas().findLojas(ObejctSession.idLoja()));
        	itemFaturamentoDescricao.persist();
            message = "message_successfully_created";
        }
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Faturamento");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "/itemFaturamentoDescricao.xhtml";
    }
	
	public String delete() {
		itemFaturamentoDescricao.remove();
		FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "ItemFaturamentoDescricao");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        init();
        return "/relatorios/RelatorioMensal.xhtml";
    }
	
	//Getters and Setters
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

	public void setItensFaturamento(List<ItemFaturamentoDescricao> itensFaturamento) {
		this.itensFaturamento = itensFaturamento;
	}
	
}
