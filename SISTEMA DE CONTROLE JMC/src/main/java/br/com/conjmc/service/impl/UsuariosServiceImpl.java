package br.com.conjmc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

import br.com.conjmc.cadastrobasico.Usuarios;
import br.com.conjmc.dao.impl.UsuariosDaoImpl;
import br.com.conjmc.service.IUsuariosService;

@Service("usuariosService")
public class UsuariosServiceImpl extends ServiceImpl<Usuarios> implements
		IUsuariosService {
	@Inject
	private UsuariosDaoImpl usuariosDao;
	
	private List<String> columns;

	@PostConstruct
	public void init() {
		super.setDaoImpl(usuariosDao);
        columns = new ArrayList<String>();
        columns.add("senha");	
        
	}
	
	public List<String> getColumns() {
        return columns;
    }	
	
	public Usuarios findUsuarios(Long id) {
		return usuariosDao.findUsuarios(id);
	}
	
	public long countUsuarios() {
		return usuariosDao.countUsuarioses();
	}
	
	public List<Usuarios> findAllUsuarios() {
	    return usuariosDao.findAllUsuarioses();	
	}
	
	public void alterar(Usuarios usuario){
		usuariosDao.merge(usuario);
	} 
	
	public void gravar() {
		usuariosDao.persist();
	}
	
	 public void excluir(){
		 usuariosDao.remove();
	 }
}
