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
import br.com.conjmc.cadastrobasico.ItemFaturamento;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.relatorios.ClassificacaoVO;
import br.com.conjmc.relatorios.ItensVO;

/**
 * @author leilson
 *
 */
public class RelatorioDiaDoMes {
	private List<ClassificacaoVO> classificacaoItens;
	private List<Sangria> allSangrias;
	private List<ItemFaturamento> todosItemFaturamentos;	
	private int QTD_CAMPOS = 33; 
	private Double[] campoTemp;
	private static Double[] totalLinha;
	private static Date data;
	private NumberFormat df; 
	private List <DespesasGastos> todosItens;
	private List <Sangria> todosDadosDespesasPorData;
	
	public RelatorioDiaDoMes(Date dataTemp){
		df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		Calendar c = Calendar.getInstance();
		c.setTime(dataTemp);
		data = c.getTime();
		this.QTD_CAMPOS = c.getActualMaximum(Calendar.DAY_OF_MONTH)+2;
		totalLinha = inicializaArray(new Double[QTD_CAMPOS]);
		todosItemFaturamentos = findAllItemFaturmentos();
		todosItens =  findAllDespasGastos();
		todosDadosDespesasPorData = findAllSangriaByItens();
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
	public List<ClassificacaoVO> criarRelatorio(){
		return linhasDoRelatorio();
	}
	
	/**
	 * Método que cria cada Linha do relatorio, dinamicamente.
	 */	
	private List<ClassificacaoVO> linhasDoRelatorio() {
		campoTemp = inicializaArray(new Double[QTD_CAMPOS]);
		classificacaoItens = new ArrayList<ClassificacaoVO>();
		for(Despesas classificacao :findAllClassificacao()){
			classificacaoItens.add(criarLinhas(classificacao));
		}
		classificacaoItens.add(criarTotalDeTodasLinhas());
		return classificacaoItens;		
	}
	
	private ClassificacaoVO criarTotalDeTodasLinhas(){
		ClassificacaoVO classificacaoIten = new ClassificacaoVO();
		List<ItensVO> listItens = new ArrayList<ItensVO>();
			listItens.add(criarSomarTotalLinha());
		classificacaoIten.setItens(listItens);
		return classificacaoIten;
	}
	
	private ItensVO criarSomarTotalLinha() {
		ItensVO itensRelatorio = new ItensVO();
			itensRelatorio.setCampos(arrayStringToArrayLong("TOTAL GERAL",totalLinha));
		return itensRelatorio;
	}		
	
	/**
	 * Método que carrega do dados do itens.
	 * @param classificacaoIten -- Objeto da lista classificação
	 * @param classificacao -- Objeto da classificação
	 */
	private ClassificacaoVO criarLinhas(Despesas classificacao) {
		ClassificacaoVO classificacaoIten = new ClassificacaoVO();
		classificacaoIten.setName(classificacao.getCodigo() + " - "	+ classificacao.getDescricao());
		classificacaoIten.setResumo(classificacao.getIdResumo());
		List<ItensVO> listItens = new ArrayList<ItensVO>();
		for (DespesasGastos item : todosItens) {
			if(item.getClassificacao().getId().equals(classificacao.getId())){
				listItens.add(criarDadosDeItem(item));
			}
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
	private ItensVO criarDadosDeItem(DespesasGastos item) {
		ItensVO itensRelatorio = new ItensVO();
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
		for(int i = 1; i<campos.length; i++){
			if(itenId.equals(Long.parseLong("1"))){
				for(ItemFaturamento dadosF :todosItemFaturamentos){
					if(dadosF.getValor()!=null && dadosF.getPeriodo().getDate() == i && dadosF.getPeriodo().getMonth() == data.getMonth() && dadosF.getPeriodo().getYear() == data.getYear() && dadosF.getFaturamentoDescricao().getId().equals(Long.parseLong("7"))){
						campos[i] = dadosF.getValor();
						campos[campos.length-1] = campos[campos.length-1] + dadosF.getValor();
					}
				}
			}
			if(itenId.equals(Long.parseLong("2"))){
				for(ItemFaturamento dadosF :todosItemFaturamentos){
					if(dadosF.getValor()!=null && dadosF.getPeriodo().getDate() == i && dadosF.getPeriodo().getMonth() == data.getMonth() && dadosF.getPeriodo().getYear() == data.getYear() && dadosF.getFaturamentoDescricao().getId().equals(Long.parseLong("6"))){
						campos[i] =dadosF.getValor();
						campos[campos.length-1] = campos[campos.length-1] + dadosF.getValor();
					}
				}
			}
			if(!itenId.equals(Long.parseLong("1"))||!itenId.equals(Long.parseLong("2"))){
				for (Sangria dado : todosDadosDespesasPorData) {
					if(dado.getItem().getId().equals(itenId)&&!dado.getItem().getId().equals(Long.parseLong("1"))&&!dado.getItem().getId().equals(Long.parseLong("2"))){
						if( dado.getValor()!=null && dado.getPeriodo().getDate() == i && dado.getPeriodo().getMonth() == data.getMonth() && dado.getPeriodo().getYear() == data.getYear() ){
							campos[i] = campos[i] + dado.getValor();
							campos[campos.length-1] = campos[campos.length-1] + dado.getValor();
						}
					}
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
	private ItensVO criarTotalLinha() {
		ItensVO itensRelatorio = new ItensVO();
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
	 * Método para encontrar dados de todos os itens .
	 * 
	 * @param Long id
	 *            -- Id da classificação.
	 */	
	public List<DespesasGastos> findAllDespasGastos() {
		return DespesasGastos.findAllDespesasGastosAtivos();
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
	
	/**
	 * Método para encontrar valores do sangria por data
	 * 
	 * @param Long id
	 *            -- Id dos itens.
	 */
	public List<Sangria> findAllSangriaByItens() {
		allSangrias =  Sangria.paginaPorMes(data);
		return allSangrias;
	}


	/**
	 * Método para encontrar valores dos itens de faturamentos
	 * 
	 * @param Long id
	 *            -- Id dos itens.
	 */
	public List<ItemFaturamento> findAllItemFaturmentos() {
		setTodosItemFaturamentos(ItemFaturamento.findAllItemFaturmentoByDate(data));
		return getTodosItemFaturamentos();
	}		
	
	public List<ClassificacaoVO> getClassificacaoItens() {
		return classificacaoItens;
	}

	public void setClassificacaoItens(List<ClassificacaoVO> classificacaoItens) {
		this.classificacaoItens = classificacaoItens;
	}

	public List<ItemFaturamento> getTodosItemFaturamentos() {
		return todosItemFaturamentos;
	}

	public void setTodosItemFaturamentos(List<ItemFaturamento> todosItemFaturamentos) {
		this.todosItemFaturamentos = todosItemFaturamentos;
	}
}
