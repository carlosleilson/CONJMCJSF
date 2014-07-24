package br.com.conjmc.massa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.conjmc.massa.classificacao.Classificacao;
import br.com.conjmc.massa.itens.Itens;
import br.com.conjmc.massa.sangria.Sangria;

public class GerarMassa {

	public static void main(String[] args) {
		System.out.println("###Inicio###");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/META-INF/spring/applicationContext.xml");
        EntityManagerFactory entityManagerFactory = (EntityManagerFactory) applicationContext.getBean("entityManagerFactory");
        EntityManager em = entityManagerFactory.createEntityManager();
        
        System.out.println("###Iniciar Classificação###");
		Classificacao.setDados();
		System.out.println("###Iniciar Itens###");
		Itens.setDados();
		System.out.println("###Iniciar Sangria###");
		Sangria.setDados();
        System.out.println("###Fim###");
	}

}
