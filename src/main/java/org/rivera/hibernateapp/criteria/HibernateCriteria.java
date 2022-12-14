package org.rivera.hibernateapp.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
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
    //Reutilizo las variables para reiniciar la consulta
    query = criteriaBuilder.createQuery(Cliente.class);    //Criterio que devuelve
    from = query.from(Cliente.class);                      //De donde

    query.select(from).where(criteriaBuilder.equal(from.get("name"), "Mauricio"));
    clients = em.createQuery(query)
                    .getResultList();
    clients.forEach(System.out::println);

    System.out.println("======= Listar todos los nombres iguales con parámetro - Where y Equal =======");
    query = criteriaBuilder.createQuery(Cliente.class);   //Reutilizo las variables para reiniciar la consulta
    from = query.from(Cliente.class);
    ParameterExpression<String> nameParam = criteriaBuilder.parameter(String.class, "nameQuery");

    query.select(from).where(criteriaBuilder.equal(from.get("name"), nameParam));
    clients = em.createQuery(query)
                    .setParameter("nameQuery", "Jade")
                    .getResultList();
    clients.forEach(System.out::println);

    System.out.println("======= Clientes por nombre(coincidencias) - Where y Like =======");
    query = criteriaBuilder.createQuery(Cliente.class);
    from = query.from(Cliente.class);
    query.select(from).where(criteriaBuilder.like(from.get("name"), "%lu%")); //Importante los espacios entre los porcentajes
    clients = em.createQuery(query)
                    .getResultList();
    clients.forEach(System.out::println);

    System.out.println("======= Clientes por nombre(coincidencias) con parámetros - Where y Like =======");
    System.out.println("======= Como buena práctica utilizar UPPER O LOWER para comparar valores =======");
    query = criteriaBuilder.createQuery(Cliente.class);
    from = query.from(Cliente.class);
    ParameterExpression<String> searchLike = criteriaBuilder.parameter(String.class, "textLikeQuery");
    query.select(from).where(criteriaBuilder.like(criteriaBuilder.upper(from.get("name")), criteriaBuilder.upper(searchLike)));
    clients = em.createQuery(query)
                    .setParameter("textLikeQuery", "%au%")
                    .getResultList();
    clients.forEach(System.out::println);

    System.out.println("======= Para rangos, clientes con id entre ciertos números - Where y Between =======");
    query = criteriaBuilder.createQuery(Cliente.class);
    from = query.from(Cliente.class);
    query.select(from).where(criteriaBuilder.between(from.get("id"), 2L, 6L));
    clients = em.createQuery(query)
                    .getResultList();
    clients.forEach(System.out::println);

    System.out.println("======= Parecido al igual pero varios (IN) - Where, IN =======");
    query = criteriaBuilder.createQuery(Cliente.class);
    from = query.from(Cliente.class);
    query.select(from).where(from.get("name").in("Irving", "Jade", "Angeles", "Lubu")); //Pudiera tenerlo como una lista por parámetro
    clients = em.createQuery(query)
                    .getResultList();
    clients.forEach(System.out::println);

    System.out.println("======= Filtrar usando predicados MAYOR QUE o MAYOR IGUAL QUE =======");
    query = criteriaBuilder.createQuery(Cliente.class);
    from = query.from(Cliente.class);
    query.select(from).where(criteriaBuilder.ge(from.get("id"), 3L));   //"ge" Mayor o igual a 3 - "gt" es para solo mayor
    clients = em.createQuery(query)
                    .getResultList();
    clients.forEach(System.out::println);

    System.out.println("======= Nombres MAYORES QUE 5 =======");
    query = criteriaBuilder.createQuery(Cliente.class);
    from = query.from(Cliente.class);
    query.select(from).where(criteriaBuilder.gt(criteriaBuilder.length(from.get("name")), 5));    //"le" menor o igual - "lt" es para menor que
    clients = em.createQuery(query)
                    .getResultList();
    clients.forEach(System.out::println);

    System.out.println("======= Predicados AND(Conjunción) Y OR(Disyunción) =======");
    System.out.println("======= Consulta que cumple dos condiciones AND =======");
    query = criteriaBuilder.createQuery(Cliente.class);
    from = query.from(Cliente.class);
    Predicate byName = criteriaBuilder.equal(from.get("name"), "Lubu");
    Predicate byPay = criteriaBuilder.equal(from.get("wayToPay"), "paypal");
    query.select(from).where(criteriaBuilder.and(byName, byPay)); //Se cumplen todas las condiciones que se pasan en "AND" - con "OR" se incluye "o que tenga"
    clients = em.createQuery(query)
                    .getResultList();
    clients.forEach(System.out::println);

    System.out.println("======= Predicados AND(Conjunción) Y OR(Disyunción) combinados =======");
    System.out.println("======= Consulta que cumple dos condiciones AND, una de ellas es un OR =======");
    query = criteriaBuilder.createQuery(Cliente.class);
    from = query.from(Cliente.class);
    Predicate greaterThan = criteriaBuilder.ge(from.get("id"), 4L);
    query.select(from).where(criteriaBuilder.and( greaterThan, criteriaBuilder.or(byName, byPay))); //El or puede ser uno u otro para cumplir
    clients = em.createQuery(query)
            .getResultList();
    clients.forEach(System.out::println);

    System.out.println("======= ORDER BY - ASC, DESC =======");
    System.out.println("======= Ordenamiento por nombre y apellido =======");
    query = criteriaBuilder.createQuery(Cliente.class);
    from = query.from(Cliente.class);
    query.select(from).orderBy(criteriaBuilder.asc(from.get("name")), criteriaBuilder.asc(from.get("lastName"))); //OrderBy puede tener más de 1 criterio
    clients = em.createQuery(query)
                    .getResultList();
    clients.forEach(System.out::println);

    System.out.println("======= Consulta por id en especifico con parámetro =======");
    query = criteriaBuilder.createQuery(Cliente.class);
    from = query.from(Cliente.class);
    ParameterExpression<Long> idParameter = criteriaBuilder.parameter(Long.class, "idP");
    query.select(from).where(criteriaBuilder.equal(from.get("id"), idParameter));
    Cliente client = em.createQuery(query)
                    .setParameter("idP", 7L)
                    .getSingleResult();
    System.out.println("Cliente encontrado - " + client);

    System.out.println("======= Solo quiero obtener los nombre de los clientes =======");
    CriteriaQuery<String> queryString = criteriaBuilder.createQuery(String.class);    //Devuelvo solo Strings
    from = queryString.from(Cliente.class);         //De la misma tabla
    queryString.select(from.get("name"));
    List<String> namesString = em.createQuery(queryString)
                    .getResultList();
    namesString.forEach(System.out::println);

    System.out.println("======= DISTINCT - Solo quiero obtener los nombre de los clientes pero SIN REPETIRLOS =======");
    queryString = criteriaBuilder.createQuery(String.class);    //Devuelvo solo Strings
    from = queryString.from(Cliente.class);         //De la misma tabla
    queryString.select(from.get("name")).distinct(true);
    namesString = em.createQuery(queryString)
                    .getResultList();
    namesString.forEach(System.out::println);

    System.out.println("======= Consulta por nombre y apellidos concatenados - CONCAT =======");
    queryString = criteriaBuilder.createQuery(String.class);
    from = queryString.from(Cliente.class);
    queryString.select(criteriaBuilder.concat(criteriaBuilder.concat(from.get("name"), " "), from.get("lastName")));
    namesString = em.createQuery(queryString)
                    .getResultList();
    namesString.forEach(System.out::println);

    System.out.println("======= ======= ======= =======");
    System.out.println("======= MULTI SELECT =======");
    System.out.println("======= ======= ======= =======");
    System.out.println("======= Consulta de campos personalizados del entity cliente =======");
    CriteriaQuery<Object[]> queryObj = criteriaBuilder.createQuery(Object[].class);     //Personalizo un objeto de resultados
    from = queryObj.from(Cliente.class);
    queryObj.multiselect(from.get("id"), from.get("name"), from.get("lastName"));   //"multiselect" devuelve muchos, "select" solo uno
    List<Object[]> registers = em.createQuery(queryObj)
                    .getResultList();
    registers.forEach(reg -> {
      Long idObj = (Long) reg[0];
      String nameObj = (String) reg[1];
      String lastNameObj = (String) reg[2];
      System.out.println("ID - " + idObj + " NOMBRE - " + nameObj + " APELLIDO - " + lastNameObj);
    });

    System.out.println("======= Consulta de campos personalizados del entity cliente con WHERE y un UPPER =======");
    queryObj = criteriaBuilder.createQuery(Object[].class);
    from = queryObj.from(Cliente.class);
    queryObj.multiselect(from.get("id"), criteriaBuilder.upper(from.get("name")), from.get("lastName")).where(criteriaBuilder.like(from.get("name"),"%lu%"));
    registers = em.createQuery(queryObj)
                    .getResultList();
    registers.forEach(reg -> {
      Long idObj = (Long) reg[0];
      String nameObj = (String) reg[1];
      String lastNameObj = (String) reg[2];
      System.out.println("ID - " + idObj + " NOMBRE - " + nameObj + " APELLIDO - " + lastNameObj);
    });

    System.out.println("======= Consulta de UN SOLO REGISTRO con campos personalizados =======");
    queryObj = criteriaBuilder.createQuery(Object[].class);
    from = queryObj.from(Cliente.class);
    queryObj.multiselect(from.get("name"), from.get("lastName"), from.get("wayToPay")).where(criteriaBuilder.equal(from.get("id"), 7L));
    Object[] register = em.createQuery(queryObj)
                    .getSingleResult();
    String nameObj = (String) register[0];
    String lastNameObj = (String) register[1];
    String payObj = (String) register[2];
    System.out.println("NOMBRE - " + nameObj + " APELLIDO - " + lastNameObj + " FORMA DE PAGO " + payObj);


    System.out.println("======= ======= ======= =======");
    System.out.println("======= FUNCIÓN DE AGREGACIÓN =======");
    System.out.println("======= ======= ======= =======");
    System.out.println("======= Contar registros de la consulta con - COUNT =======");
    CriteriaQuery<Long> queryLong = criteriaBuilder.createQuery(Long.class);
    from = queryLong.from(Cliente.class);
    queryLong.select(criteriaBuilder.count(from));  //Pudiera poner from.get() pero el resultado es el mismo
    Long total = em.createQuery(queryLong)
                    .getSingleResult();
    System.out.println("Total de registros en tabla clientes - " + total);

    System.out.println("======= Sumar datos de algún campo de la tabla - SUM =======");
    queryLong = criteriaBuilder.createQuery(Long.class);
    from = queryLong.from(Cliente.class);
    queryLong.select(criteriaBuilder.sum(from.get("id")));
    total = em.createQuery(queryLong)
                    .getSingleResult();
    System.out.println("Suma de ids - " + total);

    System.out.println("======= MÁXIMO O MÍNIMO =======");
    queryLong = criteriaBuilder.createQuery(Long.class);
    from = queryLong.from(Cliente.class);
    queryLong.select(criteriaBuilder.max(from.get("id")));
    Long maxId = em.createQuery(queryLong)
                    .getSingleResult();
    System.out.println("ID máximo - " + maxId);

    System.out.println("======= Ejemplo de varios resultados de funciones de agregación en una sola consulta =======");
    queryObj = criteriaBuilder.createQuery(Object[].class);
    from = queryObj.from(Cliente.class);
    queryObj.multiselect(criteriaBuilder.count(from.get("id")), criteriaBuilder.sum(from.get("id")), criteriaBuilder.min(from.get("id")), criteriaBuilder.max(from.get("id")));
    Object[] statistics = em.createQuery(queryObj)
                    .getSingleResult();
    System.out.println("CONTEO DE IDS - " + statistics[0] + " SUMAS DE IDS - " + statistics[1] + " ID MAS CHICO - " + statistics[2] + " ID MAS GRANDE - " + statistics[3]);




    System.out.println("======= PRUEBAS =======");
    query = criteriaBuilder.createQuery(Cliente.class);
    from = query.from(Cliente.class);

    query.select(from).where(criteriaBuilder.equal(from.get("id"), 2L));
    clients = em.createQuery(query)
            .getResultList();
    clients.forEach(System.out::println);


    em.close();
  }
}
