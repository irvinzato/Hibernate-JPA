package org.rivera.hibernateapp;

import jakarta.persistence.EntityManager;
import org.rivera.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateQL {

  public static void main(String[] args) {

    EntityManager em = JpaUtil.getEntityManager();

    System.out.println("======= Consulta solo el nombre por id =======");
    String nameClient = em.createQuery("SELECT c.nombre FROM Cliente c WHERE c.id=:id", String.class) //Otra forma de pasar parámetros(En lugar de "=?1")
            .setParameter("id", 2L)
            .getSingleResult();
    System.out.println(nameClient);

    System.out.println("======= Consulta por campos personalizados de un registro =======");
    Object[] objectClient = em.createQuery("SELECT c.id, c.nombre, c.apellido FROM Cliente c WHERE c.id=:id", Object[].class)
            .setParameter("id", 2L)
            .getSingleResult();
    Long id = (Long) objectClient[0];
    String name = (String) objectClient[1];
    String lastName = (String) objectClient[2];
    System.out.println("Id encontrado - " + id + " con nombre - " + name + " y apellido - " + lastName);

    System.out.println("======= Consulta por campos personalizados de varios registros =======");
    List<Object[]> listClientsPer = em.createQuery("SELECT c.id, c.nombre, c.apellido FROM Cliente c", Object[].class)
            .getResultList();
    for( Object[] reg: listClientsPer ) {
      id = (Long) reg[0];
      name = (String) reg[1];
      lastName = (String) reg[2];
      System.out.println("Id encontrado - " + id + " con nombre - " + name + " y apellido - " + lastName);
    }

    em.close();

  }
}
