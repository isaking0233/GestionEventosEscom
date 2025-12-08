package com.ipn.mx.gestioneventos.core.domain.features.asistente.repository;

import com.ipn.mx.gestioneventos.core.domain.Asistente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsistenteRepository extends JpaRepository<Asistente, Long> {
    // Metodo derivado para buscar por email si lo necesitamos más adelante
    Asistente findByEmail(String email);
}