package com.example.CustomerClient.service;

import com.example.CustomerClient.dto.ClientRequestDTO;
import com.example.CustomerClient.dto.ClientResponseDTO;
import com.example.CustomerClient.exception.ClientAlreadyExistsException;
import com.example.CustomerClient.exception.ClientNotFoundException;
import com.example.CustomerClient.model.Client;
import com.example.CustomerClient.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Client create(ClientRequestDTO request) {

        validateClient(request);
        Client client = buildClient(request);
        return repository.save(client);
    }

    public ClientResponseDTO getByUserId(Long userId) {

        Client client = repository.findByUserId(userId).orElseThrow(
                () -> new ClientNotFoundException("Client not found"));

        return mapResponse(client);
    }

    public Client findById(Long id) {

        return repository.findById(id).orElseThrow(
                () -> new ClientNotFoundException("Client not found"));
    }

    public List<Client> findAll() {

        return repository.findAll();
    }

    public Client update(ClientRequestDTO request, Long id) {

        Client client = findById(id);

        validateUpdate(request, id);

        updateFields(client, request);

        return repository.save(client);
    }

    public Client delete(Long id) {

        Client client = findById(id);

        repository.delete(client);

        return client;
    }

    // PRIVATE METHODS

    private void validateClient(ClientRequestDTO request) {

        validateEmail(request.getEmail());

        validateRut(request.getRut());

        validatePhone(request.getPhone());
    }

    private void validateUpdate(ClientRequestDTO request,Long id) {

        repository.findByEmail(request.getEmail()).ifPresent(client -> {

            if (!client.getIdCustomer().equals(id)) {

                throw new ClientAlreadyExistsException("Correo already exists");
            }
        });

        repository.findByRut(request.getRut()).ifPresent(client -> {

            if (!client.getIdCustomer().equals(id)) {

                throw new ClientAlreadyExistsException("Rut already exists");
            }
        });

        repository.findByPhone(request.getPhone()).ifPresent(client -> {

            if (!client.getIdCustomer().equals(id)) {

                throw new ClientAlreadyExistsException("Telefono already exists");
            }
        });
    }

    private void validateEmail(String correo) {

        repository.findByEmail(correo).ifPresent(client -> {

            throw new ClientAlreadyExistsException("Correo already exists");
        });
    }

    private void validateRut(String rut) {

        repository.findByRut(rut).ifPresent(client -> {

            throw new ClientAlreadyExistsException("Rut already exists");
                });
    }

    private void validatePhone(String phone) {

        repository.findByPhone(phone.replace(" ","")).ifPresent(client -> {

            throw new ClientAlreadyExistsException("Telefono already exists");
                });
    }

    private Client buildClient(ClientRequestDTO request) {

        Client client = new Client();

        updateFields(client, request);

        return client;
    }

    private void updateFields(Client client, ClientRequestDTO request
    ) {

        client.setName(request.getName());

        client.setRut(request.getRut());

        client.setEmail(request.getEmail());

        client.setAddress(request.getAddress());

        String phone = request.getPhone().replace(" ","");

        client.setPhone(phone);

        client.setUserId(request.getUserId());
    }

    private ClientResponseDTO mapResponse(Client client) {

        ClientResponseDTO dto = new ClientResponseDTO();

        dto.setId(client.getIdCustomer());

        dto.setUserId(client.getUserId());

        dto.setName(client.getName());

        return dto;
    }
}