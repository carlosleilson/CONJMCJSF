package br.com.conjmc.jsf.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.conjmc.cadastrobasico.Usuarios;

public class ObejctSession {
	public static <T> Object getObjectSession(String attribute){        
	    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();    
	    HttpSession session = request.getSession(true);    
	    return session.getAttribute(attribute);               
	}
	
	public static Long idLoja(){
		Usuarios usuarioLogado = (Usuarios) ObejctSession.getObjectSession("usuarioLogado");
		return usuarioLogado.getNome().getLoja().getId();
	}
}

