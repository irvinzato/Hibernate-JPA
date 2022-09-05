package org.rivera.hibernateapp;

import jakarta.persistence.EntityManager;
import org.rivera.hibernateapp.entity.Cliente;
import org.rivera.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateListar {

  public static void main(String[] args) {

    //Instancia para poder hacer una consulta, operación con hibernate
    EntityManager em = JpaUtil.getEntityManager();
    //Similar a consulta SQL nativa pero devuelve el objeto "c" de la clase "Cliente", nombre de referencia "c"
    List<Cliente> listClients = em.createQuery("SELECT c FROM Cliente c").getResultList();    //Tiene métodos

    listClients.forEach(cli -> System.out.println(cli));
    em.close();
  }
}
