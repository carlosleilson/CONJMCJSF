package br.com.conjmc.cadastrobasico;

public enum TipoPagamento {

    DINHEIRO ("Dinheiro"),
    DEBITO ("Débito"),
    CREDITO ("Crédito"),
    CHEQUE ("Cheque");
    
    private String label;
    
    public String getLabel() {
		return label;
	}

    private TipoPagamento (String label) {  
        this.label = label;  
    }
    
}
