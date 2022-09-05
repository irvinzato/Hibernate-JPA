package org.rivera.hibernateapp.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

  //EntityManagerFactory ES UNO PARA TODA LA APLICACIÓN !
  private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = buildEntityManagerFactory();

  private static EntityManagerFactory buildEntityManagerFactory() {
    return Persistence.createEntityManagerFactory("ejemploJPA"); //Nombre que le dí en "persistence.xml"
  }

  //EntityManager UNO PARA CADA CLIENTE(request, hilo) !
  public static EntityManager getEntityManager() {
    return ENTITY_MANAGER_FACTORY.createEntityManager();    //Crea un entity por cada hilo
  }
}
