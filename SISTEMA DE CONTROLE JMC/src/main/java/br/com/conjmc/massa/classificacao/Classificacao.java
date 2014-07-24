package br.com.conjmc.massa.classificacao;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.conjmc.cadastrobasico.Despesas;

public class Classificacao {
	private static List<String[]> data =  Arrays.asList(new String[][]{
		  			{"a1","taxa de entrega","RES01"},
		  			{"a2","serviço mesa","RES01"},
		  			{"b1","royalties e fundo de propaganda","RES02"},
		  			{"c1","produtos cárneos","RES03"},
		  			{"c2","hortifruti","RES03"},
		  			{"c3","outros insumos","RES03"},
		  			{"c4","produtos industrializados","RES03"},
		  			{"c5","hortifruti industrializados","RES03"},
		  			{"c6","bebidas","RES03"},
		  			{"d1","embalagens","RES03"},
		  			{"e1","produtos logotipados","RES03"},
		  			{"f1","imóvel","RES05"},
		  			{"g1","água e esgoto","RES05"},
		  			{"g2","luz","RES05"},
		  			{"g3","telefone","RES05"},
		  			{"g4","gás","RES05"},
		  			{"g5","coleta de lixo","RES05"},
		  			{"h1","despesas administrativas (impostos e taxas)","RES05"},
		  			{"h2","prestadores de serviço e terceiros","RES05"},
		  			{"i1","despesa de cozinha","RES05"},
		  			{"i2","despesa atendimento e gerência","RES05"},
		  			{"i3","custo dos entregadores","RES05"},
		  			{"i4","encargos e impostos sem os salários","RES05"},
		  			{"i5","férias e 13º salário","RES05"},
		  			{"i6","benefícios","RES05"},
		  			{"l7","funcionário do setor administrativo","RES05"},
		  			{"j1","materiais de limpeza","RES05"},
		  			{"j2","materiais descartaveis","RES05"},
		  			{"j3","materiais de escritório","RES05"},
		  			{"j4","materiais de cozinha","RES05"},
		  			{"k1","marketing local","RES05"},
		  			{"l1","manutenção","RES05"},
		  			{"m1","despesas financeiras 1","RES05"},
		  			{"n1","transporte","RES05"},
		  			{"o1","homologações","RES05"},
		  			{"p1","despesas financeiras 2","RES07"},
		  			{"q1","investimentos","RES00"},
		  			{"r1","proprietários","res07"}			
		  	});
		  	
		  	
	public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/META-INF/spring/applicationContext.xml");
        EntityManagerFactory entityManagerFactory = (EntityManagerFactory) applicationContext.getBean("entityManagerFactory");
        EntityManager em = entityManagerFactory.createEntityManager();
        setDados();
	}
	
	/**
	 * Método statico para cadastra dados na Classificação.
	 * 
	 * @param 
	 * @param 
	 *            
	 */
	public static void setDados(){
        for (String [] dado :  data) {
			Despesas despesas = new Despesas();
			despesas.setCodigo(dado[0].toUpperCase().trim());
			despesas.setDescricao(dado[1].trim());
			despesas.setIdResumo(dado[2].toUpperCase().trim());
			despesas.setSituacao(true);
			despesas.setVersion(1);
			despesas.persist();
	        //em.persist(despesas);
        }
        System.out.println("###Fim###");
	}
}
