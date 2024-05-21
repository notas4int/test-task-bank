package com.projects.notas4int.bankservce.security.exceptions;

public class InsufficientFundsInBalanceException extends RuntimeException {
    public InsufficientFundsInBalanceException(String s) {
        super(s);
    }
}
