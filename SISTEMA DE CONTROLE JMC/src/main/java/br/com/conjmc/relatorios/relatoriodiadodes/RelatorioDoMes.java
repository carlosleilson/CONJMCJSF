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
import br.com.conjmc.cadastrobasico.Resumos;
import br.com.conjmc.controlediario.controlesaida.Sangria;
import br.com.conjmc.jsf.util.DataUltil;
import br.com.conjmc.relatorios.ClassificacaoVO;
import br.com.conjmc.relatorios.ItensVO;
import br.com.conjmc.relatorios.ResumoVO;

/**
 * @author leilson
 *
 */
public class RelatorioDoMes {
	private List<ResumoVO> resumosItens;
	private List<Sangria> allSangrias;
	private List<ItemFaturamento> todosItemFaturamentos;
	private int QTD_CAMPOS = 33; 
	private String[] campoTemp;
	private String[] totalLinha;
	private Date data;
	private NumberFormat df;
	private Double faturamentoBruto;
	private String tempTotalPercente;
	private String taxaDeEntrega;
	private Double tempPercente;
	private Double[] TempResultRESTTotal;
	private String[] tempRESTotalPercente;
	private String[] tempTotal;
	private List <DespesasGastos> todosItens;
	private List <Sangria> todosDadosDespesasPorData;	
	
	
	public RelatorioDoMes(Date dataTemp){
		TempResultRESTTotal = new Double[3];
		tempRESTotalPercente = new String[2];
		tempTotal = new String[2];
		df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		Calendar c = Calendar.getInstance();
		c.setTime(dataTemp);
		data = c.getTime();
		this.QTD_CAMPOS = c.getActualMaximum(Calendar.DAY_OF_MONTH)+3;
		totalLinha = inicializaArray(new String[QTD_CAMPOS]);
		totalLinha[0] = "TOTAL GERAL";
		//todosItemFaturamentos = findAllItemFaturmentos();
		faturamentoBruto = valorFaturamentoTotal();
		todosItens =  findAllDespasGastos();
		todosDadosDespesasPorData = findAllSangriaByItens();		
		inicializarTaxaDeEntrega();
	}
	
	private void inicializarTaxaDeEntrega(){
		if(taxaDeEntrega==null){
			taxaDeEntrega = new String("R$ 0,00");
		}
	}

	private String[] inicializaArray(String[] campos){
		for(int y =1; y < QTD_CAMPOS; y++){
			campos[y] = df.format(0.0);
		}
		return campos;
	}
	
	/**
	 * Método para criar relatorio.
	 */	
	public List<ResumoVO> criarRelatorio(){
		try {
			return linhasDoRelatorio();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Método que cria cada Linha do relatorio, dinamicamente.
	 */	
	private List<ResumoVO> linhasDoRelatorio() throws ParseException{
		campoTemp = inicializaArray(new String[QTD_CAMPOS]);
		resumosItens = new ArrayList<ResumoVO>();
		for(Resumos resumo :Resumos.values()){
			ResumoVO resumoIten = new ResumoVO();
			resumoIten.setName(resumo.getLabel());
			totalResumo(resumoIten);
			resumoIten.setPorcentagem(tempTotalPercente);
			resumoIten.setClassificacoes(criarClassificacao(resumo));
			percenteTotal(resumoIten);
			resumosItens.add(resumoIten);
		}
		return resumosItens;		
	}
	
	private void percenteTotal(ResumoVO resumoIten) throws ParseException {
		if(!resumoIten.getClassificacoes().isEmpty()){
			if (resumoIten.getClassificacoes().get(0).getCodigo().equals("A1")) {
				TempResultRESTTotal[0] = df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue();
				tempPercente = Double.parseDouble(tempTotalPercente.replace("%", "").replace(",", ".").trim());
			}
			if (resumoIten.getClassificacoes().get(0).getCodigo().equals("B1")) {
				TempResultRESTTotal[1] = df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue();
				tempPercente = Double.parseDouble(tempTotalPercente.replace("%", "").replace(",", ".").trim());
			}
			if (resumoIten.getName().equals("RES03")) {
				TempResultRESTTotal[2] = df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue();
				tempPercente = Double.parseDouble(tempTotalPercente.replace("%", "").replace(",", ".").trim());
			}			
		}
	}
	
	private void totalResumo(ResumoVO resumoIten) throws ParseException {
		resumoIten.setValorTemp(totalLinha[QTD_CAMPOS-1]);
		switch (resumoIten.getName()) {
			case "RES01":{
				resumoIten.setTitulo("Receita Total de Vendas,incluindo-se as taxas de Entrega pagas pelos clientes");
				resumoIten.setValorTemp(df.format(faturamentoBruto));
				tempTotalPercente =  "100,00 %";
				break;
			}
			case "RES02":{
				resumoIten.setTitulo("Receita Total sobre as vendas de produtos ( Res 02 = Res 01 - Item A)");
				double totalItem = new ItemFaturamento().faturamentoByDateAndId(data,data, "6") + new ItemFaturamento().faturamentoByDateAndId(data, data, "7");
				String resultRESTTotal = df.format(Math.abs(faturamentoBruto-totalItem));
				tempTotal[1] = resultRESTTotal;
				tempTotalPercente =  String.format("%.2f",(df.parse(resultRESTTotal).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				resumoIten.setValorTemp(resultRESTTotal);
				break;
			}
			
			case "RES03":{
				resumoIten.setTitulo("Res 03 = Res 02 - Item B");
				String resultRESTTotal = df.format(Math.abs(df.parse(tempTotal[1]).doubleValue()-TempResultRESTTotal[1]));
				tempTotal[0] = df.format(Math.abs(df.parse(resultRESTTotal).doubleValue()));
				tempRESTotalPercente[0] = String.format("%.2f",(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				tempTotalPercente =  String.format("%.2f",((df.parse(resultRESTTotal).doubleValue() / tratarFaturamento(faturamentoBruto))*100))+" %";
				resumoIten.setValorTemp(resultRESTTotal);
				break;
			}
			case "RES04":{
				resumoIten.setTitulo("CPV (Res 04) = Soma dos Grupos de Itens ' ' C até ' ' E");
				tempTotal[1] = df.format(Math.abs(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue()));
				tempRESTotalPercente[1] = String.format("%.2f",(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				tempTotalPercente =  String.format("%.2f",(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				break;
			}
			case "RES05":{
				resumoIten.setTitulo("Res 05 = Res 03 - Res 04");
				String resultRESTTotal = df.format(Math.abs(df.parse(tempTotal[0]).doubleValue()-df.parse(tempTotal[1]).doubleValue()));
				resumoIten.setValorTemp(resultRESTTotal);
				tempTotal[0] = resultRESTTotal;
				tempRESTotalPercente[0] = String.format("%.2f",(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				tempTotalPercente =  String.format("%.2f",(df.parse(resultRESTTotal).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				break;
			}
			case "RES06":{
				resumoIten.setTitulo("D.O (Res 06) = Soma dos Grupos de Itens ' ' F ate ' ' O");
				tempTotal[1] = df.format(Math.abs(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue()));
				tempRESTotalPercente[1] = String.format("%.2f",(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				tempTotalPercente =  String.format("%.2f",(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				break;
			}			
			case "RES07":{
				String resultRESTTotal = df.format(Math.abs(df.parse(tempTotal[0]).doubleValue()-df.parse(tempTotal[1]).doubleValue()));
				resumoIten.setTitulo("R.O ((Res 07) = Res 05 - Res 06)");
				resumoIten.setValorTemp(resultRESTTotal);
				tempTotal[0] = resultRESTTotal;
				tempRESTotalPercente[0] = String.format("%.2f",(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				tempTotalPercente =  String.format("%.2f",(df.parse(resultRESTTotal).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				break;
			}
			case "RES08":{
				resumoIten.setTitulo("D.N.O (Res 08) = Soma dos gruposde itens ' ' P ate ' ' R");
				tempTotal[1] = df.format(Math.abs(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue()));
				tempRESTotalPercente[1] = String.format("%.2f",(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				tempTotalPercente =  String.format("%.2f",(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				break;
			}
			case "RES09":{
				String resultRESTTotal = df.format(Math.abs(df.parse(tempTotal[0]).doubleValue()-df.parse(tempTotal[1]).doubleValue()));
				resumoIten.setTitulo("R.L (Res 07 - Res 08)");
				resumoIten.setValorTemp(resultRESTTotal);
				tempTotalPercente =  String.format("%.2f",(df.parse(resultRESTTotal).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				break;
			}				
			default:{
				tempTotalPercente =  String.format("%.2f",(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
				break;
			}
		}
		totalLinha = inicializaArray(new String[QTD_CAMPOS]);
	}
	

	/**
	 * Método que carrega do dados do itens.
	 * @param classificacaoIten -- Objeto da lista classificação
	 * @param classificacao -- Objeto da classificação
	 */
	private List<ClassificacaoVO> criarClassificacao(Resumos idResumo) {
		List<ClassificacaoVO> classificacaoItens = new ArrayList<ClassificacaoVO>();
		for (Despesas dadosDoResumo : findAllDadosDaClassificacao(idResumo)) {
				ClassificacaoVO classificacaoTemp = new ClassificacaoVO();
				classificacaoTemp.setCodigo(dadosDoResumo.getCodigo());
				classificacaoTemp.setName(dadosDoResumo.getDescricao());
				classificacaoTemp.setResumo(dadosDoResumo.getIdResumo());
				classificacaoTemp.setItens(criarTotalDeTodasLinhas());
				classificacaoItens.add(classificacaoTemp);
				somarLinhas(dadosDoResumo);
		}	
		return classificacaoItens;
	}
	
	private List<ItensVO> criarTotalDeTodasLinhas(){
		List<ItensVO> listItens = new ArrayList<ItensVO>();
			listItens.add(criarSomarTotalLinha());
		return listItens;
	}
	
	private ItensVO criarSomarTotalLinha() {
		ItensVO itensRelatorio = new ItensVO();
			itensRelatorio.setCampos(campoTemp);
		return itensRelatorio;
	}		
	
	private List<Despesas>  findAllDadosDaClassificacao(Resumos idResumo) {
		return  Despesas.findAllIdResumo(idResumo);
	}

	/**
	 * Método que retorna todos resumo.
	 */		
	public List<Despesas> findAllResumo() {
        return  Despesas.findAllDespesasesByResumo();
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
	 * @param dataTmp 
	 * 
	 * @param Long id
	 *            -- Id dos itens.
	 */
	public List<ItemFaturamento> findAllItemFaturmentos(Date dataTmp) {
		todosItemFaturamentos = ItemFaturamento.findAllItemFaturmentoByDate(dataTmp);
		return todosItemFaturamentos;
	}	
	
	public List<ResumoVO> getResumosItens() {
		return resumosItens;
	}

	public void setResumosItens(List<ResumoVO> resumosItens) {
		this.resumosItens = resumosItens;
	}
//////////////////////////////////////////////////////////
	/**
	 * Método que carrega do dados do itens.
	 * @param classificacaoIten -- Objeto da lista classificação
	 * @param classificacao -- Objeto da classificação
	 */
	private ClassificacaoVO somarLinhas(Despesas classificacao) {
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
		campoTemp = inicializaArray(new String[QTD_CAMPOS]);
		classificacaoIten.setItens(listItens);
		return classificacaoIten;
	}
	
	/**
	 * Método para incluir o total:
	 */		
	private ItensVO criarTotalLinha() {
		ItensVO itensRelatorio = new ItensVO();
		itensRelatorio.setCampos(campoTemp);
		return itensRelatorio;
	}	
	/**
	 * Método que carrega dado de valor e periodo do itens.
	 * @param item -- id do itens
	 */
	private ItensVO criarDadosDeItem(DespesasGastos item) {
		ItensVO itensRelatorio = new ItensVO();
		String[] campos = new String[QTD_CAMPOS];
		campos[0]=item.getDescrisao();
		try {
			itensRelatorio.setCampos( preencharCampos( inicializaArray(campos),item.getId() ));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return itensRelatorio;
	}
	
	/**
	 * Método que preenche os campos.campoTemp[QTD_CAMPOS-2] = String.format("%.2f",( valor / faturamentoBruto )*100)+" %";
	 * @param campos -- 31 campos para representar o mês.
	 * @param itenId -- itens do sangria.
	 */
	private String[] preencharCampos(String[] campos, Long itenId) throws ParseException {
		Boolean itemI3 = false;
		for(int i = 1; i<campos.length; i++){
			if(itenId.equals(Long.parseLong("1"))){
				for(ItemFaturamento dadosF :todosItemFaturamentos){
					if(dadosF.getValor()!=0 && dadosF.getPeriodo().getDate() == i && dadosF.getPeriodo().getMonth() == data.getMonth() && dadosF.getPeriodo().getYear() == data.getYear() && dadosF.getFaturamentoDescricao().getId().equals(Long.parseLong("7"))){
						campos[i] = df.format(dadosF.getValor());
						campos[QTD_CAMPOS-1] =df.format(df.parse(campos[QTD_CAMPOS-1]).doubleValue() + dadosF.getValor());
						taxaDeEntrega = campos[QTD_CAMPOS-1];
					}
				}				
			}
			if(itenId.equals(Long.parseLong("2"))){
				for(ItemFaturamento dadosF :todosItemFaturamentos){
					if(dadosF.getValor()!=0 && dadosF.getPeriodo().getDate() == i && dadosF.getPeriodo().getMonth() == data.getMonth() && dadosF.getPeriodo().getYear() == data.getYear() && dadosF.getFaturamentoDescricao().getId().equals(Long.parseLong("6"))){
						campos[i] = df.format(dadosF.getValor());
						campos[QTD_CAMPOS-1] =df.format(df.parse(campos[QTD_CAMPOS-1]).doubleValue() + dadosF.getValor());
					}					
				}				
			}
			
			if(!itenId.equals(Long.parseLong("1"))||!itenId.equals(Long.parseLong("2"))){
				for (Sangria dado : todosDadosDespesasPorData) {
					if(dado.getItem().getId().equals(itenId)&&!dado.getItem().getId().equals(Long.parseLong("1"))&&!dado.getItem().getId().equals(Long.parseLong("2"))){
						if( dado.getValor()!=null && dado.getPeriodo().getDate() == i && dado.getPeriodo().getMonth() == data.getMonth() && dado.getPeriodo().getYear() == data.getYear()  ){
							campos[i] = df.format(dado.getValor());
							campos[QTD_CAMPOS-1] =df.format(df.parse(campos[QTD_CAMPOS-1]).doubleValue() + dado.getValor());
							if(dado.getItem().getClassificacao().getId().equals(Long.parseLong("22"))){
								itemI3 = true;
							}
						}
					}
				}		
			}
			totalLinha[i] = df.format(df.parse(totalLinha[i]).doubleValue()+ df.parse(campos[i]).doubleValue());
			if(itemI3){
				totalLinha[i] = df.format(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue() - df.parse(taxaDeEntrega).doubleValue());
			}
			somarTotalPorClassificacao(i,df.parse(campos[i]).doubleValue());
			porcentagem(itemI3,i,df.parse(campoTemp[QTD_CAMPOS-1]).doubleValue());
		}
		if(itemI3){
			campoTemp[QTD_CAMPOS-1] = df.format(df.parse(campoTemp[QTD_CAMPOS-1]).doubleValue() - df.parse(taxaDeEntrega).doubleValue());
			campoTemp[QTD_CAMPOS-2] = String.format("%.2f",( df.parse(campoTemp[QTD_CAMPOS-1]).doubleValue() / tratarFaturamento(faturamentoBruto) )*100)+" %";
		}
		return campos;
	}

	private void porcentagem(Boolean itemI3, int dia, Double valor) {
		//calcular a porcentagem.
		if(dia == QTD_CAMPOS-1){
			campoTemp[QTD_CAMPOS-2] = String.format("%.2f",( valor / tratarFaturamento(faturamentoBruto) )*100)+" %";
		}
	}

	private Double valorFaturamentoTotal() {
		Double valorTemp = 0.0;
		for (ItemFaturamento  faturamentoTotal : findAllItemFaturmentos(data)) {
			valorTemp = valorTemp + faturamentoTotal.getValor();
		}
//		if( valorTemp == null || valorTemp == 0.0)
//			valorTemp = 1.0;
		return valorTemp;
	}	
	
	/**
	 * Método que somar total de cada linha e adcionar na linha total:
	 * @param dia O dai do mes.
	 * @param valor é o valor do dia.
	 */			
	private void somarTotalPorClassificacao(int dia, Double valor) throws ParseException {
		campoTemp[0] ="Totals:";
		 //não escrever na ultima casas
		if (dia != QTD_CAMPOS-2){
			campoTemp[dia] = df.format(df.parse(campoTemp[dia]).doubleValue() + valor);
		}
		if(campoTemp[QTD_CAMPOS-2].contains("R$ 0,00")){
			campoTemp[QTD_CAMPOS-2] = "0,00 %";
		}
	}

	public List<ItemFaturamento> getTodosItemFaturamentos() {
		return todosItemFaturamentos;
	}

	public void setTodosItemFaturamentos(List<ItemFaturamento> todosItemFaturamentos) {
		this.todosItemFaturamentos = todosItemFaturamentos;
	}	
	
	/**
	 * Método que trata todos os valores double.
	 * @param Double faturamentoBrutoTmp
	 * @return double 
	 */		
	private double tratarFaturamento(Double faturamentoBrutoTmp) {
		return (faturamentoBrutoTmp==0.0?1.0:faturamentoBrutoTmp);
	}	
			
}