package br.com.conjmc.jsf.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class CalculadoraWindows {
	
	 public void calculadora() {
		 
         try {
             Runtime rt = Runtime.getRuntime();
             Process pr = rt.exec("c:\\Windows\\System32\\calc.exe");
             BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
             /*String line=null;
             while((line=input.readLine()) != null) {
                 System.out.println(line);
             }
             int exitVal = pr.waitFor();
             System.out.println("Exited with error code "+exitVal);
*/
         } catch(Exception e) {
             System.out.println(e.toString());
             e.printStackTrace();
         }
     }

}
