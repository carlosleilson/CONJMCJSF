package br.com.conjmc.jsf;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class LoginPhseListener  implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RESTORE_VIEW;
	}
	
	@Override
	public void afterPhase(PhaseEvent event) {
		// vari�vel com acesso � arvore de componentes
		FacesContext context = event.getFacesContext();
		
		// verica��o de de login � p�gina de login da aplica��o
		if(context.getViewRoot().getViewId().equals("/index.xhtml") || context.getViewRoot().getViewId().equals("/login.xhtml") ) {
			return;
		}
		
		// carrega o objeto login bean na sess�o   
		Login login = context.getApplication().evaluateExpressionGet(context, "#{login}", Login.class);
	
		// valida��o da autentica��o
		if(!login.isUsuarioLoagado()) {
			// carrega objeto de vagega��o
			NavigationHandler handler = context.getApplication().getNavigationHandler();

			// Redirecona para a tela de login
			handler.handleNavigation(context, null, "login.xhtml");
			context.renderResponse();
		}
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		
	}

}
