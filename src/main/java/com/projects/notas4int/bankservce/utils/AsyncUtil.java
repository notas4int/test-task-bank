package com.projects.notas4int.bankservce.utils;

import com.projects.notas4int.bankservce.services.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AsyncUtil {
    private final BankAccountService bankAccountService;

    @Async
    public void incBalanceAfterDepositByLogin(String login, double depositBalance) throws InterruptedException {
        double limitBalance = depositBalance * 2.07;
        depositBalance += depositBalance * 0.05;

        while (depositBalance <= limitBalance) {
            Thread.sleep(60000);
            bankAccountService.incDepAndUpdateAccount(login, depositBalance);
            depositBalance += depositBalance * 0.05;
            log.info(depositBalance);
        }
    }
}
