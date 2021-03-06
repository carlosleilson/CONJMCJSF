package br.com.conjmc.jsf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Configurable;

import br.com.conjmc.cadastrobasico.Lojas;
import br.com.conjmc.cadastrobasico.Usuarios;
import br.com.conjmc.jsf.util.ObejctSession;

@Configurable
@ManagedBean
@RequestScoped
public class ApplicationBean {
    public String getColumnName(String column) {
        if (column == null || column.length() == 0) {
            return column;
        }
        final Pattern p = Pattern.compile("[A-Z][^A-Z]*");
        final Matcher m = p.matcher(Character.toUpperCase(column.charAt(0)) + column.substring(1));
        final StringBuilder builder = new StringBuilder();
        while (m.find()) {
            builder.append(m.group()).append(" ");
        }
        return builder.toString().trim();
    }

    private Lojas lojaLogado;
    private Usuarios usuarioLogado;	

	@PostConstruct
    public void init() {
    }

	public Boolean ativarModuloAlertar(Boolean ativar){
		return ativar != null? ativar: true ;
	}

	public String getAppName() {
        return "Conjmc";
    }
	
	public Usuarios getUsuarioLogado() {
		return (Usuarios) ObejctSession.getObjectSession("usuarioLogado");
	}
	
	public void setUsuarioLogado(Usuarios usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	} 	
	
	public Lojas getLojaLogado() {
		return (Lojas) ObejctSession.getObjectSession("loja") ;
	}

	public void setLojaLogado(Lojas lojaLogado) {
		this.lojaLogado = lojaLogado;
	}
}