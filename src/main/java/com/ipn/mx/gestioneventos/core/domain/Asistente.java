package com.ipn.mx.gestioneventos.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Asistente", schema = "public")
public class Asistente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAsistente", nullable = false)
    private Long idAsistente;

    @NotEmpty(message = "El nombre no debera estar vacío")
    @Size(min = 2, max = 50, message= "El nombre debera estar entre 2 y 50")
    @Column(name = "nombre",length = 50,nullable = false)
    private String nombre;

    @NotEmpty(message = "El apellido paterno no debera estar vacío")
    @Size(min = 2, max = 50, message= "El apellido paterno debera estar entre 2 y 50")
    @Column(name = "paterno",length = 50,nullable = false)
    private String paterno;

    @NotEmpty(message = "El apellido materno no debera estar vacío")
    @Size(min = 2, max = 50, message= "El apellido materno debera estar entre 2 y 50")
    @Column(name = "materno",length = 50,nullable = false)
    private String materno;

    @Email(message = "Introduce un correo electrónico válido")
    @NotEmpty(message = "El correo electrónico no debera estar vacío")
    @Size(min = 2, max = 50, message= "El email debera estar entre 2 y 50")
    @Column(name = "email",length = 200,nullable = false)
    private String email;


}
