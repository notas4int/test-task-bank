package com.projects.notas4int.bankservce.services.impls;

import com.projects.notas4int.bankservce.DTOs.ResponseClientDTO;
import com.projects.notas4int.bankservce.mappers.ClientMapper;
import com.projects.notas4int.bankservce.models.Client;
import com.projects.notas4int.bankservce.repositories.SearchClientRepository;
import com.projects.notas4int.bankservce.security.exceptions.BankAccountNotFoundException;
import com.projects.notas4int.bankservce.services.SearchClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class SearchClientServiceImpl implements SearchClientService {
    private final SearchClientRepository searchClientRepository;
    private final ClientMapper clientMapper;

    @Override
    public List<ResponseClientDTO> findClientByDateOfBirthday(LocalDate date, int offset, int limit, String sort) {
        List<ResponseClientDTO> client = searchClientRepository
                .findByDateOfBirthdayGreaterThan(date, PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sort)))
                .map(clientMapper::convertClientToClientResponse)
                .toList();

        if (client.isEmpty())
            return Collections.emptyList();

        return client;
    }

    @Override
    public ResponseClientDTO findClientByPhone(String phone) {
        Client client = searchClientRepository.findClientByPhone(phone)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account with phone '"
                        + phone + "' not found"));
        log.info(client.toString());

        return clientMapper.convertClientToClientResponse(client);
    }

    @Override
    public ResponseClientDTO findClientByInitials(String initials) {
        String surname = initials.substring(0, initials.indexOf(" "));
        String name = initials.substring(initials.indexOf(" ") + 1, initials.lastIndexOf(" "));
        String middleName = initials.substring(initials.lastIndexOf(" ") + 1);

        Client client = searchClientRepository.findClientBySurnameAndNameAndMiddleName(surname, name, middleName)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account with initials '"
                        + initials + "' not found"));
        log.info(client.toString());

        return clientMapper.convertClientToClientResponse(client);
    }

    @Override
    public ResponseClientDTO findClientByEmail(String email) {
        Client client = searchClientRepository.findClientByEmail(email)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account with date of birthday '"
                        + email + "' not found"));
        log.info(client.toString());

        return clientMapper.convertClientToClientResponse(client);
    }
}
