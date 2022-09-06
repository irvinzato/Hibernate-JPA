package org.rivera.hibernateapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")   //Nombre de tabla sql
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)   //Indio que es AI
  private Long id;

  @Column(name = "nombre")    //Como no se llaman igual que la tabla se lo indico
  private String name;        //Importante el valor en objeto y valor en base de datos para hacer consultas

  @Column(name = "apellido")
  private String lastName;

  @Column(name = "forma_pago")
  private String wayToPay;

  // ¡Siempre debe haber un constructor vacío! para que JPA pueda instancear la clase
  public Cliente() {
  }

  public Cliente(String name, String lastName) {
    this.name = name;
    this.lastName = lastName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getWayToPay() {
    return wayToPay;
  }

  public void setWayToPay(String wayToPay) {
    this.wayToPay = wayToPay;
  }

  @Override
  public String toString() {
    return "Cliente " +
            "id=" + id +
            ", name='" + name +
            ", lastName='" + lastName +
            ", wayToPay='" + wayToPay;
  }
}
