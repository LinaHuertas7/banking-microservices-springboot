package com.banking.spring.clients.ms_clients.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.banking.spring.clients.ms_clients.DTO.event.ClientCreatedEventDTO;
import com.banking.spring.clients.ms_clients.config.RabbitMQConfig;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClientEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishClientCreated(ClientCreatedEventDTO event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.CLIENT_CREATED_KEY,
                event);
    }
}