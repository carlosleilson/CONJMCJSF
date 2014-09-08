package br.com.conjmc.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import br.com.conjmc.cadastrobasico.Cargos;
import br.com.conjmc.dao.ICargosDao;

@Repository("cargosDao")
public class CargosDaoImpl extends DaoImpl<Cargos, Serializable> implements ICargosDao {

	
}
