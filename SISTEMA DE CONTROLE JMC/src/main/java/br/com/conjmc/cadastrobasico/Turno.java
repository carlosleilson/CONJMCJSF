package br.com.conjmc.cadastrobasico;

public enum Turno {

	PRIMEIRO("Primeirto"),
	SEGUNDO("Segundo");
	
	private String label;
	
	public String getLabel() {
		return label;
	}

	private Turno(String label) {
		this.label = label;
	}

}
