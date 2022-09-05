package org.rivera.hibernateapp.services;

import jakarta.persistence.EntityManager;
import org.rivera.hibernateapp.entity.Cliente;
import org.rivera.hibernateapp.repository.ClienteRepository;
import org.rivera.hibernateapp.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public class ClienteServiceImp implements ClienteService{

  private EntityManager em;
  private CrudRepository<Cliente> repository;

  public ClienteServiceImp(EntityManager em) {
    this.em = em;
    this.repository = new ClienteRepository(em);
  }

  @Override //No necesita manejo de transacciones(try, catch, finally)
  public List<Cliente> listClients() {
    return repository.toList();
  }

  @Override //No necesita manejo de transacciones(try, catch, finally)
  public Optional<Cliente> clientById(Long id) {
    return Optional.ofNullable(repository.byId(id));
  }

  @Override //La conexión se cierra fuera del servicio
  public void saveClient(Cliente client) {
    try{
      em.getTransaction().begin();

      repository.save(client);

      em.getTransaction().commit();
    }catch( Exception e ){
      em.getTransaction().rollback();
      e.printStackTrace();
    }
  }

  @Override //La conexión se cierra fuera del servicio
  public void deleteClient(Long id) {
    try{
      em.getTransaction().begin();

      repository.delete(id);

      em.getTransaction().commit();
    }catch( Exception e ){
      em.getTransaction().rollback();
      e.printStackTrace();
    }
  }
}
