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
	private String[] campos;
	private String[] totalLinha;
	
	public RelatorioDiaDoMes(){
	}
	
	private String[] inicializaArray(String[] campos){
		campos =  new String[QTD_CAMPOS];
		for(int y =0; y < QTD_CAMPOS; y++){
			campos[y] = "0";
		}
		return campos;
	}
	/**
	 * Método para criar relatorio.
	 */	
	public List<Classificacao> criarRelatorio(){
		classificacaoItens = new ArrayList<Classificacao>();
		for(Despesas classificacao :findAllClassificacao()){
			classificacaoItens.add(criarClassificacao(classificacao));
		}
		return classificacaoItens;
	}
	
	/**
	 * Método que carrega do dados do itens.
	 * @param classificacaoIten -- Objeto da lista classificação
	 * @param classificacao -- Objeto da classificação
	 */		
	private Classificacao criarClassificacao(Despesas classificacao) {
		Classificacao classificacaoIten = new Classificacao();
		classificacaoIten.setName(classificacao.getCodigo() + " - "	+ classificacao.getDescricao());
		List<Itens> listItens = new ArrayList<Itens>();
		for (DespesasGastos item : findAllDespasGastosByClassificao(classificacao.getId())) {
			Itens itensRelatorio = new Itens();
			listItens.add(criarDadosDeItem(item,itensRelatorio));
		}
		listItens.add(criarTotalLinha());
		classificacaoIten.setItens(listItens);
		return classificacaoIten;
	}
	
	private Itens criarTotalLinha() {
		Itens itensRelatorio = new Itens();
		itensRelatorio.setCampos(totalLinha);
		return itensRelatorio;
	}
	/**
	 * Método que carrega dado de valor e periodo do itens.
	 * @param item -- id do itens
	 * @param itensRelatorio -- Objeto do iten.
	 */
	private Itens criarDadosDeItem(DespesasGastos item, Itens itensRelatorio) {
		List<Sangria> dadosItens = findAllSangriaByItens(item.getId());
		String[] total = new String[QTD_CAMPOS];
		total = inicializaArray(total);
		campos = inicializaArray(campos);
		campos[0] = item.getDescrisao();
		preencherCampos(total,campos,dadosItens);
		itensRelatorio.setCampos(campos);
		totalLinha = total;
		return itensRelatorio;
	}

	/**
	 * Método que preenche os campos.
	 * @param campos -- 31 campos para representar o mês.
	 * @param campos2 
	 * @param dadosItens -- itens do sangria.
	 */
	private void preencherCampos( String[] total,String[] campos, List<Sangria> dadosItens) {
		DecimalFormat df = new DecimalFormat("#.##");
		campos[QTD_CAMPOS-1]="0.0";
		for (Sangria dado : dadosItens) {
			if(dado.getValor()!=null){
				campos[dado.getPeriodo().getDate()] =  df.format(dado.getValor()).replace(",", ".");
				campos[QTD_CAMPOS-1] = df.format(Double.valueOf(campos[QTD_CAMPOS-1]) + dado.getValor()).replace(",", ".");
				somarTotalPorClassificacao(total,dado.getPeriodo().getDate(),dado.getValor());
			}	
		}
	}

	private void somarTotalPorClassificacao(String[] total, int campo, Double valor) {
		DecimalFormat df = new DecimalFormat("#.##");
		total[0] ="Totals:";
		total[campo] = df.format(Double.valueOf(total[campo]) + valor).replace(",", ".");
		total[QTD_CAMPOS-1] = df.format(Double.valueOf(total[QTD_CAMPOS-1]) + valor).replace(",", ".");

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
