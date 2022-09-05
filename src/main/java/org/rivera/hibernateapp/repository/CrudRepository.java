package org.rivera.hibernateapp.repository;

import java.util.List;

public interface CrudRepository<T> {

  List<T> toList();
  T byId(Long id);
  void save(T t);
  void delete(Long id);

}
