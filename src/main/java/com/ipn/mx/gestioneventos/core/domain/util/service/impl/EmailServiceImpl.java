package com.ipn.mx.gestioneventos.core.domain.util.service.impl;

import com.ipn.mx.gestioneventos.core.domain.util.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Inyectamos el valor del correo desde properties para no hardcodearlo
    @Value("${spring.mail.username}")
    private String emailRemitente;

    @Value("classpath:static/nombrepdf.pdf")
    private Resource resourceFile;

    @Override
    public void enviarEmail(String to, String subject, String text) {
        MimeMessage mensaje = mailSender.createMimeMessage();

        try {
            // true = multipart (permite adjuntos)
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, StandardCharsets.UTF_8.name());

            helper.setFrom(emailRemitente); // Usar el mismo correo autenticado
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // true = permite contenido HTML

            // Lógica segura para el adjunto
            if (resourceFile.exists() && resourceFile.isReadable()) {
                try {
                    // Obtenemos el archivo de forma segura
                    File file = resourceFile.getFile();
                    helper.addAttachment("InformacionEvento.pdf", file);
                } catch (Exception e) {
                    System.err.println("Advertencia: No se pudo cargar el PDF adjunto: " + e.getMessage());
                    // El correo se enviará sin adjunto en lugar de fallar
                }
            } else {
                System.out.println("No se encontró el archivo 'nombrepdf.pdf' en static, enviando sin adjunto.");
            }

            mailSender.send(mensaje);
            System.out.println("Correo enviado exitosamente a: " + to);

        } catch (MessagingException e) {
            // Es mejor hacer un log del error que lanzar RuntimeException a ciegas
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
    }
}