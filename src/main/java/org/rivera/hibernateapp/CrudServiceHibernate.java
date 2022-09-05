package org.rivera.hibernateapp;

import jakarta.persistence.EntityManager;
import org.rivera.hibernateapp.entity.Cliente;
import org.rivera.hibernateapp.services.ClienteService;
import org.rivera.hibernateapp.services.ClienteServiceImp;
import org.rivera.hibernateapp.util.JpaUtil;

import java.util.List;
import java.util.Optional;

public class CrudServiceHibernate {

  public static void main(String[] args) {

    EntityManager em = JpaUtil.getEntityManager();
    ClienteService service = new ClienteServiceImp(em);

    System.out.println("----- Listar clientes -----");
    List<Cliente> clients = service.listClients();
    clients.forEach( c -> System.out.println(c) );

    System.out.println("----- Por id cliente -----");
    Optional<Cliente> opClient = service.clientById( 2L );
    if( opClient.isPresent() ) {
      System.out.println( opClient.get() );
    }

    System.out.println("----- Guardar cliente -----");
    Cliente client1 = new Cliente();
    client1.setName("Nombre de prueba");
    client1.setLastName("Apellido de prueba");
    client1.setWayToPay("debito");
    service.saveClient(client1);
    System.out.println("Cliente guardado con éxito !");

    System.out.println("----- Editar cliente -----");
    Long id = client1.getId();  //Como ya lo creé, ya tiene id
    opClient = service.clientById(id);
    opClient.ifPresent( c -> {    //Con expresión lambda en lugar de if con present
      c.setWayToPay("paypal");
      service.saveClient(c);
      System.out.println("Actualizado correctamente");
    });

    service.listClients().forEach( c -> System.out.println( c ) );

    System.out.println("----- Eliminar cliente -----");
    id = client1.getId();
    opClient = service.clientById(id);
    if( opClient.isPresent() ) {
      service.deleteClient( opClient.get().getId() );
    }

    service.listClients().forEach( System.out::println );


    em.close(); //Siempre fuera para que no cierre la conexión ningún método del service y pueda seguir utilizando

  }
}
