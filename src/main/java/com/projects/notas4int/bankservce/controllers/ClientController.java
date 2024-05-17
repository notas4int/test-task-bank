package com.projects.notas4int.bankservce.controllers;

import com.projects.notas4int.bankservce.DTOs.RequestClientDTO;
import com.projects.notas4int.bankservce.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/create-client")
    public ResponseEntity<HttpStatus> createClient(@RequestBody @Valid RequestClientDTO requestClientDTO) {
        clientService.saveClient(requestClientDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
