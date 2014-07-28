package br.com.conjmc.relatorios;

public class Itens {
	 private final int QTD_CAMPOS = 33; 
	 private String[] campos;
	 
	public Itens() {
		this.campos = new String[QTD_CAMPOS];
	}
	
	public String[] getCampos() {
		return campos;
	}

	public void setCampos(String[] campos) {
		this.campos = campos;
	}
}
