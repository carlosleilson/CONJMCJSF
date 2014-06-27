// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.conjmc.despesa;

import br.com.conjmc.cadastrobasico.Despesa_Gastos;
import br.com.conjmc.despesa.DespesasLoja;
import java.util.Calendar;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect DespesasLoja_Roo_Finder {
    
    public static Long DespesasLoja.countFindDespesasLojasByItem(Despesa_Gastos item) {
        if (item == null) throw new IllegalArgumentException("The item argument is required");
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM DespesasLoja AS o WHERE o.item = :item", Long.class);
        q.setParameter("item", item);
        return ((Long) q.getSingleResult());
    }
    
    public static Long DespesasLoja.countFindDespesasLojasByMes_anoEquals(Calendar mes_ano) {
        if (mes_ano == null) throw new IllegalArgumentException("The mes_ano argument is required");
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM DespesasLoja AS o WHERE o.mes_ano = :mes_ano", Long.class);
        q.setParameter("mes_ano", mes_ano);
        return ((Long) q.getSingleResult());
    }
    
    public static TypedQuery<DespesasLoja> DespesasLoja.findDespesasLojasByItem(Despesa_Gastos item) {
        if (item == null) throw new IllegalArgumentException("The item argument is required");
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery<DespesasLoja> q = em.createQuery("SELECT o FROM DespesasLoja AS o WHERE o.item = :item", DespesasLoja.class);
        q.setParameter("item", item);
        return q;
    }
    
    public static TypedQuery<DespesasLoja> DespesasLoja.findDespesasLojasByItem(Despesa_Gastos item, String sortFieldName, String sortOrder) {
        if (item == null) throw new IllegalArgumentException("The item argument is required");
        EntityManager em = DespesasLoja.entityManager();
        String jpaQuery = "SELECT o FROM DespesasLoja AS o WHERE o.item = :item";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        TypedQuery<DespesasLoja> q = em.createQuery(jpaQuery, DespesasLoja.class);
        q.setParameter("item", item);
        return q;
    }
    
    public static TypedQuery<DespesasLoja> DespesasLoja.findDespesasLojasByMes_anoEquals(Calendar mes_ano) {
        if (mes_ano == null) throw new IllegalArgumentException("The mes_ano argument is required");
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery<DespesasLoja> q = em.createQuery("SELECT o FROM DespesasLoja AS o WHERE o.mes_ano = :mes_ano", DespesasLoja.class);
        q.setParameter("mes_ano", mes_ano);
        return q;
    }
    
    public static TypedQuery<DespesasLoja> DespesasLoja.findDespesasLojasByMes_anoEquals(Calendar mes_ano, String sortFieldName, String sortOrder) {
        if (mes_ano == null) throw new IllegalArgumentException("The mes_ano argument is required");
        EntityManager em = DespesasLoja.entityManager();
        String jpaQuery = "SELECT o FROM DespesasLoja AS o WHERE o.mes_ano = :mes_ano";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        TypedQuery<DespesasLoja> q = em.createQuery(jpaQuery, DespesasLoja.class);
        q.setParameter("mes_ano", mes_ano);
        return q;
    }
    
}
