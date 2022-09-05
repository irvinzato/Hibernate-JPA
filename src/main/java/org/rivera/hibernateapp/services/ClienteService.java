package org.rivera.hibernateapp.services;

import org.rivera.hibernateapp.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

  List<Cliente> listClients();
  Optional<Cliente> clientById(Long id);
  void saveClient(Cliente client);
  void deleteClient(Long id);
}
