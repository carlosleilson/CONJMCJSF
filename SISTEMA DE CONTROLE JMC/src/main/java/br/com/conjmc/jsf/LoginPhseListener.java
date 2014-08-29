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
		// variável com acesso à arvore de componentes
		FacesContext context = event.getFacesContext();
		
		// vericação de de login à página de login da aplicação
		if(context.getViewRoot().getViewId().equals("/index.xhtml") || context.getViewRoot().getViewId().equals("/login.xhtml") ) {
			return;
		}
		
		// carrega o objeto login bean na sessão   
		Login login = context.getApplication().evaluateExpressionGet(context, "#{login}", Login.class);
	
		// validação da autenticação
		if(!login.isUsuarioLoagado()) {
			// carrega objeto de vagegação
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
