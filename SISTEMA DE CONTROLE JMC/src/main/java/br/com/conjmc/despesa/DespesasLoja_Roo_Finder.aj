// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.conjmc.despesa;

import br.com.conjmc.despesa.DespesasLoja;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect DespesasLoja_Roo_Finder {
    
    public static Long DespesasLoja.countFindDespesasLojasByMes_anoEquals(Date mes_ano) {
        if (mes_ano == null) throw new IllegalArgumentException("The mes_ano argument is required");
        EntityManager em = DespesasLoja.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM DespesasLoja AS o WHERE o.mes_ano = :mes_ano", Long.class);
        q.setParameter("mes_ano", mes_ano);
        return ((Long) q.getSingleResult());
    }
    
}