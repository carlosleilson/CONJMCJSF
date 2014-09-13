package br.com.conjmc.relatorios;

public class ItensVO {
	 private final int QTD_CAMPOS = 33; 
	 private String[] campos;
	 
	public ItensVO() {
		this.campos = new String[QTD_CAMPOS];
	}
	
	public String[] getCampos() {
		return campos;
	}

	public void setCampos(String[] campos) {
		this.campos = campos;
	}
}
