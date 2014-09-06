package br.com.conjmc.dao;

import java.io.Serializable;
import java.util.List;

public interface IDao<T, ID extends Serializable> {
	T getById(ID id);

	void persist(T entity);

	void merge(T entity);

	void delete(T entity);

	void refresh(T entity);

	List<T> getList();
}
