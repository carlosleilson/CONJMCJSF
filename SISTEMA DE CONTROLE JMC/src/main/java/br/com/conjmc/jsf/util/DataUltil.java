package br.com.conjmc.jsf.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;

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

	/**
	 * Método que return a data atual como string.
	 * @return Retorna data com string. 
	 */		
	public static String dataAtual(){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(c.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH)-1 );
		data = c.getTime();	
		data.setMonth(data.getMonth()+1);
		return sdf.format(data).toString();
	}
	
	public static Date porMes(Date dataTEmp){
		sdf = new SimpleDateFormat("MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(dataTEmp);
		return c.getTime();
	}
	
	/**
	 * Método que retorna a data com mes anterior.
	 * @return Retorna data alterado. 
	 */		
	public static String mesAnterior(){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(c.DAY_OF_MONTH,1 );
		data = c.getTime();
		/*data.setMonth(data.getMonth()-1);*/
		return sdf.format( data).toString();
	}
	
	/**
	 * Método que retorna a data com mes anterior.
	 * @return Retorna data alterado. 
	 */			
	public static Date mesAnterior(Date data){
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(c.DAY_OF_MONTH,1 );
		data.setMonth(data.getMonth()-1);
		data = c.getTime();
		return data;
	}	
	
	
	/**
	 * Método que retorna a data com mes anterior.
	 * @return Retorna data alterado. 
	 */			
	public static Date mesAnteriorDevedor(Date data){
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(c.DAY_OF_MONTH,1 );
		data.setMonth(data.getMonth()-1);
		data = c.getTime();
		return data;
	}	

	/**
	 * Método que retorna a data com primeiro dia do mes.
	 * @return Retorna data alterado. 
	 */			
	public static Date primeiroDiaMesTemp(Date data){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(c.DAY_OF_MONTH,c.getActualMinimum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 0); 
		c.set(Calendar.MINUTE, 0);  
		c.set(Calendar.SECOND, 0);
		data = c.getTime();
		return data;
	}		

	/**
	 * Método que retorna a data com primeiro dia do mes anterior.
	 * @return Retorna data alterado. 
	 */			
	public static Date primeiroDiaMesAnterior(Date data){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(c.MONTH, data.getMonth()-1);
		c.set(c.DAY_OF_MONTH,c.getActualMinimum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 0); 
		c.set(Calendar.MINUTE, 0);  
		c.set(Calendar.SECOND, 0);
		data = c.getTime();
		return data;
	}
	
	/**
	 * Método que retorna a data com ultimo dia do mes anterior.
	 * @return Retorna data alterado. 
	 */	
	public static Date ultimoDiaMesAnterior(Date data){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(c.MONTH, data.getMonth()-1);
		c.set(c.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH) );
		c.set(Calendar.HOUR_OF_DAY, 23); 
		c.set(Calendar.MINUTE, 59);  
		c.set(Calendar.SECOND, 59);
		data = c.getTime();
		return data;
	}	
	
	/**
	 * Método que retorna a data com primeiro dia do mes.
	 * @return Retorna data alterado. 
	 */		
	public static Date primeiroDiaMes(Date data){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(c.DAY_OF_MONTH,-1 );
		data = c.getTime();
		return data;
	}
	
	/**
	 * Método que retorna a data com ultimo dia do mes.
	 * @return Retorna data alterado. 
	 */	
	public static Date ultimoDiaMes(Date data){
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(c.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH) );
		c.set(Calendar.HOUR_OF_DAY, 23); 
		c.set(Calendar.MINUTE, 59);  
		c.set(Calendar.SECOND, 59);
		data = c.getTime();
		return data;
	}		
	
	/**
	 * Método que alterar com um valor do mes corrente.
	 * @return Retorna data do mes alterado. 
	 */	
	public static Date alterarMes(Date dataTmp, int numeroDoMes){
		Calendar c = Calendar.getInstance();
		c.setTime(dataTmp);
		c.set(c.MONTH,dataTmp.getMonth()+numeroDoMes);
		dataTmp = c.getTime();
		return dataTmp;
	}
	
	/**
	 * Método que retorna o quinto dia util.
	 * 
	 * @param Date
	 * @return Date
	 */
	public static Date quintoDiaUtil(Date dataTmp) {

		int workingDays = 0;
		int totalDays = deductDates(dataTmp);

		Calendar c = new GregorianCalendar();

		// Setando o calendar com a Data Inicial
		c.setTime(primeiroDiaMes(dataTmp));

		for (int i = 0; i <= totalDays; i++) {

			if (!(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
					&& !(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
				if (workingDays == 5) {
					c.set(Calendar.HOUR_OF_DAY, 0);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);					
					dataTmp = c.getTime();
					return dataTmp;
				}
				workingDays++;
			}
			c.add(Calendar.DATE, 1);

		}
		dataTmp = c.getTime();
		return dataTmp;
	}

	/**
	 * Método que recebe data inicial e a final e retorna o total de dias domes.
	 * 
	 * @param Date
	 *            initialDate
	 * @param Date
	 *            finalDate
	 * @return int - total de dias
	 */
	private static int deductDates(Date dataTemp) {
		Calendar meuCalendario = new GregorianCalendar();
		meuCalendario.setTime(dataTemp);
		return meuCalendario.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Método que format a data
	 * 
	 * @param Date
	 * @return DateTime - joda
	 */	
	public static DateTime formatDate(Date dataTmp){
		sdf = new SimpleDateFormat("yyyyy-mm-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(dataTmp);
		return setDateTime( c );
	}
	
	/** Método responsavel por retornar um instancia de 
	 * DateTime encapsulando um objeto que implementa Calendar 
	 */  
    private static DateTime setDateTime(Calendar calendar){  
        return new DateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND));  
    } 		
}
