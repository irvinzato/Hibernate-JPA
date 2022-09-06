package org.rivera.hibernateapp.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.rivera.hibernateapp.entity.Cliente;
import org.rivera.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateCriteria {
  public static void main(String[] args) {
    System.out.println("======= ¡ API CRITERIA ! =======");
    System.out.println("======= No hay consultas directas, es mediante programación de objetos, métodos, de forma programática(Más dinámico) =======");

    EntityManager em = JpaUtil.getEntityManager();
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();  //Permite generar paso a paso las consultas hibernate

    System.out.println("======= Lista de todos los elementos de la tabla clientes =======");
    CriteriaQuery<Cliente> query = criteriaBuilder.createQuery(Cliente.class);  //Resultado del tipo dato query
    Root<Cliente> from = query.from(Cliente.class);   //Equivale al "FROM Cliente c"
    query.select(from);

    List<Cliente> clients = em.createQuery(query)   //El objeto query contiene toda la sentencia
                    .getResultList();
    clients.forEach(System.out::println);

    System.out.println("======= Listar todos los nombres iguales - Where y Equal =======");
    query = criteriaBuilder.createQuery(Cliente.class);   //Reutilizo las variables para reiniciar la consulta
    from = query.from(Cliente.class);

    query.select(from).where(criteriaBuilder.equal(from.get("name"), "Mauricio"));
    clients = em.createQuery(query)
                    .getResultList();
    clients.forEach(System.out::println);

    System.out.println("======= Listar todos los nombres iguales con parámetro - Where y Equal =======");
    query = criteriaBuilder.createQuery(Cliente.class);   //Reutilizo las variables para reiniciar la consulta
    from = query.from(Cliente.class);
    ParameterExpression<String> nameParam = criteriaBuilder.parameter(String.class, "name");

    query.select(from).where(criteriaBuilder.equal(from.get("name"), nameParam));
    clients = em.createQuery(query)
                    .setParameter("name", "Jade")
                    .getResultList();
    clients.forEach(System.out::println);






    em.close();
  }
}
