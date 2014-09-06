package br.com.conjmc.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.conjmc.cadastrobasico.Usuarios;
import br.com.conjmc.dao.IUsuariosDao;

@Repository("usuariosDao")
public class UsuariosDaoImpl extends DaoImpl<Usuarios, Serializable> implements IUsuariosDao {

	public  final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("nome", "senha", "perfil");

	public  final EntityManager entityManager() {
        EntityManager em = entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public  long countUsuarioses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Usuarios o", Long.class).getSingleResult();
    }

	public  List<Usuarios> findAllUsuarioses() {
        return entityManager().createQuery("SELECT o FROM Usuarios o", Usuarios.class).getResultList();
    }

	public  List<Usuarios> findAllUsuarioses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Usuarios o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Usuarios.class).getResultList();
    }

	public  Usuarios findUsuarios(Long id) {
        if (id == null) return null;
        return entityManager().find(Usuarios.class, id);
    }

	public  List<Usuarios> findUsuariosEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Usuarios o", Usuarios.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public  List<Usuarios> findUsuariosEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Usuarios o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Usuarios.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } 
//        else {
//            Usuarios attached = this.findUsuarios(id);
//            this.entityManager.remove(attached);
//        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public void merge(Usuarios usuario) {
        if (this.entityManager == null) this.entityManager = entityManager();
        Usuarios merged = this.entityManager.merge(usuario);
        this.entityManager.flush();
       // return merged;
    }
}
