package org.rivera.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.rivera.hibernateapp.entity.Cliente;
import org.rivera.hibernateapp.util.JpaUtil;

public class HibernateWhere {

  public static void main(String[] args) {
    EntityManager em = JpaUtil.getEntityManager();
    Query query = em.createQuery("SELECT c FROM Cliente c WHERE forma_pago=?1", Cliente.class);
    query.setParameter(1, "paypal");
    Cliente c = (Cliente) query.getSingleResult();        //"getSingleResult" - Cuando hay solo un resultado, "getSingleResult" - Cuando hay muchos
    System.out.println(c);
    em.close();

  }
}
