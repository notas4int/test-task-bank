package com.projects.notas4int.bankservce.security.exceptions;

public class EmailAddressAlreadyTakenException extends RuntimeException {
    public EmailAddressAlreadyTakenException(String s) {
        super(s);
    }
}
