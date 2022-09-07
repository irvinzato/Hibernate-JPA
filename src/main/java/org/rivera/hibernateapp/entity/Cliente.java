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

  //Columnas añadidas en práctica de eventos del ciclo de vida
  @Column(name = "creado_en")
  private LocalDateTime createIn;

  @Column(name = "editado_en")
  private LocalDateTime editedIn;

  // ¡Siempre debe haber un constructor vacío! para que JPA pueda instancear la clase
  public Cliente() {
  }

  public Cliente(String name, String lastName) {
    this.name = name;
    this.lastName = lastName;
  }

  //EVENTOS DE CICLO DE VIDA - UNA FUNCIÓN BASTANTE UTIL ES USARLOS PARA FECHAS DE CUANDO SE CREA ALGÚN REGISTRO O SE EDITA
  @PrePersist
  public void prePersist() {
    System.out.println("Inicializar algo antes del PERSIST()");
    this.createIn = LocalDateTime.now();    //Pongo fecha cuando es creado
  }

  @PreUpdate
  public void preUpdate() {
    System.out.println("Inicializar algo antes del UPDATE");
    this.editedIn = LocalDateTime.now();    //Pongo fecha cuando es editado
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

  public LocalDateTime getCreateIn() {
    return createIn;
  }

  public void setCreateIn(LocalDateTime createIn) {
    this.createIn = createIn;
  }

  public LocalDateTime getEditedIn() {
    return editedIn;
  }

  public void setEditedIn(LocalDateTime editedIn) {
    this.editedIn = editedIn;
  }

  @Override
  public String toString() {
    return "Cliente " +
            "id=" + id +
            ", name='" + name +
            ", lastName='" + lastName +
            ", wayToPay='" + wayToPay +
            ", creado en = " + createIn +
            ", editado en = " + editedIn;
  }
}
