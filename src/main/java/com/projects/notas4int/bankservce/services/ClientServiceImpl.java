package com.projects.notas4int.bankservce.services;

import com.projects.notas4int.bankservce.DTOs.RequestClientDTO;
import com.projects.notas4int.bankservce.mappers.BankAccountMapper;
import com.projects.notas4int.bankservce.mappers.ClientMapper;
import com.projects.notas4int.bankservce.models.BankAccount;
import com.projects.notas4int.bankservce.models.Client;
import com.projects.notas4int.bankservce.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final BankAccountMapper bankAccountMapper;
    private final ClientMapper clientMapper;

    @Transactional
    public void saveClient(RequestClientDTO requestClientDTO) {
        // TODO: 17.05.2024 проверка на существование аккаунта
        Client client = clientMapper.convertClientDTOtoClientModel(requestClientDTO);
        BankAccount bankAccount = bankAccountMapper.convertClientDTOtoBankAccountModel(requestClientDTO);
        client.setBankAccount(bankAccount);

        clientRepository.save(client);
    }
}
