package br.com.conjmc.service;

import java.io.Serializable;
import java.util.List;

public interface IService<T, ID extends Serializable> {
	void remove(T entity);

	void persist(T entity);

	void update(T entity);

	void refresh(T entity);

	List<T> getList();

	T getById(ID id);
}
