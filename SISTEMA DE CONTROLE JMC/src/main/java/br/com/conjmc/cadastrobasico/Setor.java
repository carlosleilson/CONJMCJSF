package br.com.conjmc.cadastrobasico;

public enum Setor {

    COZINHA ("Cozinha"),
    ATENDIMENTO ("Atendimento"),
    CENTRALDECORTE ("Central de Corte"),
    CALLCENTER ("Call Center");
    
    private String label;
    
    public String getLabel() {
		return label;
	}

    private Setor (String label) {  
        this.label = label;  
     }
    
}
