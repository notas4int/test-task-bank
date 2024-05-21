package com.projects.notas4int.bankservce.services;

import com.projects.notas4int.bankservce.DTOs.ResponseClientDTO;

import java.time.LocalDate;
import java.util.List;

public interface SearchClientService {
    List<ResponseClientDTO> findClientByDateOfBirthday(LocalDate date, int offset, int limit, String sort);
    ResponseClientDTO findClientByPhone(String phone);
    ResponseClientDTO findClientByInitials(String initials);
    ResponseClientDTO findClientByEmail(String email);
}
