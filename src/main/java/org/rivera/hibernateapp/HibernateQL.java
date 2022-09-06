package org.rivera.hibernateapp;

import jakarta.persistence.EntityManager;
import org.rivera.hibernateapp.dto.ClienteDto;
import org.rivera.hibernateapp.entity.Cliente;
import org.rivera.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateQL {

  public static void main(String[] args) {

    EntityManager em = JpaUtil.getEntityManager();

    //Es importante utilizar el valor de los campos del objeto entity para hacer consultas SQL
    System.out.println("======= Consulta para lista de Clientes =======");
    List<Cliente> clientsList = em.createQuery("SELECT c FROM Cliente c", Cliente.class)
            .getResultList();
    clientsList.forEach(System.out::println);

    System.out.println("======= Consulta solo el nombre por id =======");
    String nameClient = em.createQuery("SELECT c.name FROM Cliente c WHERE c.id=:id", String.class) //Otra forma de pasar parámetros(En lugar de "=?1")
            .setParameter("id", 2L)
            .getSingleResult();
    System.out.println(nameClient);

    System.out.println("======= Consulta por campos personalizados de un registro =======");
    System.out.println("======= Importante el tipo 'Object' cuando quiero devolver solo ciertos parámetros =======");
    Object[] objectClient = em.createQuery("SELECT c.id, c.name, c.lastName FROM Cliente c WHERE c.id=:id", Object[].class)
            .setParameter("id", 2L)
            .getSingleResult();
    Long id = (Long) objectClient[0];
    String name = (String) objectClient[1];
    String lastName = (String) objectClient[2];
    System.out.println("Id encontrado - " + id + " con nombre - " + name + " y apellido - " + lastName);

    System.out.println("======= Consulta por campos personalizados de varios registros =======");
    List<Object[]> listClientsPer = em.createQuery("SELECT c.id, c.name, c.lastName FROM Cliente c", Object[].class)
            .getResultList();
    for( Object[] reg: listClientsPer ) {
      id = (Long) reg[0];
      name = (String) reg[1];
      lastName = (String) reg[2];
      System.out.println("Id encontrado - " + id + " con nombre - " + name + " y apellido - " + lastName);
    }

    System.out.println("======= Consulta por Cliente y forma pago =======");
    List<Object[]> listObjPay = em.createQuery("SELECT c, c.wayToPay FROM Cliente c", Object[].class)
                    .getResultList();
    listObjPay.forEach( reg -> {
      Cliente c = (Cliente) reg[0];
      String pay = (String) reg[1];
      System.out.println("Cliente obj - " + c + " Forma de pago extra " + pay );
    });

    System.out.println("======= Consulta que puebla y devuelve objeto entity de una clase personalizada =======");
    List<Cliente> clientsPerList = em.createQuery("SELECT new Cliente(c.name, c.lastName) FROM Cliente c", Cliente.class)
                    .getResultList();
    clientsPerList.forEach(System.out::println);

    System.out.println("======= Consulta que puebla y devuelve objeto DTO de una clase personalizada =======");
    System.out.println("======= Importante incluir el package en consulta después del new =======");
    List<ClienteDto> clientsListDto = em.createQuery("SELECT new org.rivera.hibernateapp.dto.ClienteDto(c.name, c.lastName) FROM Cliente c", ClienteDto.class)
                    .getResultList();
    clientsListDto.forEach(System.out::println);










    System.out.println("======= Pruebas =======");
    String prueba = em.createQuery("SELECT c.lastName FROM Cliente c WHERE c.name=:name", String.class)
            .setParameter("name", "Jade")
            .getSingleResult();
    System.out.println(prueba);


    em.close();

  }
}
