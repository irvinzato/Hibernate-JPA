package org.rivera.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.rivera.hibernateapp.entity.Cliente;
import org.rivera.hibernateapp.util.JpaUtil;

public class HibernateWhereId {

  public static void main(String[] args) {

    //Solo funciona cuando quiero buscar por "id", el find hace una consulta WHERE id de la tabla "clientes"
    EntityManager em = JpaUtil.getEntityManager();
    Cliente c = em.find(Cliente.class, 2L);   //El parámetro id es tipo long

    System.out.println(c);
    em.close();

  }
}
