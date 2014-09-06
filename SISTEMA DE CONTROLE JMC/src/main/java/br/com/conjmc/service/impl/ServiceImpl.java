package br.com.conjmc.service.impl;

import java.io.Serializable;
import java.util.List;

import br.com.conjmc.dao.IDao;
import br.com.conjmc.service.IService;

@org.springframework.stereotype.Service
public class ServiceImpl<T extends Object> implements IService<T, Serializable> {

  private IDao<T, Serializable> dao;

  @Override
  public List<T> getList() {
    return dao.getList();
  }

  @Override
  public void remove(T bean) {
    dao.delete(bean);
  }

  @Override
  public void persist(T bean) {
    dao.persist(bean);
  }

  @Override
  public void update(T bean) {
    dao.merge(bean);
  }

  @Override
  public T getById(Serializable id) {
    return dao.getById(id);
  }

  @Override
  public void refresh(T entity) {
    dao.refresh(entity);
  }

  public IDao<T, Serializable> getDaoImpl() {
    return dao;
  }

  public void setDaoImpl(IDao<T, Serializable> dao) {
    this.dao = dao;
  }
} 
