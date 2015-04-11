package br.com.conjmc.cadastrobasico;

public enum Status {

    PENDENTE ("Pendente"),
    RECEBER ("A Receber"),
    BAIXADO ("Baixado");
    /* BAIXADO_DIF ("Baixado DIF"); */
    
    private String label;
    
    public String getLabel() {
		return label;
	}

    private Status (String label) {  
        this.label = label;  
    }
    
}
