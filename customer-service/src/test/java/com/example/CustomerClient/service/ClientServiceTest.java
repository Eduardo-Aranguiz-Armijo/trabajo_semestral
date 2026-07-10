package com.example.CustomerClient.service;

import com.example.CustomerClient.dto.ClientRequestDTO;
import com.example.CustomerClient.dto.ClienteResponseDTO;
import com.example.CustomerClient.model.Client;
import com.example.CustomerClient.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientService service;
    @Test
    void debeCrearCliente() {

        ClientRequestDTO request = new ClientRequestDTO();
        request.setNombre("Juan");
        request.setRut("123");
        request.setCorreo("test@mail.com");
        request.setDireccion("Santiago");
        request.setTelefono("999");
        request.setUserId(1L);

        Client client = new Client();
        client.setIdCustomer(10L);
        client.setName("Juan");

        when(repository.save(any(Client.class)))
                .thenReturn(client);

        Client result = service.create(request);

        assertNotNull(result);
        assertEquals("Juan", result.getName());

        verify(repository).save(any(Client.class));
    }

    @Test
    void debeObtenerPorUserId() {

        Client client = new Client();
        client.setIdCustomer(1L);
        client.setUserId(10L);
        client.setName("Juan");

        when(repository.findByUserId(10L))
                .thenReturn(Optional.of(client));

        ClienteResponseDTO result = service.getByUserId(10L);

        assertEquals(1L, result.getId());
        assertEquals(10L, result.getUserId());
        assertEquals("Juan", result.getNombre());

        verify(repository).findByUserId(10L);
    }
    @Test
    void debeBuscarPorId() {

        Client client = new Client();
        client.setIdCustomer(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(client));

        Client result = service.findById(1L);

        assertEquals(1L, result.getIdCustomer());

        verify(repository).findById(1L);
    }

    @Test
    void debeListarClientes() {

        List<Client> list = List.of(new Client(), new Client());

        when(repository.findAll()).thenReturn(list);

        List<Client> result = service.findAll();

        assertEquals(2, result.size());

        verify(repository).findAll();
    }
    @Test
    void debeActualizarCliente() {

        ClientRequestDTO request = new ClientRequestDTO();
        request.setNombre("Nuevo");
        request.setRut("999");
        request.setCorreo("nuevo@mail.com");
        request.setDireccion("Valpo");
        request.setTelefono("111");
        request.setUserId(2L);

        Client existing = new Client();
        existing.setIdCustomer(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(existing));

        Client result = service.update(request, 1L);

        assertEquals("Nuevo", result.getName());
        assertEquals("999", result.getRut());

        verify(repository).findById(1L);
    }
}