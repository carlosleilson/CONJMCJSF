package br.com.conjmc.relatorios.relatoriodiadodes;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.relatorios.Classificacao;
import br.com.conjmc.relatorios.Itens;

/**
 * @author leilson
 *
 */
public class RelatorioDiaDoMes {
	private List<Classificacao> classificacaoItens;
	private List<Sangria> allSangrias;
	private int QTD_CAMPOS = 33; 
	private String[] campoTemp;
	private static String[] totalLinha;
	private static Date data;
	
	public RelatorioDiaDoMes(){
		Calendar c = Calendar.getInstance();
		//data = c.getTime();
		this.QTD_CAMPOS = c.getActualMaximum(Calendar.DAY_OF_MONTH)+2;
		totalLinha = inicializaArray(new String[QTD_CAMPOS]);
		totalLinha[0] = "TOTAL GERAL";
		
	}

	public void mesAnterior() {
		Calendar c = Calendar.getInstance();
		data =c.getTime();
		data.setMonth(data.getMonth()-1);
	}	
	
	private String[] inicializaArray(String[] campos){
		for(int y =1; y < QTD_CAMPOS; y++){
			campos[y] = "0";
		}
		return campos;
	}
	
	/**
	 * Método para criar relatorio.
	 */	
	public List<Classificacao> criarRelatorio(){
		return linhasDoRelatorio();
	}
	
	/**
	 * Método que cria cada Linha do relatorio, dinamicamente.
	 */	
	private List<Classificacao> linhasDoRelatorio() {
		campoTemp = inicializaArray(new String[QTD_CAMPOS]);
		classificacaoItens = new ArrayList<Classificacao>();
		for(Despesas classificacao :findAllClassificacao()){
			classificacaoItens.add(criarLinhas(classificacao));
		}
		classificacaoItens.add(criarTotalDeTodasLinhas());
		return classificacaoItens;		
	}
	
	private Classificacao criarTotalDeTodasLinhas(){
		Classificacao classificacaoIten = new Classificacao();
		List<Itens> listItens = new ArrayList<Itens>();
			listItens.add(criarSomarTotalLinha());
		classificacaoIten.setItens(listItens);
		return classificacaoIten;
	}
	
	private Itens criarSomarTotalLinha() {
		Itens itensRelatorio = new Itens();
			itensRelatorio.setCampos(totalLinha);
		return itensRelatorio;
	}		
	
	/**
	 * Método que carrega do dados do itens.
	 * @param classificacaoIten -- Objeto da lista classificação
	 * @param classificacao -- Objeto da classificação
	 */
	private Classificacao criarLinhas(Despesas classificacao) {
		Classificacao classificacaoIten = new Classificacao();
		classificacaoIten.setName(classificacao.getCodigo() + " - "	+ classificacao.getDescricao());
		List<Itens> listItens = new ArrayList<Itens>();
		for (DespesasGastos item : findAllDespasGastosByClassificao(classificacao.getId())) {
			listItens.add(criarDadosDeItem(item));
		}		
		listItens.add(criarTotalLinha());
		campoTemp = inicializaArray(new String[QTD_CAMPOS]);
		classificacaoIten.setItens(listItens);
		return classificacaoIten;
	}

	/**
	 * Método que carrega dado de valor e periodo do itens.
	 * @param item -- id do itens
	 */
	private Itens criarDadosDeItem(DespesasGastos item) {
		Itens itensRelatorio = new Itens();
		String[] campos = new String[QTD_CAMPOS];
		campos[0]=item.getDescrisao();
		itensRelatorio.setCampos( preencharCampos( inicializaArray(campos),item.getId() ));
		return itensRelatorio;
	}
	
	/**
	 * Método que preenche os campos.
	 * @param campos -- 31 campos para representar o mês.
	 * @param itenId -- itens do sangria.
	 */
	private String[] preencharCampos(String[] campos, Long itenId) {
		DecimalFormat df = new DecimalFormat("#.##");
		List<Sangria> dadosItens = findAllSangriaByItens(itenId);
		for(int i = 1; i<campos.length; i++){
			for (Sangria dado : dadosItens) {
				if(dado.getPeriodo().getDate() == i && dado.getValor()!=null){
					campos[i] = String.valueOf(dado.getValor());
					campos[QTD_CAMPOS-1] = df.format(Double.valueOf(campos[QTD_CAMPOS-1])+ dado.getValor()).replace(",", ".");
				}
			}			
			totalLinha[i] = df.format(Double.valueOf(totalLinha[i])+ Double.valueOf(campos[i])).replace(",", ".");
			somarTotalPorClassificacao(i,Double.valueOf(campos[i]));
		}
		return campos;
	}
	
	/**
	 * Método para incluir o total:
	 */		
	private Itens criarTotalLinha() {
		Itens itensRelatorio = new Itens();
		itensRelatorio.setCampos(campoTemp);
		return itensRelatorio;
	}	
	
	/**
	 * Método que somar total de cada linha e adcionar na linha total:
	 * @param dia O dai do mes.
	 * @param valor é o valor do dia.
	 */			
	private void somarTotalPorClassificacao(int dia, Double valor) {
		DecimalFormat df = new DecimalFormat("#.##");
		campoTemp[0] ="Totals:";
		campoTemp[dia] = df.format(Double.valueOf(campoTemp[dia]) + valor).replace(",", ".");
	}	
	
	/**
	 * Método que retorna todas clasificação.
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
		allSangrias =  Sangria.paginaPorMes(data, id);
		return allSangrias;
	}

	public List<Classificacao> getClassificacaoItens() {
		return classificacaoItens;
	}

	public void setClassificacaoItens(List<Classificacao> classificacaoItens) {
		this.classificacaoItens = classificacaoItens;
	}
}
