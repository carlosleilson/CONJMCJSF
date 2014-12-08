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
public class ConsultaFaturamentoBean {

	private ItemFaturamento itemFaturamento;

	@PostConstruct
	public void init() {
		itemFaturamento = new ItemFaturamento();
	}

	
	

}
