package com.banking.spring.clients.ms_clients.listener;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.banking.spring.clients.ms_clients.DTO.request.ClientQueryRequestDTO;
import com.banking.spring.clients.ms_clients.DTO.response.ClientQueryResponseDTO;
import com.banking.spring.clients.ms_clients.mapper.ClientQueryMapperInterface;
import com.banking.spring.clients.ms_clients.model.Client;
import com.banking.spring.clients.ms_clients.repository.ClientRepositoryInterface;
import org.springframework.amqp.rabbit.annotation.Queue;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ClientQueryListener {

    private final ClientRepositoryInterface clientRepository;
    private final ClientQueryMapperInterface clientQueryMapper;

    @RabbitListener(queuesToDeclare = @Queue(name = "client.query.request.queue", durable = "true"))
    public ClientQueryResponseDTO handleValidationRequest(ClientQueryRequestDTO request) {
        Optional<Client> client = clientRepository.findActiveById(request.getClientId());

        log.info("Se recibió una solicitud de validación para clientId: {}, existe: {}, activo: {}",
                request.getClientId(),
                client.isPresent(),
                client.isPresent() && client.get().getStatus());

        ClientQueryResponseDTO response = client
                .map(c -> clientQueryMapper.toResponse(c, request))
                .orElseGet(() -> clientQueryMapper.toNotFoundResponse(request));

        log.info("Enviando respuesta de validación para clientId: {}", response);

        return response;
    }
}