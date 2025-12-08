package com.ipn.mx.gestioneventos.core.domain.features.asistente.service;

import com.ipn.mx.gestioneventos.core.domain.Asistente;
import java.util.List;

public interface AsistenteService {
    List<Asistente> findAll();
    Asistente save(Asistente asistente);
    Asistente findById(Long id);
    void deleteById(Long id);
}