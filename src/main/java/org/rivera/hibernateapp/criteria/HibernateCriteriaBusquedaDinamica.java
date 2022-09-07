package org.rivera.hibernateapp.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.rivera.hibernateapp.entity.Cliente;
import org.rivera.hibernateapp.util.JpaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HibernateCriteriaBusquedaDinamica {
  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    System.out.println("Filtro para nombre: ");
    String nameSearch = sc.nextLine();

    System.out.println("Filtro para apellido: ");
    String lastNameSearch = sc.nextLine();

    System.out.println("Filtro para forma de pago: ");
    String paySearch = sc.nextLine();

    EntityManager em = JpaUtil.getEntityManager();
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

    CriteriaQuery<Cliente> query = criteriaBuilder.createQuery(Cliente.class);
    Root<Cliente> from = query.from(Cliente.class);

    List<Predicate> conditions = new ArrayList<>();

    if( nameSearch != null && !nameSearch.isEmpty() ) {
      conditions.add( criteriaBuilder.equal(from.get("name"), nameSearch) );
    }
    if( lastNameSearch != null && !lastNameSearch.isEmpty() ) {
      conditions.add( criteriaBuilder.equal(from.get("lastName"), lastNameSearch) );
    }
    if( paySearch != null && !paySearch.equals("") ) {
      conditions.add( criteriaBuilder.equal(from.get("wayToPay"), paySearch ));
    }

    //Se hace un cast porque los predicados los tengo en una lista pero necesita un arreglo de predicados
    query.select(from).where(criteriaBuilder.and( conditions.toArray(new Predicate[conditions.size()]) ));
    List<Cliente> clients = em.createQuery(query)
            .getResultList();
    System.out.println("RESULTADOS DE TU BÚSQUEDA");
    clients.forEach(System.out::println);

    em.close();

  }
}
