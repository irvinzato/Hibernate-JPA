package org.rivera.hibernateapp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")   //Nombre de tabla sql
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)   //Indico que es AI
  private Long id;

  @Column(name = "nombre")    //Como no se llaman igual que la tabla se lo indico
  private String name;        //Importante el valor en objeto y valor en base de datos para hacer consultas

  @Column(name = "apellido")
  private String lastName;

  @Column(name = "forma_pago")
  private String wayToPay;

  @Embedded   //Que se incluye alguna otra clase(TODOS LOS ATRIBUTOS, TODO EL CONTENIDO)
  private Auditoria aud = new Auditoria();


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
    LocalDateTime createIn = this.aud != null ?aud.getCreateIn() :null;
    LocalDateTime editedIn = this.aud != null ?aud.getEditedIn() :null;
    return "Cliente " +
            "id=" + id +
            ", name='" + name +
            ", lastName='" + lastName +
            ", wayToPay='" + wayToPay +
            ", creado en = " + createIn +
            ", editado en = " + editedIn;
  }

  //Nota - V.510 Se explica como añadir una configuración en "persistence.xml" para que la tabla se elimine y se vuelva a crear al utilizarla
  //E importaciones de tablas Sql
}
