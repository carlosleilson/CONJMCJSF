package br.com.conjmc.jsf.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUltil {
	private static SimpleDateFormat sdf;
	private static Date data;
	
	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	public static void setSdf(SimpleDateFormat sdf) {
		DataUltil.sdf = sdf;
	}

	public static Date getData() {
		return data;
	}

	public static void setData(Date data) {
		DataUltil.data = data;
	}

	public static String dataAtual(){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(c.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH) );
		data = c.getTime();	
		data.setMonth(data.getMonth()+1);
		return sdf.format(data).toString();
	}
	
	public static String mesAnterior(){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(c.DAY_OF_MONTH,1 );
		data = c.getTime();
		/*data.setMonth(data.getMonth()-1);*/
		return sdf.format( data).toString();
	}
	
	public static Date mesAnterior(Date data){
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(c.DAY_OF_MONTH,1 );
		data.setMonth(data.getMonth()-1);
		data = c.getTime();
		return data;
	}	
	
	public static Date primeiroDiaMes(Date data){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(c.DAY_OF_MONTH,-1 );
		data = c.getTime();
		return data;
	}	
	
	public static Date ultimoDiaMes(Date data){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(c.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH) );
		data = c.getTime();
		return data;
	}		
}
