package com.ipn.mx.gestioneventos.core.domain.features.asistente.service.impl;

import com.ipn.mx.gestioneventos.core.domain.Asistente;
import com.ipn.mx.gestioneventos.core.domain.features.asistente.repository.AsistenteRepository;
import com.ipn.mx.gestioneventos.core.domain.features.asistente.service.AsistenteService;
import com.ipn.mx.gestioneventos.core.domain.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AsistenteServiceImpl implements AsistenteService {

    @Autowired
    private AsistenteRepository dao;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional(readOnly = true)
    public List<Asistente> findAll() {
        return dao.findAll();
    }

    @Override
    @Transactional
    public Asistente save(Asistente asistente) {
        // 1. Guardamos en Base de Datos
        Asistente asistenteGuardado = dao.save(asistente);

        // 2. Intentamos enviar el correo de confirmación
        try {
            String asunto = "Registro Exitoso - Gestión Eventos ESCOM";
            String cuerpo = "Hola " + asistenteGuardado.getNombre() + ",\n\n" +
                    "Tu registro ha sido completado exitosamente.\n" +
                    "Adjunto encontrarás información relevante sobre los eventos.\n\n" +
                    "Saludos,\nEquipo ESCOM.";

            emailService.enviarEmail(asistenteGuardado.getEmail(), asunto, cuerpo);
        } catch (Exception e) {
            // Solo imprimimos el error para no cancelar el guardado del usuario
            System.err.println("No se pudo enviar el correo al asistente: " + e.getMessage());
        }

        return asistenteGuardado;
    }

    @Override
    @Transactional(readOnly = true)
    public Asistente findById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}