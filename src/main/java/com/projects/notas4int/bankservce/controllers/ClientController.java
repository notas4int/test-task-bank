package com.projects.notas4int.bankservce.controllers;

import com.projects.notas4int.bankservce.security.exceptions.LastClientConnectedWayRemoveException;
import com.projects.notas4int.bankservce.security.exceptions.RequestParamNotFoundException;
import com.projects.notas4int.bankservce.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/add-info")
    public ResponseEntity<HttpStatus> addClientInfo(@RequestParam(required = false) String phone,
                                                    @RequestParam(required = false) String email) {
        checkRequestParam(phone, email);

        clientService.saveClientInfo(phone, email);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/update-info")
    public ResponseEntity<HttpStatus> updateClientInfo(@RequestParam(required = false) String phone,
                                                       @RequestParam(required = false) String email) {
        checkRequestParam(phone, email);

        clientService.updateClientInfo(phone, email);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete-info")
    public ResponseEntity<HttpStatus> deleteClientInfo(@RequestParam(required = false, defaultValue = "false") boolean phone,
                                                       @RequestParam(required = false, defaultValue = "false") boolean email) {
        if (!phone && !email)
            throw new RequestParamNotFoundException("Phone and email not entered");
        if (phone && email)
            throw new LastClientConnectedWayRemoveException("It is not possible to delete the last communication method. " +
                    "You must specify either a phone number or an e-mail address");

        clientService.removeClientInfo(phone, email);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/transfer-money")
    public ResponseEntity<HttpStatus> transferMoney(@RequestParam String login, @RequestParam double amountOfFunds) {
        clientService.transferMoneyByLogin(login, amountOfFunds);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private static void checkRequestParam(String phone, String email) {
        if (phone == null && email == null)
            throw new RequestParamNotFoundException("Phone and email not entered");
    }
}
