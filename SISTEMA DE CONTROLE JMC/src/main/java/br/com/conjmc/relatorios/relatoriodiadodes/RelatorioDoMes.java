package br.com.conjmc.relatorios.relatoriodiadodes;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.DespesasGastos;
import br.com.conjmc.cadastrobasico.Faturamento;
import br.com.conjmc.cadastrobasico.Resumos;
import br.com.conjmc.controlediario.controlesaida.Sangria;
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
	private int QTD_CAMPOS = 33; 
	private String[] campoTemp;
	private String[] totalLinha;
	private static Date data;
	private NumberFormat df;
	private Double faturamentoBruto;
	private String tempTotalPercente;
	
	public RelatorioDoMes(Date dataTemp){
		df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		Calendar c = Calendar.getInstance();
		c.setTime(dataTemp);
		data = c.getTime();
		this.QTD_CAMPOS = c.getActualMaximum(Calendar.DAY_OF_MONTH)+3;
		totalLinha = inicializaArray(new String[QTD_CAMPOS]);
		totalLinha[0] = "TOTAL GERAL";
		faturamentoBruto = extracted();
		
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
			resumosItens.add(resumoIten);
		}
		return resumosItens;		
	}

	private void totalResumo(ResumoVO resumoIten) throws ParseException {
		if(resumoIten.getName().equals("RES01")){
			resumoIten.setValorTemp(df.format(faturamentoBruto));
			tempTotalPercente =  "0,00 %";
		}else{
			resumoIten.setValorTemp(totalLinha[QTD_CAMPOS-1]);
			tempTotalPercente =  String.format("%.2f",(df.parse(totalLinha[QTD_CAMPOS-1]).doubleValue() / faturamentoBruto )*100)+" %";
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
		for (DespesasGastos item : findAllDespasGastosByClassificao(classificacao.getId())) {
			listItens.add(criarDadosDeItem(item));
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
		List<Sangria> dadosItens = findAllSangriaByItens(itenId);
		for(int i = 1; i<campos.length; i++){
			for (Sangria dado : dadosItens) {
				if( dado.getValor()!=null && dado.getPeriodo().getDate() == i && dado.getPeriodo().getMonth() == data.getMonth() && dado.getPeriodo().getYear() == data.getYear()  ){
					campos[i] = df.format(dado.getValor());
					campos[QTD_CAMPOS-1] =df.format(df.parse(campos[QTD_CAMPOS-1]).doubleValue() + dado.getValor());
				}
			}		
			totalLinha[i] = df.format(df.parse(totalLinha[i]).doubleValue()+ df.parse(campos[i]).doubleValue());
			somarTotalPorClassificacao(i,df.parse(campos[i]).doubleValue());
			porcentagem(i,df.parse(campoTemp[QTD_CAMPOS-1]).doubleValue());
		}
		return campos;
	}

	private void porcentagem(int dia, Double valor) {
		//calcular a porcentagem.
		if(dia == QTD_CAMPOS-1){
			campoTemp[QTD_CAMPOS-2] = String.format("%.2f",( valor / faturamentoBruto )*100)+" %";
		}
	}

	private Double extracted() {
		Double valor = null;
		List<Faturamento> faturamento = Faturamento.faturamentoesPorDate(data);
		for (Faturamento faturamento2 : faturamento) {
			valor = faturamento2.getFaturamentoBruto();
		}
		if( valor == null )
			valor = 1.0;
		return valor;
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
}