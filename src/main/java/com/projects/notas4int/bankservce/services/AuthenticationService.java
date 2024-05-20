package com.projects.notas4int.bankservce.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.notas4int.bankservce.DTOs.RequestAuthenticationDTO;
import com.projects.notas4int.bankservce.DTOs.RequestRegisterDTO;
import com.projects.notas4int.bankservce.DTOs.ResponseAuthenticationDTO;
import com.projects.notas4int.bankservce.models.BankAccount;
import com.projects.notas4int.bankservce.models.BankAccountDetails;
import com.projects.notas4int.bankservce.repositories.BankAccountRepository;
import com.projects.notas4int.bankservce.security.exceptions.BankAccountNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final BankAccountRepository bankAccountRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ClientService clientService;

    @Transactional
    public ResponseAuthenticationDTO register(RequestRegisterDTO request) {
        BankAccount account = clientService.saveClient(request);
        BankAccountDetails accountDetails = new BankAccountDetails(account);

        var jwtToken = jwtService.generateToken(accountDetails);
        var refreshToken = jwtService.generateRefreshToken(accountDetails);

        jwtService.saveClientToken(account, jwtToken);
        return ResponseAuthenticationDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public ResponseAuthenticationDTO authenticate(RequestAuthenticationDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()));

        var account = bankAccountRepository.findBankAccountByLogin(request.getLogin())
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account with login '" + request.getLogin()
                        + "' not found"));
        BankAccountDetails accountDetails = new BankAccountDetails(account);

        var jwtToken = jwtService.generateToken(accountDetails);
        var refreshToken = jwtService.generateRefreshToken(accountDetails);

        jwtService.revokeAllAccountTokens(account);
        jwtService.saveClientToken(account, jwtToken);

        return ResponseAuthenticationDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String login;

        if (authHeader == null ||!authHeader.startsWith("Bearer "))
            return;

        refreshToken = authHeader.substring(7);
        login = jwtService.extractUsername(refreshToken);

        if (login != null) {
            BankAccount account = bankAccountRepository.findBankAccountByLogin(login).orElseThrow(
                    () -> new BankAccountNotFoundException("Bank account with login '" + login
                    + "' not found"));
            BankAccountDetails accountDetails = new BankAccountDetails(account);

            if (jwtService.isTokenValid(refreshToken, accountDetails)) {
                var accessToken = jwtService.generateToken(accountDetails);
                jwtService.revokeAllAccountTokens(account);
                jwtService.saveClientToken(account, accessToken);

                var authResponse = ResponseAuthenticationDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
