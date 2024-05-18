package com.projects.notas4int.bankservce.services;

import com.projects.notas4int.bankservce.DTOs.RequestClientDTO;

public interface ClientService {
    void saveClient(RequestClientDTO requestClientDTO);

    void saveClientInfo(String phone, String email);

    void updateClientInfo(String phone, String email);

    void removeClientInfo(boolean phone, boolean email);

    void transferMoneyByLogin(String login, double amountOfFunds);
}
