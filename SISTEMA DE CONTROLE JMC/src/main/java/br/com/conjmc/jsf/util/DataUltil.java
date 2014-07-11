package br.com.conjmc.jsf.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUltil {
	private static SimpleDateFormat sdf;
	private static Date data;
	
	public static String dataAtual(){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		data =  new Date();
		return sdf.format(data).toString();
	}
	
	public static String mesAnterior(){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		data = new Date();
		data.setMonth(data.getMonth()-1);
		return sdf.format( data).toString();
	}	
}
