package br.com.conjmc.relatorios.relatoriodiadodes;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
	private Double[] campoTemp;
	private static Double[] totalLinha;
	private static Date data;
	private NumberFormat df; 
	
	public RelatorioDiaDoMes(Date dataTemp){
		df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		Calendar c = Calendar.getInstance();
		c.setTime(dataTemp);
		data = c.getTime();
		this.QTD_CAMPOS = c.getActualMaximum(Calendar.DAY_OF_MONTH)+2;
		totalLinha = inicializaArray(new Double[QTD_CAMPOS]);
	}

	private Double[] inicializaArray(Double[] campos){
		for(int y =1; y < QTD_CAMPOS; y++){
			campos[y] = 0.0;
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
		campoTemp = inicializaArray(new Double[QTD_CAMPOS]);
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
			itensRelatorio.setCampos(arrayStringToArrayLong("TOTAL GERAL",totalLinha));
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
		classificacaoIten.setResumo(classificacao.getIdResumo());
		List<Itens> listItens = new ArrayList<Itens>();
		for (DespesasGastos item : findAllDespasGastosByClassificao(classificacao.getId())) {
			listItens.add(criarDadosDeItem(item));
		}		
		listItens.add(criarTotalLinha());
		campoTemp = inicializaArray(new Double[QTD_CAMPOS]);
		classificacaoIten.setItens(listItens);
		return classificacaoIten;
	}

	/**
	 * Método que carrega dado de valor e periodo do itens.
	 * @param item -- id do itens
	 */
	private Itens criarDadosDeItem(DespesasGastos item) {
		Itens itensRelatorio = new Itens();
		Double[] campos = new Double[QTD_CAMPOS];
		try {
			itensRelatorio.setCampos( arrayStringToArrayLong(item.getDescrisao(),preencharCampos( inicializaArray(campos),item.getId() )));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return itensRelatorio;
	}
	
	/**
	 * Método que preenche os campos.
	 * @param campos -- 31 campos para representar o mês.
	 * @param itenId -- itens do sangria.
	 * @throws ParseException 
	 */
	private Double[] preencharCampos(Double[] campos, Long itenId) throws ParseException {
		List<Sangria> dadosItens = findAllSangriaByItens(itenId);
		for(int i = 1; i<campos.length; i++){
			for (Sangria dado : dadosItens) {
				if(dado.getPeriodo().getDate() == i && dado.getValor()!=null){
					campos[i] = campos[i] + dado.getValor();
					campos[QTD_CAMPOS-1] = campos[QTD_CAMPOS-1] + dado.getValor();
				}
			}			
			totalLinha[i] = totalLinha[i] + campos[i];
			somarTotalPorClassificacao(i,campos[i]);
		}
		return campos;
	}
	
	/**
	 * Método para incluir o total:
	 */		
	private Itens criarTotalLinha() {
		Itens itensRelatorio = new Itens();
		itensRelatorio.setCampos(arrayStringToArrayLong("Totals:",campoTemp));
		return itensRelatorio;
	}	
	
	/**
	 * Método que somar total de cada linha e adcionar na linha total:
	 * @param dia O dai do mes.
	 * @param valor é o valor do dia.
	 * @throws ParseException 
	 */			
	private void somarTotalPorClassificacao(int dia, Double valor) throws ParseException {
		campoTemp[dia] = campoTemp[dia] + valor;
	}	
	
	/**
	 * Método que converte array de Double para Sring.
	 * 
	 * @param String primeiroCampoTemp
	 * @param Long[] tempArray
	 */	
	private String[] arrayStringToArrayLong(String primeiroCampoTemp,Double[] tempArray ){
		String[] camposTemp = new String[QTD_CAMPOS];
		camposTemp[0] = primeiroCampoTemp;
		for(int i = 1; i< tempArray.length; i++){
			if(tempArray[i]!=0.0)
				camposTemp[i] = df.format(tempArray[i]);
		}
		return camposTemp;
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
	 * Método para encontrar valores do sangria por id dos itenzs
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
