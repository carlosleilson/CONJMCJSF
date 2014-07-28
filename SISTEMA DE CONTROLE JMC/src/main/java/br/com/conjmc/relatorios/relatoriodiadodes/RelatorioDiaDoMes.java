package br.com.conjmc.relatorios.relatoriodiadodes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.relatorios.Classificacao;
import br.com.conjmc.relatorios.Itens;

public class RelatorioDiaDoMes {
	private List<Classificacao> classificacaoItens;
	private List<Sangria> allSangrias;
	private final int QTD_CAMPOS = 33; 
	private double[] campos;
	private double totalLinha;
	
	public RelatorioDiaDoMes(){
        //criarRelatorio();
		initCampos();
	}
	private void initCampos(){
		campos = new double[QTD_CAMPOS];
		for (int y = 1; y < QTD_CAMPOS; y++) {
			campos[y] = 0.0;
		}
	}
	/**
	 * Método para criar relatorio.
	 */	
	public List<Classificacao> criarRelatorio(){
		classificacaoItens = new ArrayList<Classificacao>();
		for(Despesas classificacao :findAllClassificacao()){
				criarClassificacao(classificacao);
		}
		return classificacaoItens;
	}
	
	/**
	 * Método que carrega do dados do itens.
	 * @param classificacao -- Objeto da classificação
	 */		
	private void criarClassificacao(Despesas classificacao) {
		Classificacao classificacaoIten = new Classificacao();
		classificacaoItens.add(criarItens(classificacaoIten,classificacao));
	}
	
	/**
	 * Método que carrega do dados do itens.
	 * @param classificacaoIten -- Objeto da lista classificação
	 * @param classificacao -- Objeto da classificação
	 */		
	private Classificacao criarItens(Classificacao classificacaoIten, Despesas classificacao) {
		classificacaoIten.setName(classificacao.getCodigo() + " - "	+ classificacao.getDescricao());
		List<Itens> listItens = new ArrayList<Itens>();
		for (DespesasGastos iten : findAllDespasGastosByClassificao(classificacao.getId())) {
			Itens itensRelatorio = new Itens();
			itensRelatorio.setClassificacao(classificacao);
			itensRelatorio.setItem(iten);
			listItens.add(criarDadosDeItem(iten.getId(),itensRelatorio));
		}
		classificacaoIten.setItens(listItens);
		return classificacaoIten;
	}
	
	/**
	 * Método que carrega dado de valor e periodo do itens.
	 * @param idItens -- id do itens
	 * @param itensRelatorio -- Objeto do iten.
	 */
	private Itens criarDadosDeItem(Long idItens, Itens itensRelatorio) {
		List<Sangria> dadosItens = findAllSangriaByItens(idItens);
		campos = new double[QTD_CAMPOS];
		preencherCampos(campos,dadosItens);
		itensRelatorio.setCampos(campos);		
		return itensRelatorio;
	}

	private void preencherCampos(double[] campos, List<Sangria> dadosItens) {
		campos[QTD_CAMPOS-1]=0.0;
		for (Sangria dado : dadosItens) {
			if(dado.getValor()!=null){
				campos[dado.getPeriodo().getDate()] =  dado.getValor();
				campos[QTD_CAMPOS-1] = campos[QTD_CAMPOS-1] + dado.getValor();
			}	
		}
	}

	/**
	 * Método que retorna todas clasificação.
	 * 
	 * @param 
	 *            
	 */		
	public List<Despesas> findAllClassificacao() {
        return  Despesas.findAllDespesases();
    }
	
	/**
	 * Método para encontrar dados dos itens por id da classificação.
	 * 
	 * @param Long id
	 *            -- Id da classificação.
	 */	
	public List<DespesasGastos> findAllDespasGastosByClassificao(Long id) {
		return DespesasGastos.findAllClassificaco(id);
	}
	
	/**
	 * Método para encontrar valores do sangria por id dos itens
	 * 
	 * @param Long id
	 *            -- Id dos itens.
	 */
	public List<Sangria> findAllSangriaByItens(Long id) {
		allSangrias =  Sangria.findSangriaByItens(id);
		return allSangrias;
	}

	public List<Classificacao> getClassificacaoItens() {
		return classificacaoItens;
	}

	public void setClassificacaoItens(List<Classificacao> classificacaoItens) {
		this.classificacaoItens = classificacaoItens;
	}	
}
