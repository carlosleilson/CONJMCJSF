package br.com.conjmc.cadastrobasico;

public enum Perfil {
	ADMIN("Administrador"),
    GERENTE("Gerente"),
    CAIXA("Caixa"),
    ESTAGIARIO("Estagiário"),
    CONTABILIDADE("Contabilidade");
    
    private String label;

	public String getLabel() {
		return label;
	}

	private Perfil (String label) {  
        this.label = label;  
    }
    
    
}
