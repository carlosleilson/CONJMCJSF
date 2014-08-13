package br.com.conjmc.massa.sangria;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.conjmc.cadastrobasico.Despesas;
import br.com.conjmc.cadastrobasico.DespesasGastos;

public class Sangria {
//	 private static List<String[]> dados = Arrays.asList(new String[][]{
//  			//Classificação, itens, dia, valor
//  			{"C1 - produtos cárneos","coxa desossada","7","251,20"},
//  			{"C3 - outros insumos","arroz","2","275,60"},
//  			{"C3 - outros insumos","arroz","2","206,70"},
//  			{"C3 - outros insumos","gelo","1","33"},
//  			{"C3 - outros insumos","óleo de soja","2","404,60"},
//  			{"C3 - outros insumos","óleo de soja","7","346,80"},
//  			{"C3 - outros insumos","óleo de soja","8","346,80"},
//  			{"C5 - hortifruti industrializados","champignom","10","577,50"},
//  			{"C6 - bebidas","todas as bebidas prontas para consumo","4","1.329,47"},
//  			{"F1 - imovel","aluguel","7","546,3"},
//  			{"G2 - luz","conta de luz","9","2449,95"},
//  			{"G3 - telefone","tv a cabo","8","77,38"},
//  			{"G4 - gás","conta de gás","4","539,83"},
//  			{"G4 - gás","conta de gás","10","644,11"},
//  			{"H2 - prestadores de serviço e terceiros","assessoria contábil","7","1.371,61"},
//  			{"H2 - prestadores de serviço e terceiros","certificações (incluindo digital)","1","333,33"},
//  			{"H2 - prestadores de serviço e terceiros","ecad","25","73,99"},
//  			{"H2 - prestadores de serviço e terceiros","eclética","5","344,00"},
//  			{"H2 - prestadores de serviço e terceiros","reunião regional","25","236,40"},
//  			{"I3 - custo dos entregadores","custo dos entregadores","1","3.500,00"},
//  			{"I3 - custo dos entregadores","custo dos entregadores","8","3.500,00"},
//  			{"I3 - custo dos entregadores","custo dos entregadores","15","3.500,00"},
//  			{"I3 - custo dos entregadores","custo dos entregadores","22","3.500,00"},
//  			{"I3 - custo dos entregadores","custo dos entregadores","29","3.500,00"},
//  			{"I6 - beneficios","alimentação","7","94,40"},
//  			{"I6 - beneficios","alimentação","8","272,36"},
//  			{"I6 - beneficios","alimentação","9","30,86"},
//  			{"I6 - beneficios","alimentação","1","10,23"},
//  			{"I6 - beneficios","alimentação","2","8,86"},
//  			{"I6 - beneficios","equipamento de proteção individual","14","35,90"},
//  			{"I6 - beneficios","uniformes","15","630,84"},
//  			{"I6 - beneficios","uniformes","24","630,84"},
//  			{"J1 - materiais de limpeza","água sanitária","7","20,90"},
//  			{"J1 - materiais de limpeza","álcool 70ºc","7","41,90"},
//  			{"J1 - materiais de limpeza","detergente neutro","7","59,80"},
//  			{"J1 - materiais de limpeza","detergente neutro","16","59,80"},
//  			{"J2 - materiais descartaveis","copos descartáveis","7","11,98"},
//  			{"J2 - materiais descartaveis","copos descartáveis","14","17,97"},
//  			{"J2 - materiais descartaveis","papel higiênico","16","14,99"},
//  			{"J2 - materiais descartaveis","papel toalha de banheiro","7","49,80"},
//  			{"J2 - materiais descartaveis","papel toalha de banheiro","16","49,80"},
//  			{"J2 - materiais descartaveis","sacos para lixo","7","45,90"},
//  			{"J2 - materiais descartaveis","sacos para lixo","16","45,90"},
//  			{"J2 - materiais descartaveis","sacos plásticos","7","118,68"},
//  			{"J3 - materiais de escritório","caneta","7","30,90"},
//  			{"J3 - materiais de escritório","talonários","14","74,70"},
//  			{"J4 - materias de cozinha","frigideira","14"," 2,99"},
//  			{"K1 - marketing local","front light","1","1000,00"},
//  			{"K1 - marketing local","out door","24","1.100,00"},
//  			{"N1 - transporte","frete","11","87,57"}
//  	});
	 private static List<String[]> dados = Arrays.asList(new String[][]{
	  			//Classificação, itens, dia, valor
	  			{"produtos cárneos","coxa desossada","7","251,20"},
	  			{"outros insumos","arroz","2","275,60"},
	  			{"outros insumos","arroz","2","206,70"},
	  			{"outros insumos","gelo","1","33"},
	  			{"outros insumos","óleo de soja","2","404,60"},
	  			{"outros insumos","óleo de soja","7","346,80"},
	  			{"outros insumos","óleo de soja","8","346,80"},
	  			{"hortifruti industrializados","champignom","10","577,50"},
	  			{"bebidas","todas as bebidas prontas para consumo","4","1329,47"},
	  			{"imovel","aluguel","7","546,3"},
	  			{"luz","conta de luz","9","2449,95"},
	  			{"telefone","tv a cabo","8","77,38"},
	  			{"gás","conta de gás","4","539,83"},
	  			{"gás","conta de gás","10","644,11"},
	  			{"prestadores de serviço e terceiros","assessoria contábil","7","1371,61"},
	  			{"prestadores de serviço e terceiros","certificações (incluindo digital)","1","333,33"},
	  			{"prestadores de serviço e terceiros","ecad","25","73,99"},
	  			{"prestadores de serviço e terceiros","eclética","5","344,00"},
	  			{"prestadores de serviço e terceiros","reunião regional","25","236,40"},
	  			{"custo dos entregadores","custo dos entregadores","1","3500,00"},
	  			{"custo dos entregadores","custo dos entregadores","8","3500,00"},
	  			{"custo dos entregadores","custo dos entregadores","15","3500,00"},
	  			{"custo dos entregadores","custo dos entregadores","22","3500,00"},
	  			{"custo dos entregadores","custo dos entregadores","29","3500,00"},
	  			{"beneficios","alimentação","7","94,40"},
	  			{"beneficios","alimentação","8","272,36"},
	  			{"beneficios","alimentação","9","30,86"},
	  			{"beneficios","alimentação","1","10,23"},
	  			{"beneficios","alimentação","2","8,86"},
	  			{"beneficios","equipamento de proteção individual","14","35,90"},
	  			{"beneficios","uniformes","15","630,84"},
	  			{"beneficios","uniformes","24","630,84"},
	  			{"materiais de limpeza","água sanitária","7","20,90"},
	  			{"materiais de limpeza","álcool 70ºc","7","41,90"},
	  			{"materiais de limpeza","detergente neutro","7","59,80"},
	  			{"materiais de limpeza","detergente neutro","16","59,80"},
	  			{"materiais descartaveis","copos descartáveis","7","11,98"},
	  			{"materiais descartaveis","copos descartáveis","14","17,97"},
	  			{"materiais descartaveis","papel higiênico","16","14,99"},
	  			{"materiais descartaveis","papel toalha de banheiro","7","49,80"},
	  			{" materiais descartaveis","papel toalha de banheiro","16","49,80"},
	  			{"materiais descartaveis","sacos para lixo","7","45,90"},
	  			{"materiais descartaveis","sacos para lixo","16","45,90"},
	  			{"materiais descartaveis","sacos plásticos","7","118,68"},
	  			{"materiais de escritório","caneta","7","30,90"},
	  			{"materiais de escritório","talonários","14","74,70"},
	  			{"materias de cozinha","frigideira","14"," 2,99"},
	  			{"marketing local","front light","1","1000,00"},
	  			{"marketing local","out door","24","1100,00"},
	  			{"transporte","frete","11","87,57"}
	  	});	 
	public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/META-INF/spring/applicationContext.xml");
        EntityManagerFactory entityManagerFactory = (EntityManagerFactory) applicationContext.getBean("entityManagerFactory");
        EntityManager em = entityManagerFactory.createEntityManager();
        setDados();
	}
	
	/**
	 * Método statico para cadastra dados do sangria.
	 * 
	 * @param 
	 * @param 
	 *            
	 */
	public static void setDados() {
		for (String[] dado : dados) {
			br.com.conjmc.controlediario.controlesaida.Sangria sangria = new br.com.conjmc.controlediario.controlesaida.Sangria();
			sangria.setPeriodo(setdata(Integer.parseInt(dado[2])));
			sangria.setValor(Double.parseDouble(dado[3].replace(",", ".")));
			sangria.setClassificacao(comboClassificacao(dado[0]));
			sangria.setItem(comboItem(dado[1]));
			sangria.setSangria(true);
			sangria.setVersion(1);
			sangria.persist();
		}
		System.out.println("###Fim###");
	}
	
	/**
	 * Método para informar o mes/ano
	 * 
	 * @param dia
	 *            - para o dia para atualizar data.
	 */
	private static Date setdata(final int dia) {
		Calendar data = Calendar.getInstance();
		data.set(Calendar.DAY_OF_MONTH, dia);
		data.set(Calendar.MONTH, 6);
		return data.getTime();
	}

	/**
	 * Método para carregar o id item na combox
	 * 
	 * @param nomeIten
	 *            - nome do item..
	 */
	private static DespesasGastos comboItem(final String nomeIten) {
		DespesasGastos itens = new DespesasGastos();
		for(DespesasGastos iten : itens.findAllDespesasGastoses()){
			if(iten.getDescrisao().equals(nomeIten)){
				return iten;
			}
		}		
		return null;
	}
	
	/**
	 * Método para carregar o id Classificação na combox
	 * 
	 * @param nomeIten
	 *            - nome da classificação..
	 */	
	private static Despesas comboClassificacao(final String nomeClassificacao){
		Despesas despesas = new Despesas();
		for(Despesas classificacao : despesas.findAllDespesases()){
			if(classificacao.getDescricao().equals(nomeClassificacao)){
				return classificacao;
			}
		}
		return null;
	}
	
	/**
	 * Método para modificar string para um tamanho fixo
	 * 
	 * @param string
	 * @param Tamanho
	 *            - string que será modificada
	 *            - tamonho fixo para modificação.
	 */	
    private static String limitarString(String string, int Tamanho){
    	if(string.length()>29)
    		return string.substring(0, Tamanho-1);
    	else
    		return string;
    }	
}
