package br.com.conjmc.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.dao.IDao;


@Repository
public abstract class DaoImpl<T, ID extends Serializable> implements IDao<T, ID> {
  private Class<T> persistentClass;
  
  @PersistenceContext(name = "connectionConjmc")
  protected EntityManager entityManager;
  
  Root<T> root;
  @SuppressWarnings("rawtypes")
  TypedQuery q;

  @SuppressWarnings("unchecked")
  public DaoImpl() {
    this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
    ;
  }

  public Class<T> getPersistentClass() {
    return persistentClass;
  }

  @Transactional(readOnly = true)
  public T getById(ID id) {
    return entityManager.find(persistentClass, id);
  }

  @Transactional
  public void persist(T entity) {
    entityManager.persist(entity);
  }

  @Transactional
  public void merge(T entity) {
    entityManager.merge(entity);
  }

  @Transactional
  public void delete(T entity) {
    Object o = entityManager.merge(entity);
    entityManager.remove(o);
  }

  @Transactional
  public void refresh(T entity) {
    entityManager.refresh(entity);
  }

  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<T> getList() {
    return entityManager.createQuery(
        "Select t from " + persistentClass.getSimpleName() + " t")
        .getResultList();
  }
}

