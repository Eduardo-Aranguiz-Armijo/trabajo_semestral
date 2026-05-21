package com.example.CustomerClient.controller;

import com.example.CustomerClient.dto.ClientRequestDTO;
import com.example.CustomerClient.dto.ClienteResponseDTO;
import com.example.CustomerClient.model.Client;
import com.example.CustomerClient.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class ClientController {
    @Autowired
    private ClientService service;
    //no se coloco alguna restriccion debido un usuario sin rol no podria registrarse, no confundan "user"
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClientRequestDTO request){
        service.create(request);
        return ResponseEntity.ok().build();
    }
    //solo administrador podra hacer operaciones secundarias
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Client> findAll() {
        return service.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Client findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ClienteResponseDTO>
    getByUserId(@PathVariable Long userId) {

        return ResponseEntity.ok(
                service.getByUserId(userId)
        );
    }
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> updateClient(@RequestBody ClientRequestDTO request, @PathVariable Long id){
        service.update(request,id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Client deletedById(@PathVariable Long id){ return service.delete(id);}

}
