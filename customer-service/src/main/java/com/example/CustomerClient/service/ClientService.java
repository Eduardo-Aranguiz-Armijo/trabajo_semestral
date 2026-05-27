package com.example.CustomerClient.service;

import com.example.CustomerClient.dto.ClientRequestDTO;
import com.example.CustomerClient.dto.ClienteResponseDTO;
import com.example.CustomerClient.model.Client;
import com.example.CustomerClient.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Service
public class ClientService {
    @Autowired
private ClientRepository repository;

    public Client create(ClientRequestDTO request){

        Client client = new Client();
        //extrae le dto proviniente del otro microservicio
        client.setName(request.getNombre());
        client.setRut(request.getRut());
        client.setEmail(request.getCorreo());
        client.setAddress(request.getDireccion());
        client.setPhone(request.getTelefono());
        client.setUserId(request.getUserId());

        return repository.save(client);
    }
    //no teste findbyid
    public ClienteResponseDTO getByUserId(Long userId) {

        Client client =
                repository.findByUserId(userId)
                        .orElseThrow();

        ClienteResponseDTO dto =
                new ClienteResponseDTO();

        dto.setId(client.getIdCustomer());
        dto.setUserId(client.getUserId());
        dto.setNombre(client.getName());

        return dto;
    }
    public Client findById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("clien"));
    }
    //muestra exitosamente los clientes registrados desde el microservicio ms-users
    public List<Client> findAll() {
        return repository.findAll();

    }
    public Client update(ClientRequestDTO request, Long id){
        Client client = repository.findById(id).orElseThrow(() -> new RuntimeException("cliente no encontrado"));
        client.setName(request.getNombre());
        client.setRut(request.getRut());
        client.setEmail(request.getCorreo());
        client.setAddress(request.getDireccion());
        client.setPhone(request.getTelefono());
        client.setUserId(request.getUserId());
        client.setIdCustomer(id);
        return client;
    }
    public Client delete(Long id){
        Client client = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        repository.delete(client);
        return client;
    }

}
