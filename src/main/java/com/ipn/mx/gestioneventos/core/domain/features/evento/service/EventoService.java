package com.ipn.mx.gestioneventos.core.domain.features.evento.service;

import com.ipn.mx.gestioneventos.core.domain.Evento;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface EventoService {

    public List<Evento> findAll();
    public Evento save(Evento evento);
    public Evento findById(Long id);
    public void deletebyId(Long id);

    public ByteArrayInputStream reporte(List<Evento> ListaEventos);//Nos toca a nostroa, deberia de generar un PDF

}
