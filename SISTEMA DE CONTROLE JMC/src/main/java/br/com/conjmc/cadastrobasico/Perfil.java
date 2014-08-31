package br.com.conjmc.cadastrobasico;

public enum Perfil {
	ADMIN("Administrador"),
    EXCLUSIVO("Exclusivo"),
    SUPERVISOR("Supervisor"),
    GERENTE("Gerente"),
    ATENDENTE("Atendente");
    
    private String label;

	public String getLabel() {
		return label;
	}

	private Perfil (String label) {  
        this.label = label;  
    }
    
    
}
