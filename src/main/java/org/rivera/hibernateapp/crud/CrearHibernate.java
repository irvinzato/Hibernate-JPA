package org.rivera.hibernateapp.crud;

import jakarta.persistence.EntityManager;
import org.rivera.hibernateapp.entity.Cliente;
import org.rivera.hibernateapp.util.JpaUtil;

public class CrearHibernate {

  public static void main(String[] args) {

    //Importante estructura del try, catch y finally con los "getTransaction"
    EntityManager em = JpaUtil.getEntityManager();
    try {
      String name = "Nombre de prueba";
      String lastName = "Apellido de prueba";
      String pay = "debito";

      em.getTransaction().begin();

      Cliente c = new Cliente();
      c.setName(name);
      c.setLastName(lastName);
      c.setWayToPay(pay);

      em.persist(c);  //Hace la consulta "INSERT INTO clientes (nombre, apellido, forma_pago) VALUES (?,?,?)"

      em.getTransaction().commit();
    }catch( Exception e ) {
      em.getTransaction().rollback();
      e.printStackTrace();
    }finally {
      em.close();
    }
  }
}
