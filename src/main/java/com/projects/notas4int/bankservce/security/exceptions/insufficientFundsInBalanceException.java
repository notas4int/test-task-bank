package com.projects.notas4int.bankservce.security.exceptions;

public class insufficientFundsInBalanceException extends RuntimeException {
    public insufficientFundsInBalanceException(String s) {
        super(s);
    }
}
