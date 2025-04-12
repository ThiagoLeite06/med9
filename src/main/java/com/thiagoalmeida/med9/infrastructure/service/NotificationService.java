package com.thiagoalmeida.med9.infrastructure.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.thiagoalmeida.med9.application.dto.AppointmentNotification;
import com.thiagoalmeida.med9.infrastructure.config.RabbitMQConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final RabbitTemplate rabbitTemplate;
    
    public void sendAppointmentNotification(AppointmentNotification notification) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.APPOINTMENT_QUEUE, notification);
    }
} 