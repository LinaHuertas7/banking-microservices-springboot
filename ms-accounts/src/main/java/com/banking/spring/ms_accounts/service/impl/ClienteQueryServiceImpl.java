package com.banking.spring.ms_accounts.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.banking.spring.ms_accounts.DTO.request.ClientQueryRequestDTO;
import com.banking.spring.ms_accounts.DTO.response.ClientQueryResponseDTO;
import com.banking.spring.ms_accounts.exception.ClientInactiveException;
import com.banking.spring.ms_accounts.exception.ClientNotExistExeption;
import com.banking.spring.ms_accounts.exception.ClientServiceUnavailableException;
import com.banking.spring.ms_accounts.service.ClienteQueryServiceInterface;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteQueryServiceImpl implements ClienteQueryServiceInterface {

    private final RabbitTemplate rabbitTemplate;

    public ClientQueryResponseDTO validateClient(Long clientId) {

        ClientQueryRequestDTO request = new ClientQueryRequestDTO();
        request.setClientId(clientId);

        log.info("Enviando solicitud de validación para clientId: {}", clientId);

        ClientQueryResponseDTO response = (ClientQueryResponseDTO) rabbitTemplate.convertSendAndReceiveAsType(
                "client.query.request.queue",
                request,
                new ParameterizedTypeReference<ClientQueryResponseDTO>() {
                });

        if (response == null) {
            throw new ClientServiceUnavailableException(
                    "El servicio de clientes no está disponible");
        }

        if (!response.isExists()) {
            throw new ClientNotExistExeption(
                    "No se encontró el cliente con Id: %d".formatted(request.getClientId()));
        }

        if (!response.isActive()) {
            throw new ClientInactiveException(
                    "El cliente con el id: %d se encuentra innactivo".formatted(request.getClientId()));
        }

        return response;
    }
}
