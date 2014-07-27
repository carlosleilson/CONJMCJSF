package br.com.conjmc.relatorios.relatoriodiadodes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.controlediario.controlesaida.Sangria;

public class RelatorioDiaDoMes {
	private List<Sangria> allSangrias;
	private List<String[]> relatorioDiaDoMes;
	private final int QTD_CAMPOS = 33; 
	private String[] campo;
	private double totalLinha;
	private String[] resultadoTotal;
	private String[] resultadoTotalPorItens;
	private Double total = 0.0;	
	
	public RelatorioDiaDoMes(){
        initRealatorio();
        initResultadoTotalProItens();
        initResultadoTotal();		
	}
	/**
	 * Método para inicializar com 0,00 a linhas de cada itens do relatorio
	 */	
	private void initResultadoTotalProItens() {
		resultadoTotalPorItens = new String[QTD_CAMPOS];
		for(int y =1; y < QTD_CAMPOS; y++){
			resultadoTotalPorItens[y] = "0";
		}
		resultadoTotalPorItens[0] ="TOTAL DO ITEM";
	}

	/**
	 * Método para inicializar com 0,00 a linhas do relatorio
	 */	
	private void initRealatorio() {
		campo = new String[QTD_CAMPOS];		
		for(int i =1; i < QTD_CAMPOS; i++){
			campo[i] = "####";
		}
	}

	/**
	 * Método para inicializar com 0,00 a ultima linha do relatorio
	 */
	private void initResultadoTotal() {
		resultadoTotal = new String[QTD_CAMPOS];
		for(int y =1; y < QTD_CAMPOS; y++){
			resultadoTotal[y] = "0";
		}
		resultadoTotal[0] ="TOTAL GERAL:";
	}
	/**
	 * Método que alimenta todos dados do relatorio
	 * 
	 * @param 
	 *            
	 */	
	public List<String[]> allRelatorioDiaDoMes() {
		List<String[]>  linha = new ArrayList<String[]>();
		for(Despesas classificacao :findAllClassificacao()){
			campo = new String[QTD_CAMPOS];
			campo[0]=classificacao.getCodigo()+" - "+classificacao.getDescricao();
			linha.add(campo);
			//Necessario para fazer calculo para cada classificação de itens.
			initResultadoTotalProItens();
			dadosItens(classificacao.getId(),linha);
		}
		linha.add(resultadoTotal);
		setRelatorioDiaDoMes(linha);
		return linha;
	}
	/**
	 * Método que carrega do dados do itens.
	 * @param id -- Id da classificação
	 * @param linha -- Linha do relatorio.
	 */		
	private void dadosItens(Long id, List<String[]> linha) {
		for (DespesasGastos itens : findAllDespasGastosByClassificao(id)) {
			String[] campo = new String[QTD_CAMPOS];
			campo[0] = " - "+itens.getDescrisao();
			linha.add(valoresItens(campo,itens.getId()));
		}
		linha.add(resultadoTotalPorItens);
	}
	
	/**
	 * Método que retorna valores de uma linha.
	 * @param String[] campo  -- valor de cada coluna
	 * @param Long idItens 	  -- id dos itens
	 */	
	private String[] valoresItens(String[] campo, Long idItens) {
		List<Sangria> dadosItens = findAllSangriaByItens(idItens);
		totalLinha = 0;
		for(int i =1; i < QTD_CAMPOS; i++){
			campo[i] = "0";
			if(dadosItens.size() != 0)
				dados(i, campo, idItens,dadosItens);
		}
		campo[QTD_CAMPOS-1]= String.valueOf(totalLinha);
		total = total + totalLinha;
	
		return campo;
	}

	/**
	 * Método que retorna dado de um iten.
	 * @param int i --Valor do dia
	 * @param String[] campo  -- valor de cada coluna
	 * @param Long idItens 	  -- id dos itens
	 * @param List<Sangria> dadosItens --dados da despesas            
	 */		
	private void dados(int i, String[] campo, Long idItens, List<Sangria> dadosItens) {
		for (Sangria dado : dadosItens) {
			if(dado.getPeriodo().getDate() == i && dado.getValor()!=null){
				campo[i] = String.valueOf(dado.getValor());
				totalLinha = totalLinha + dado.getValor();
				linhaTotalFinalPorItem(i,dado.getValor());
				linhaTotalFinal(i,dado.getValor());
			}
		}
	}

	/**
	 * Método que cria a ultima linha de cada item.
	 * @param int i - Numero da posição do campo no relatorio.
	 * @param int i - Valor do campo. 
	 */	
	private String[] linhaTotalFinalPorItem(int i, Double valor) {
		Double tempValor = Double.parseDouble(resultadoTotalPorItens[i]);
		DecimalFormat df = new DecimalFormat("#.##");
		resultadoTotalPorItens[i] = df.format(tempValor + valor).replace(",", ".");
		resultadoTotalPorItens[QTD_CAMPOS-1] = df.format( Double.parseDouble(resultadoTotalPorItens[QTD_CAMPOS-1] ) + Double.parseDouble(resultadoTotalPorItens[i] )).replace(",", ".");
		return resultadoTotalPorItens;
	}
	/**
	 * Método que cria a ultima linha do relatorio.
	 * @param int i - Numero da posição do campo no relatorio.
	 * @param int i - Valor do campo. 
	 */	
	private String[] linhaTotalFinal(int i, Double valor) {
		Double tempValor =  Double.parseDouble(resultadoTotal[i]);
		DecimalFormat df = new DecimalFormat("#.##");
		resultadoTotal[i] = df.format(tempValor + valor).replace(",", ".");
		resultadoTotal[QTD_CAMPOS-1] = df.format( Double.parseDouble(resultadoTotal[QTD_CAMPOS-1] ) + Double.parseDouble(resultadoTotal[i] )).replace(",", ".");
		return resultadoTotal;
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
	public List<String[]> getRelatorioDiaDoMes() {
		return relatorioDiaDoMes;
	}
	public void setRelatorioDiaDoMes(List<String[]> relatorioDiaDoMes) {
		this.relatorioDiaDoMes = relatorioDiaDoMes;
	}	
}
