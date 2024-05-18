package com.projects.notas4int.bankservce.security.exceptions;

public class PhoneNumberAlreadyTakenException extends RuntimeException {
    public PhoneNumberAlreadyTakenException(String s) {
        super(s);
    }
}
