package com.banking.spring.clients.ms_clients.service.impl;

import com.banking.spring.clients.ms_clients.DTO.event.ClientCreatedEventDTO;
import com.banking.spring.clients.ms_clients.DTO.request.ClientRequestDTO;
import com.banking.spring.clients.ms_clients.DTO.request.ClientUpdateDTO;
import com.banking.spring.clients.ms_clients.DTO.response.ClientResponseDTO;
import com.banking.spring.clients.ms_clients.exception.ClientAlreadyExistsException;
import com.banking.spring.clients.ms_clients.exception.ClientNotFoundException;
import com.banking.spring.clients.ms_clients.mapper.ClientMapperInterface;
import com.banking.spring.clients.ms_clients.model.Client;
import com.banking.spring.clients.ms_clients.publisher.ClientEventPublisher;
import com.banking.spring.clients.ms_clients.repository.ClientRepositoryInterface;
import com.banking.spring.clients.ms_clients.service.ClientServiceInterface;
import com.banking.spring.clients.ms_clients.service.PasswordServiceInterface;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientServiceInterface {

    private final ClientRepositoryInterface clientRepository;
    private final ClientMapperInterface clientMapper;
    private final PasswordServiceInterface passwordService;
    private final ClientEventPublisher clientEventPublisher;

    @Override
    @Transactional
    public ClientResponseDTO create(ClientRequestDTO request) {
        if (clientRepository.existsActiveByIdentification(request.getIdentification())) {
            throw new ClientAlreadyExistsException(
                    "El cliente con la identificación %s ya existe".formatted(request.getIdentification()));
        }

        Client client = clientMapper.toEntity(request);
        client.setPassword(passwordService.encode(request.getPassword()));
        Client saved = clientRepository.save(client);

        clientEventPublisher.publishClientCreated(
                new ClientCreatedEventDTO(saved.getClientId(), saved.getName(), saved.getIdentification()));

        log.info("El cliente fue creado exitosamente con slug: {}", saved.getSlug());

        return clientMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDTO findBySlug(String slug) {
        return clientMapper.toResponse(findClientBySlug(slug));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponseDTO> findAll() {
        List<ClientResponseDTO> clients = clientRepository.findAllActive()
                .stream()
                .map(clientMapper::toResponse)
                .toList();

        log.info("Clientes {} encontrados ", clients.size());

        return clients;
    }

    @Override
    @Transactional
    public ClientResponseDTO replace(String slug, ClientRequestDTO request) {
        Client client = findClientBySlug(slug);

        if (!client.getIdentification().equals(request.getIdentification()) &&
                clientRepository.existsActiveByIdentification(request.getIdentification())) {
            throw new ClientAlreadyExistsException(
                    "La identificación %s ya está en uso".formatted(request.getIdentification()));
        }

        clientMapper.replaceEntityFromDto(request, client);
        client.setPassword(passwordService.encode(request.getPassword()));

        return clientMapper.toResponse(clientRepository.save(client));
    }

    @Override
    @Transactional
    public ClientResponseDTO update(String slug, ClientUpdateDTO request) {
        log.info("Actualizando cliente con slug: {}", slug);

        Client client = findClientBySlug(slug);
        clientMapper.updateEntityFromDto(request, client);

        if (hasNewPassword(request)) {
            client.setPassword(passwordService.encode(request.getPassword()));
        }

        return clientMapper.toResponse(clientRepository.save(client));
    }

    @Override
    @Transactional
    public void delete(String slug) {
        Client client = findClientBySlug(slug);
        client.anonymize();
        clientRepository.save(client);

        log.info("Cliente con slug {} desactivado", slug);
    }

    private Client findClientBySlug(String slug) {
        return clientRepository.findActiveBySlug(slug).orElseThrow(
                () -> new ClientNotFoundException("No se encontro el cliente con el slug %s".formatted(slug)));
    }

    private boolean hasNewPassword(ClientUpdateDTO request) {
        return request.getPassword() != null && !request.getPassword().isBlank();
    }

}
