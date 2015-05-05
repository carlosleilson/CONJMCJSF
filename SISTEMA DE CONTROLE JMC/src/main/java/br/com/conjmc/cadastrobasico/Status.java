package br.com.conjmc.cadastrobasico;

public enum Status {

    COBRAR ("A Cobrar"),
    RECEBER ("A Receber");
    
    private String label;
    
    public String getLabel() {
		return label;
	}

    private Status (String label) {  
        this.label = label;  
    }
    
}
