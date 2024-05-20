package com.projects.notas4int.bankservce.services.impls;

import com.projects.notas4int.bankservce.repositories.BankAccountRepository;
import com.projects.notas4int.bankservce.services.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void incDepAndUpdateAccount(String login, double depositBalance) {
        bankAccountRepository.updateAccountBalanceByLogin(depositBalance, login);
    }
}
