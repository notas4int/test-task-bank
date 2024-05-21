package com.projects.notas4int.bankservce.controllers;

import com.projects.notas4int.bankservce.DTOs.ResponseClientDTO;
import com.projects.notas4int.bankservce.security.exceptions.IncorrectEnteredRequestParamException;
import com.projects.notas4int.bankservce.services.SearchClientService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search-client/")
public class SearchClientController {
    private final SearchClientService searchClientService;

    @GetMapping("/get-client")
    public ResponseClientDTO getClient(@RequestParam(required = false) String phone,
                                              @RequestParam(required = false) String initials,
                                              @RequestParam(required = false) String email) {

        if (phone != null) {
            return searchClientService.findClientByPhone(phone);
        } else if (initials != null) {
            return searchClientService.findClientByInitials(initials);
        } else if (email != null) {
            return searchClientService.findClientByEmail(email);
        }

        throw new IncorrectEnteredRequestParamException("One search query parameter must be entered");
    }

    @GetMapping("/get-clients-by-birthdate")
    public List<ResponseClientDTO> getClientsByBirthdate(@RequestParam LocalDate dateOfBirthday,
                                                         @RequestParam(defaultValue = "0") @Min(0) int offset,
                                                         @RequestParam(defaultValue = "20") @Min(1) @Max(100) int limit,
                                                         @RequestParam(defaultValue = "id") String sort) {
        return searchClientService.findClientByDateOfBirthday(dateOfBirthday, offset, limit, sort);
    }

}

