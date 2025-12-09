package com.ipn.mx.gestioneventos.core.domain;

import jakarta.persistence.*;
import java.util.List;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Evento", schema = "public")
public class Evento implements Serializable {

    /*
    create table Evento{
    idEvento serial not null primary key,
    nombre varchar(200) not null,
    descripcion  varchar(500) not null,
    fechaInicio date not null,
    fechaTermino not null.

    Reglas:
     Nombre debe de ser unico
     fecha de eventos se va crear con fechas actuales (hoy o fechas futyuras)
     */


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvento", nullable = false)
    private Long idEvento;

    @NotEmpty(message = "El nombre no debe estar vacío")
    @Column(name = "nombre", length = 200, nullable = false, unique = true)
    private String nombre;

    @NotEmpty(message = "La descripcion no debe estar vacía")
    @Column(name = "descripcion", length = 500, nullable = false)
    private String descripcion;

   // @NotEmpty(message = "La fecha de inicio no debe estar vacía")
   @NotNull(message = "La fecha de inicio es obligatoria")
   @FutureOrPresent(message = "La fecha de inicio deberá ser la actual o una fecha posterior")
   @Temporal(TemporalType.DATE)
   @Column(name = "fechaInicio", nullable = false)
   private Date fechaInicio;

    //Investigar porque quitamos el not empty

    //@NotEmpty(message = "La fecha de termino no debe estar vacía")
    @NotNull(message = "La fecha de término es obligatoria")
    @FutureOrPresent(message = "La fecha de término deberá ser la actual o una fecha posterior")
    @Temporal(TemporalType.DATE)
    @Column(name = "fechaTermino", nullable = false)
    private Date fechaTermino;

    // ... tus otros campos ...

    @OneToMany(mappedBy = "idEvento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Asistente> asistentes;

}


//Averiguar que hace el future or present---->que nos esta provocando problemas
