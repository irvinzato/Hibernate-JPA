package org.rivera.hibernateapp.crud;

import jakarta.persistence.EntityManager;
import org.rivera.hibernateapp.entity.Cliente;
import org.rivera.hibernateapp.util.JpaUtil;

//Este paquete solo es para mostrar estructura del crud
public class EliminarHibernate {

  public static void main(String[] args) {

    EntityManager em = JpaUtil.getEntityManager();
    try {
      Long id = 3L;
      Cliente c = em.find(Cliente.class, id);  //Crea el objeto por id

      em.getTransaction().begin();

      em.remove(c);   //consulta "DELETE FROM clientes WHERE id=?"

      em.getTransaction().commit();
    }catch( Exception e ){
      em.getTransaction().rollback();
      e.printStackTrace();
    }finally {
      em.close();
    }
  }
}
