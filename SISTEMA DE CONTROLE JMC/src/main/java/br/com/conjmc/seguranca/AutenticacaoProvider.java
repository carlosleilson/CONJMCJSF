package br.com.conjmc.seguranca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.conjmc.cadastrobasico.Usuarios;
import br.com.conjmc.dao.impl.UsuariosDaoImpl;
import br.com.conjmc.jsf.util.Security;
import br.com.conjmc.service.impl.UsuariosServiceImpl;


public class AutenticacaoProvider extends UsernamePasswordAuthenticationFilter {
	@Inject
	private UsuariosServiceImpl usuariosService; 
	private List<Usuarios> usuarios;
    private String mensagem;
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, BadCredentialsException {
    	Collection<GrantedAuthority> regras = new ArrayList<GrantedAuthority>();
        String login = request.getParameter("j_username");
        String senha = Security.sha256(request.getParameter("j_password"));
        Long codigoLoja = Long.parseLong(request.getParameter("j_loja"));
        
        if(codigoLoja == 0)
        	throw new BadCredentialsException("Por favor, selecione uma loja.");
 
        Usuarios usuario = login(login, senha);
        
        if(usuario == null)
        	throw new BadCredentialsException("Erro no login");
        
        lojas(regras, codigoLoja, usuario);
        
        try {
			request.getSession().setAttribute("usuarioLogado", usuario);
			mensagem = "Bem vindo: " + usuario.getNome().getNome();
	        return new UsernamePasswordAuthenticationToken(usuario.getNome().getApelido(), usuario.getSenha(), regras);
 
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

	private void lojas(Collection<GrantedAuthority> regras,
			Long codigoLoja, Usuarios usuario) {
		if (usuario.getNome().getLoja().getId().equals(codigoLoja)) {
            regras.add(new SimpleGrantedAuthority(usuario.getPerfil().getLabel().trim()));
        } else {
            mensagem = "Acesso negado a loja selecionada!";
            throw new BadCredentialsException(mensagem);
        }
	}

	private Usuarios login(String login, String senha) {
		usuarios =  usuariosService.findAllUsuarios(); //Usuarios.findAllUsuarioses();
		for (Usuarios user : usuarios) {
			if(login.equals(user.getNome().getApelido().trim())){
				if(senha.equals(user.getSenha().trim())){
					return user;
				} 
			} 
		}
		return null;
	}
 
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        request.getSession().setAttribute("msg", mensagem);
        response.sendRedirect("index.html");
    }
 
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        request.getSession().setAttribute("msg", mensagem);
        response.sendRedirect("login.xhtml?erro=true");
    }
}
