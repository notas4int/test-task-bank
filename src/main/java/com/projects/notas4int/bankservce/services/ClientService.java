package com.projects.notas4int.bankservce.services;

import com.projects.notas4int.bankservce.DTOs.RequestRegisterDTO;
import com.projects.notas4int.bankservce.models.BankAccount;
import org.springframework.security.core.userdetails.UserDetails;

public interface ClientService {
    BankAccount saveClient(RequestRegisterDTO requestClientDTO);

    void saveClientInfo(String phone, String email, UserDetails userDetails);

    void updateClientInfo(String phone, String email, UserDetails userDetails);

    void removeClientInfo(boolean phone, boolean email, UserDetails userDetails);

    void transferMoneyByLogin(String login, double amountOfFunds, UserDetails userDetails);
}
