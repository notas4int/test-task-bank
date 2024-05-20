package com.projects.notas4int.bankservce.controllers;

import com.projects.notas4int.bankservce.DTOs.RequestRegisterDTO;
import com.projects.notas4int.bankservce.DTOs.ResponseAuthenticationDTO;
import com.projects.notas4int.bankservce.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
public class StaffClientController {
    private final AuthenticationService authService;

    @PostMapping("/create-client")
    public ResponseEntity<ResponseAuthenticationDTO> createClient(@RequestBody @Valid RequestRegisterDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
