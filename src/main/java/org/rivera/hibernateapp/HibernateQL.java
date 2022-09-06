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

    System.out.println("======= Lista de Cliente con solo el nombre =======");
    List<String> listNames = em.createQuery("SELECT c.name FROM Cliente c", String.class)
                    .getResultList();
    listNames.forEach(System.out::println);

    System.out.println("======= Consulta con DISTINCT para nombres únicos de tabla clientes =======");
    listNames = em.createQuery("SELECT DISTINCT(c.name) FROM Cliente c", String.class)
                    .getResultList();
    listNames.forEach(System.out::println);

    System.out.println("======= Consulta con COUNT para total de formas de pago =======");
    Long totalPays = em.createQuery("SELECT COUNT(DISTINCT(c.wayToPay)) FROM Cliente c", Long.class)
                    .getSingleResult();
    System.out.println("Maneras de pagar - " + totalPays);

    System.out.println("======= Consulta con CONCAT para nombre y apellido =======");
    listNames = em.createQuery("SELECT CONCAT(c.name, ' ', c.lastName) FROM Cliente c", String.class)
                    .getResultList();
    listNames.forEach(System.out::println);

    System.out.println("======= Consulta con CONCAT para nombre y apellido usando UPPER O LOWER =======");
    listNames = em.createQuery("SELECT UPPER(CONCAT(c.name, ' ', c.lastName)) FROM Cliente c", String.class)
                    .getResultList();
    listNames.forEach(System.out::println);

    System.out.println("======= Consulta con LIKE(coincidencias) y parámetro por nombre ======="); //Como buena práctica consultar en lower o upper ambos
    List<Cliente> clientCoins = em.createQuery("SELECT c FROM Cliente c WHERE c.name LIKE :parametro", Cliente.class)
                    .setParameter("parametro", "%" + "ma" + "%") //Ocupa los "%" para que busque izquierda y derecha la coincidencia
                    .getResultList();
    clientCoins.forEach(System.out::println);

    System.out.println("======= Consulta con BETWEEN - Sirve para rangos en tipo de datos numéricos, fechas o rango de caracteres =======");
    System.out.println("======= Rango de id´s =======");
    clientCoins = em.createQuery("SELECT c FROM Cliente c WHERE c.id BETWEEN 2 AND 5", Cliente.class)
                    .getResultList();
    clientCoins.forEach(System.out::println);

    System.out.println("======= Rango de caracteres - No se incluye el ultimo carácter =======");
    clientCoins = em.createQuery("SELECT c FROM Cliente c WHERE c.name BETWEEN 'J' AND 'P'", Cliente.class)
                    .getResultList();
    clientCoins.forEach(System.out::println);

    System.out.println("======= Consulta con ORDER BY - ordenamiento puede ser ASC O DESC =======");
    System.out.println("======= Puedo utilizar dos parámetros, uno ASC y el otro también(O a criterio) por si hay repetidos =======");
    clientCoins = em.createQuery("SELECT c FROM Cliente c ORDER BY c.name ASC", Cliente.class)
                    .getResultList();
    clientCoins.forEach(System.out::println);

    System.out.println("======= Consulta para total de registro en la tabla =======");
    Long total = em.createQuery("SELECT COUNT(c) FROM Cliente c", Long.class)
                    .getSingleResult();
    System.out.println(total);

    System.out.println("======= Consulta para valor MÍNIMO de CUALQUIER campo numérico(Este caso ID) =======");
    total = em.createQuery("SELECT MIN(c.id) FROM Cliente c", Long.class)
                    .getSingleResult();
    System.out.println(total);

    System.out.println("======= Consulta para valor MÁXIMO/ULTIMO de CUALQUIER campo numérico(Este caso ID) =======");
    total = em.createQuery("SELECT MAX(c.id) FROM Cliente c", Long.class)
                    .getSingleResult();
    System.out.println(total);

    System.out.println("======= Consulta con nombre y largo de todos los registros (LENGTH) =======");
    List<Object[]> lengthNames = em.createQuery("SELECT c.name, LENGTH(c.name) FROM Cliente c", Object[].class)
                    .getResultList();
    lengthNames.forEach(reg -> {
      String nameLen = (String) reg[0];
      Integer large = (Integer) reg[1];
      System.out.println("Nombre - " + nameLen + " con largo - " + large);
    });

    System.out.println("======= Consulta para el nombre mas CORTO(EN TAMAÑO) =======");
    Integer minNameLarge = em.createQuery("SELECT MIN(LENGTH(c.name)) FROM Cliente c", Integer.class)
                    .getSingleResult();
    System.out.println(minNameLarge);

    System.out.println("======= Consulta para el nombre mas LARGO(EN TAMAÑO) =======");
    Integer maxNameLarge = em.createQuery("SELECT MAX(LENGTH(c.name)) FROM Cliente c", Integer.class)
                    .getSingleResult();
    System.out.println(maxNameLarge);

    System.out.println("======= Consulta para ESTADÍSTICAS, funciones agregaciones MIN, MAX, SUM, COUNT, AVG  =======");
    Object[] statistics = em.createQuery("SELECT MIN(c.id), MAX(c.id), SUM(c.id), COUNT(c.id), AVG(LENGTH(c.name)) FROM Cliente c", Object[].class)
                    .getSingleResult();
    Long minStatistics = (Long) statistics[0];  //Porque los ids son tipo Long
    Long maxStatistics = (Long) statistics[1];
    Long sumStatistics = (Long) statistics[2];
    Long countStatistics = (Long) statistics[3];
    Double avgStatistics = (Double) statistics[4];
    System.out.println("MIN - " + minStatistics + " MAX - " + maxStatistics + " SUMA - " + sumStatistics + " COUNT - " + countStatistics + " PROMEDIO LARGO DEL NOMBRE - " + avgStatistics);


    System.out.println("======= Pruebas =======");
    String prueba = em.createQuery("SELECT c.lastName FROM Cliente c WHERE c.name=:name", String.class)
            .setParameter("name", "Jade")
            .getSingleResult();
    System.out.println(prueba);


    em.close();

  }
}
