package org.rivera.hibernateapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@Embeddable //Quiere decir que puede formar parte de otra(s) clase(s) los atributos que tenga aquí
public class Auditoria {

  //Columnas añadidas en práctica de eventos del ciclo de vida
  @Column(name = "creado_en")
  private LocalDateTime createIn;

  @Column(name = "editado_en")
  private LocalDateTime editedIn;

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

}
