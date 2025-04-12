package com.thiagoalmeida.med9.infrastructure.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.thiagoalmeida.med9.application.dto.AppointmentNotification;
import com.thiagoalmeida.med9.infrastructure.config.RabbitMQConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentNotificationConsumer {
    
    private final EmailService emailService;
    
    @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_QUEUE)
    public void receiveAppointmentNotification(AppointmentNotification notification) {
        try {
            log.info("Recebida notificação de agendamento: {}", notification);
            emailService.sendAppointmentNotificationEmail(notification);
            log.info("Email de notificação enviado com sucesso para: {}", notification.getPatientEmail());
        } catch (Exception e) {
            log.error("Erro ao processar notificação de agendamento: {}", e.getMessage(), e);
            // Aqui poderíamos implementar uma estratégia de retry ou dead letter queue
        }
    }
} 