package com.projects.notas4int.bankservce.services;

public interface BankAccountService {
    void incDepAndUpdateAccount(String login, double depositBalance);
}
