package com.projects.notas4int.bankservce.security.exceptions;

public class PhoneAlreadyExistsException extends RuntimeException {
    public PhoneAlreadyExistsException(String phoneNumberAlreadyExistsMsg) {
        super(phoneNumberAlreadyExistsMsg);
    }
}
