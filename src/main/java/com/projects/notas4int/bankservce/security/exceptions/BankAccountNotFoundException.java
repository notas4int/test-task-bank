package com.projects.notas4int.bankservce.security.exceptions;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException(String s) {
        super(s);
    }
}
