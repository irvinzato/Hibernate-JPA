package org.rivera.hibernateapp.repository;

import jakarta.persistence.EntityManager;
import org.rivera.hibernateapp.entity.Cliente;

import java.util.List;

public class ClienteRepository implements CrudRepository<Cliente>{

  private EntityManager em;

  public ClienteRepository(EntityManager em) {
    this.em = em;
  }

  @Override
  public List<Cliente> toList() {
    return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
  }

  @Override
  public Cliente byId(Long id) {
    return em.find(Cliente.class, id);  //Crea el "Cliente"
  }

  @Override
  public void save(Cliente cliente) {
    if( cliente.getId() != null && cliente.getId() > 0 ) {
      em.merge(cliente);
    } else {
      em.persist(cliente);
    }
  }

  @Override
  public void delete(Long id) {
    Cliente client = byId(id);
    em.remove(client);
  }
}
