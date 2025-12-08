package com.ipn.mx.gestioneventos.core.domain.features.evento.service.impl;

import com.ipn.mx.gestioneventos.core.domain.Evento;
import com.ipn.mx.gestioneventos.core.domain.features.evento.repository.EventoRepository;
import com.ipn.mx.gestioneventos.core.domain.features.evento.service.EventoService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;


@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository dao;


    @Override
    @Transactional(readOnly = true)
    public List<Evento> findAll() {
        return dao.findAll();
    }

    @Override
    @Transactional
    public Evento save(Evento evento) {
        return dao.save(evento);
    }

    @Override
    @Transactional(readOnly = true)
    public Evento findById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deletebyId(Long id) {
        dao.deleteById(id);

    }

    @Override
    public ByteArrayInputStream reporte(List<Evento> listaEventos) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Titulo
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25, BaseColor.BLUE);
            Paragraph titulo = new Paragraph("Reporte de Eventos ESCOM", fontTitle);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(Chunk.NEWLINE);

            // Tabla de 4 columnas
            PdfPTable table = new PdfPTable(4);
            // Encabezados
            String[] headers = {"ID", "Nombre", "Inicio", "Termino"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            // Datos
            for (Evento evento : listaEventos) {
                table.addCell(evento.getIdEvento().toString());
                table.addCell(evento.getNombre());
                // SimpleDateFormat para que se vea bien la fecha (opcional)
                table.addCell(evento.getFechaInicio().toString());
                table.addCell(evento.getFechaTermino().toString());
            }

            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            Logger.getLogger(EventoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
