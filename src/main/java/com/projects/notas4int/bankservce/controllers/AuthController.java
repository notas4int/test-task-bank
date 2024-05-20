package com.projects.notas4int.bankservce.controllers;

import com.projects.notas4int.bankservce.DTOs.RequestAuthenticationDTO;
import com.projects.notas4int.bankservce.DTOs.ResponseAuthenticationDTO;
import com.projects.notas4int.bankservce.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseAuthenticationDTO> authenticate(@RequestBody RequestAuthenticationDTO request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
