package br.com.conjmc.cadastrobasico;

public enum Resumos {
	
	RES01("RES01"),
	RES02("RES02"),
	RES03("RES03"),
	RES04("RES04"),
	RES05("RES05"),
	RES06("RES06"),
	RES07("RES07"),
	RES08("RES08"),
	RES09("RES09");

    private String label;
    
    public String getLabel() {
		return label;
	}

    private Resumos (String label) {  
        this.label = label;  
     }
}
