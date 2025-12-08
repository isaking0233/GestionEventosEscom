package com.ipn.mx.gestioneventos.core.domain.util.service;



public interface EmailService {

    public void enviarEmail(String to, String subject, String text);

}
