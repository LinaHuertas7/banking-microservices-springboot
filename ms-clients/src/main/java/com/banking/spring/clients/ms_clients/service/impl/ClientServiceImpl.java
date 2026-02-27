package com.banking.spring.clients.ms_clients.service.impl;

import com.banking.spring.clients.ms_clients.DTO.request.ClientRequestDTO;
import com.banking.spring.clients.ms_clients.DTO.request.ClientUpdateDTO;
import com.banking.spring.clients.ms_clients.DTO.response.ClientResponseDTO;
import com.banking.spring.clients.ms_clients.exception.ClientAlreadyExistsException;
import com.banking.spring.clients.ms_clients.exception.ClientNotFoundException;
import com.banking.spring.clients.ms_clients.mapper.ClientMapperInterface;
import com.banking.spring.clients.ms_clients.model.Client;
import com.banking.spring.clients.ms_clients.repository.ClientRepositoryInterface;
import com.banking.spring.clients.ms_clients.service.ClientServiceInterface;
import com.banking.spring.clients.ms_clients.service.PasswordServiceInterface;

import java.time.LocalDateTime;
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

    @Override
    @Transactional
    public ClientResponseDTO create(ClientRequestDTO request) {
        if (clientRepository.existsByIdentification(request.getIdentification())) {
            throw new ClientAlreadyExistsException(
                    "El cliente con la identificación %s ya existe".formatted(request.getIdentification()));
        }

        Client client = clientMapper.toEntity(request);
        client.setPassword(passwordService.encode(request.getPassword()));
        Client saved = clientRepository.save(client);

        log.info("El cliente fue creado exitosamente con id: {}", saved.getClientId());

        return clientMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDTO findById(Long id) {
        return clientMapper.toResponse(findClientById(id));
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
    public ClientResponseDTO update(Long id, ClientUpdateDTO request) {
        log.info("Actualizando cliente con id: {}", id);

        Client client = findClientById(id);
        clientMapper.updateEntityFromDto(request, client);

        if (hasNewPassword(request)) {
            client.setPassword(passwordService.encode(request.getPassword()));
        }

        return clientMapper.toResponse(clientRepository.save(client));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Client client = findClientById(id);
        client.setDeletedAt(LocalDateTime.now());

        client.setStatus(false);
        clientRepository.save(client);

        log.info("Cliente con id {} desactivado", id);
    }

    private Client findClientById(Long id) {
        return clientRepository.findActiveById(id)
                .orElseThrow(
                        () -> new ClientNotFoundException("No se encontro el cliente con el id %d".formatted(id)));
    }

    private boolean hasNewPassword(ClientUpdateDTO request) {
        return request.getPassword() != null && !request.getPassword().isBlank();
    }

}
