package com.ipn.mx.gestioneventos;

import com.ipn.mx.gestioneventos.core.domain.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GaestionEventosEscomApplication implements CommandLineRunner {
    //@Autowired
    //private EmailService service;
    @Override
    public void run(String... args) throws Exception {
        /*String to = "gatote124@gamil.com";
        String subject = "Ejemplo de envio de correo";
        String texto = "Ejemplo";
        service.enviarEmail(to, subject, texto);*/
    }
    public static void main(String[] args) {
        SpringApplication.run(GaestionEventosEscomApplication.class, args);
    }

}
