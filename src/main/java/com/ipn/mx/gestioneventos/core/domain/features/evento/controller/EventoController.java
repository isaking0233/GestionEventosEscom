package com.ipn.mx.gestioneventos.core.domain.features.evento.controller;

import com.ipn.mx.gestioneventos.core.domain.Evento;
import com.ipn.mx.gestioneventos.core.domain.features.evento.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    @Autowired
    private EventoService service;

    // 1. OBTENER TODOS
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Evento> ListaEventos() {
        return service.findAll();
    }

    // 2. OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> MostrarEvento(@PathVariable Long id) {
        Evento e = null;
        Map<String,Object> respuesta = new HashMap<>();
        try {
            e = service.findById(id);
        } catch (Exception ex) {
            respuesta.put("mensaje", "Error al realizar la consulta");
            respuesta.put("error", ex.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (e == null) {
            respuesta.put("mensaje", "El evento ID: " + id + " no existe");
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    // 3. CREAR EVENTO (Corregido)
    @PostMapping
    public ResponseEntity<?> crearEvento(@RequestBody Evento evento) {
        Evento eventoNuevo = null;
        Map<String,Object> respuesta = new HashMap<>();
        try {
            // Aquí es donde guardamos realmente
            eventoNuevo = service.save(evento);
        } catch (Exception ex) {
            respuesta.put("mensaje", "Error al realizar el registro");
            respuesta.put("error", ex.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje", "El evento ha sido creado con éxito");
        respuesta.put("evento", eventoNuevo);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // 4. ACTUALIZAR EVENTO
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEvento(@RequestBody Evento evento, @PathVariable Long id) {
        Evento eventoActual = service.findById(id);
        Evento eventoActualizado = null;
        Map<String, Object> respuesta = new HashMap<>();

        if (eventoActual == null) {
            respuesta.put("mensaje", "Error: no se pudo editar, el evento ID: " + id + " no existe");
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }

        try {
            eventoActual.setNombre(evento.getNombre());
            eventoActual.setDescripcion(evento.getDescripcion());
            eventoActual.setFechaInicio(evento.getFechaInicio());
            eventoActual.setFechaTermino(evento.getFechaTermino());

            eventoActualizado = service.save(eventoActual);
        } catch (Exception e) {
            respuesta.put("mensaje", "Error al actualizar el evento");
            respuesta.put("error", e.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        respuesta.put("mensaje", "El evento ha sido actualizado con éxito");
        respuesta.put("evento", eventoActualizado);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // 5. ELIMINAR EVENTO
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEvento(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            service.deletebyId(id);
        } catch (Exception e) {
            respuesta.put("mensaje", "Error al eliminar el evento");
            respuesta.put("error", e.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje", "El evento ha sido eliminado con éxito");
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    // 6. DESCARGAR PDF
    @GetMapping("/reporte/pdf")
    public ResponseEntity<InputStreamResource> generarReporte() {
        List<Evento> eventos = service.findAll();
        ByteArrayInputStream bis = service.reporte(eventos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=lista-eventos.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}