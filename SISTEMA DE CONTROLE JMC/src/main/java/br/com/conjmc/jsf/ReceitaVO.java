package br.com.conjmc.jsf;

import java.util.Date;

import br.com.conjmc.cadastrobasico.ControleValoresPendentes;
import br.com.conjmc.cadastrobasico.Receita;
import br.com.conjmc.jsf.util.DoubleInversionValue;
import br.com.conjmc.jsf.util.ObejctSession;

public class ReceitaVO {

	private Receita receita;
	
	private void init() {
		receita = new Receita();
		receita.setLoja(ObejctSession.loja());
		receita.setData(new Date());
		receita.setUsuarioLogado(ObejctSession.getUsuarioLogado());
	}
	
	public void somarReceita(ControleValoresPendentes controle) {
		init();
		receita.setValor(controle.getValor());
		receita.setTipoPagamento(controle.getTipoPagamento());
		receita.setControleValores(controle);
		receita.persist();
	}
	
	public void subtrairReceitar(ControleValoresPendentes controle) {
		init();
		receita.setValor(DoubleInversionValue.inverter(controle.getValor()));
		receita.setTipoPagamento(controle.getTipoPagamento());
		receita.setControleValores(controle);
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}
	
	
}
