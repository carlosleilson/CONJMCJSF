package br.com.conjmc.relatorios.mes;

import java.util.ArrayList;
import java.util.List;

import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.relatorios.Resumo;

public class Mes {
	public List<Resumo> criarRelatorio(){
		List<Resumo> tempRelatorioMes = new ArrayList<Resumo>();
		for (Despesas classificacao: findAllClassificacao()){
			Resumo resumo = new Resumo();
			resumo.setNome(classificacao.getIdResumo());
			tempRelatorioMes.add(resumo);
		}
		return tempRelatorioMes;
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
}
