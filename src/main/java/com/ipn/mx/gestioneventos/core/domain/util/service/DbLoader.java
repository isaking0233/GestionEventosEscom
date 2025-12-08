package com.ipn.mx.gestioneventos.core.domain.util.service;

import com.ipn.mx.gestioneventos.core.domain.Asistente;
import com.ipn.mx.gestioneventos.core.domain.Evento;
import com.ipn.mx.gestioneventos.core.domain.features.asistente.repository.AsistenteRepository;
import com.ipn.mx.gestioneventos.core.domain.features.evento.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class DbLoader implements CommandLineRunner {

    @Autowired
    private AsistenteRepository asistenteRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public void run(String... args) throws Exception {
        cargarAsistentes();
        cargarEventos();
    }

    private void cargarAsistentes() {
        if (asistenteRepository.count() == 0) {
            System.out.println("--- Cargando datos de prueba: ASISTENTES ---");
            List<Asistente> asistentes = List.of(
                    Asistente.builder().nombre("Isaac").paterno("Lopez").materno("Garduño").email("isaac@test.com").build(),
                    Asistente.builder().nombre("Juan").paterno("Perez").materno("Garcia").email("juan@test.com").build(),
                    Asistente.builder().nombre("Maria").paterno("Gomez").materno("Diaz").email("maria@test.com").build(),
                    Asistente.builder().nombre("Luis").paterno("Hernandez").materno("Ruiz").email("luis@test.com").build(),
                    Asistente.builder().nombre("Ana").paterno("Martinez").materno("Solis").email("ana@test.com").build()
            );
            asistenteRepository.saveAll(asistentes);
            System.out.println("--- 5 Asistentes insertados exitosamente ---");
        } else {
            System.out.println("--- Ya existen Asistentes en la BD ---");
        }
    }

    private void cargarEventos() {
        if (eventoRepository.count() == 0) {
            System.out.println("--- Cargando datos de prueba: EVENTOS ---");

            // Usamos Calendar para asegurar que las fechas sean FUTURAS
            // y cumplan con la validación @FutureOrPresent de tu entidad
            Calendar calendar = Calendar.getInstance();

            // Evento 1: Mañana
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date inicio1 = calendar.getTime();
            calendar.add(Calendar.HOUR, 5);
            Date fin1 = calendar.getTime();

            // Evento 2: Próxima semana
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            Date inicio2 = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 2);
            Date fin2 = calendar.getTime();

            // Evento 3: Próximo mes
            calendar.add(Calendar.MONTH, 1);
            Date inicio3 = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 3);
            Date fin3 = calendar.getTime();

            List<Evento> eventos = List.of(
                    Evento.builder()
                            .nombre("Congreso de Java Spring Boot 2025")
                            .descripcion("Evento intensivo sobre microservicios y nube.")
                            .fechaInicio(inicio1)
                            .fechaTermino(fin1)
                            .build(),
                    Evento.builder()
                            .nombre("Hackathon ESCOM Yugabyte")
                            .descripcion("Competencia de programación de 48 horas non-stop.")
                            .fechaInicio(inicio2)
                            .fechaTermino(fin2)
                            .build(),
                    Evento.builder()
                            .nombre("Taller de Bases de Datos Distribuidas")
                            .descripcion("Aprende a escalar PostgreSQL con Yugabyte.")
                            .fechaInicio(inicio3)
                            .fechaTermino(fin3)
                            .build()
            );
            eventoRepository.saveAll(eventos);
            System.out.println("--- 3 Eventos insertados exitosamente ---");
        } else {
            System.out.println("--- Ya existen Eventos en la BD ---");
        }
    }
}