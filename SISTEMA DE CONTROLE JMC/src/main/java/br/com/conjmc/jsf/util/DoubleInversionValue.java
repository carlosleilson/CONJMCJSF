package br.com.conjmc.jsf.util;

public class DoubleInversionValue {

	public static Double inverter(Double valor) {
		valor = valor - (valor*2);
		return valor;
	}

}
