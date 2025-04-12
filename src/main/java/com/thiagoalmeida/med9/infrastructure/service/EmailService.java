package com.thiagoalmeida.med9.infrastructure.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.thiagoalmeida.med9.application.dto.AppointmentNotification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;
    
    public void sendAppointmentNotificationEmail(AppointmentNotification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getPatientEmail());
        message.setSubject("Confirmação de Agendamento - Med9");
        message.setText(
            String.format(
                "Olá %s,\n\n" +
                "Seu agendamento foi confirmado com sucesso!\n\n" +
                "Detalhes do agendamento:\n" +
                "Médico: %s\n" +
                "Data e Hora: %s\n" +
                "Observações: %s\n\n" +
                "Atenciosamente,\n" +
                "Equipe Med9",
                notification.getPatientName(),
                notification.getDoctorName(),
                notification.getAppointmentDateTime(),
                notification.getNotes() != null ? notification.getNotes() : "Nenhuma observação"
            )
        );
        
        mailSender.send(message);
    }
} 