package com.banking.spring.ms_accounts.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.banking.spring.ms_accounts.DTO.event.ClientCreatedEventDTO;
import com.banking.spring.ms_accounts.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClientEventListener {
    @RabbitListener(queues = RabbitMQConfig.CLIENT_CREATED_QUEUE)
    public void handleClientCreated(ClientCreatedEventDTO event) {
        log.info("Received ClientCreatedEvent for clientId: {}", event);
    }
}