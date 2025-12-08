package com.ipn.mx.gestioneventos.core.domain.features.asistente.controller;

import com.ipn.mx.gestioneventos.core.domain.Asistente;
import com.ipn.mx.gestioneventos.core.domain.features.asistente.service.AsistenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/asistentes")
public class AsistenteController {

    @Autowired
    private AsistenteService service;

    @GetMapping
    public List<Asistente> listaAsistentes() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> mostrarAsistente(@PathVariable Long id) {
        Asistente asistente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            asistente = service.findById(id);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la consulta");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (asistente == null) {
            response.put("mensaje", "El asistente con ID: " + id + " no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(asistente, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> crearAsistente(@Valid @RequestBody Asistente asistente, BindingResult result) {
        Asistente asistenteNuevo = null;
        Map<String, Object> response = new HashMap<>();

        // Validación de errores del formulario (Entidad)
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            asistenteNuevo = service.save(asistente);
        } catch (Exception e) {
            response.put("mensaje", "Error al registrar al asistente");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Asistente creado con éxito y correo enviado (si configurado)");
        response.put("asistente", asistenteNuevo);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAsistente(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            service.deleteById(id);
        } catch (Exception e) {
            response.put("mensaje", "Error al eliminar al asistente");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Asistente eliminado con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarAsistente(@RequestBody Asistente asistente, @PathVariable Long id) {
        Asistente asistenteActual = null;
        Asistente asistenteActualizado = null;
        Map<String, Object> response = new HashMap<>();

        try {
            asistenteActual = service.findById(id);
        } catch (Exception e) {
            response.put("mensaje", "Error al consultar la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (asistenteActual == null) {
            response.put("mensaje", "Error: no se pudo editar, el asistente ID: " + id + " no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            // Actualizamos los datos
            asistenteActual.setNombre(asistente.getNombre());
            asistenteActual.setPaterno(asistente.getPaterno());
            asistenteActual.setMaterno(asistente.getMaterno());
            asistenteActual.setEmail(asistente.getEmail());

            asistenteActualizado = service.save(asistenteActual);

        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar el asistente");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El asistente ha sido actualizado con éxito");
        response.put("asistente", asistenteActualizado);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}