package org.rivera.hibernateapp.crud;

import jakarta.persistence.EntityManager;
import org.rivera.hibernateapp.entity.Cliente;
import org.rivera.hibernateapp.util.JpaUtil;

//Este paquete solo es para mostrar estructura del crud
public class EditarHibernate {

  public static void main(String[] args) {

    EntityManager em = JpaUtil.getEntityManager();
    try {
      Long id = 2L;   //Importante tenerlo para actualizar
      Cliente c = em.find(Cliente.class, id);     //Lo obtengo de la DB y crea el objeto

      String name = "Nombre modificado";
      String lastName = "Apellido modificado";
      String pay = "Nueva forma de pago";

      em.getTransaction().begin();

      c.setName(name);
      c.setLastName(lastName);
      c.setWayToPay(pay);

      em.merge(c);    //Hace consulta "UPDATE clientes SET name=?, lastName=?, forma_pago=? WHERE id=?"

      em.getTransaction().commit();

    }catch( Exception e ) {
      em.getTransaction().rollback();
      e.printStackTrace();
    }finally {
      em.close();
    }
  }
}
