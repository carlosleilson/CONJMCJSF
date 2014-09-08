package br.com.conjmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conjmc.cadastrobasico.Cargos;
import br.com.conjmc.dao.impl.CargosDaoImpl;
import br.com.conjmc.service.ICargosService;

@Service("cargosService")
public class CargosServiceImpl extends ServiceImpl<Cargos>
		implements ICargosService {
	
	@Autowired
	private CargosDaoImpl cargosDao;
	
	public Cargos findUsuarios(Long id) {
		return cargosDao.getById(id);
	}
	
	public List<Cargos> findAllCargos() {
	    return cargosDao.getList();	
	}
	
	public void alterar(Cargos cargo){
		cargosDao.delete(cargo);
	} 
	
	public void gravar(Cargos cargo) {
		cargosDao.persist(cargo);
	}
	
	 public void excluir(Cargos cargo){
		 cargosDao.delete(cargo);
	 }
}
