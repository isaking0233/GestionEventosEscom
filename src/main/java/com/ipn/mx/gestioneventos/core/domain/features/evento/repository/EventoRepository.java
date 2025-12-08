package com.ipn.mx.gestioneventos.core.domain.features.evento.repository;

import com.ipn.mx.gestioneventos.core.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento,Long> {

    // Metodo JPQL para buscar por coincidencia en el nombre (ignora mayúsculas/minúsculas)
    @Query("SELECT e FROM Evento e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Evento> buscarPorNombre(@Param("nombre") String nombre);
}