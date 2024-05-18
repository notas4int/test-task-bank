package com.projects.notas4int.bankservce.security.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String emailAddressAlreadyExistsMsg) {
        super(emailAddressAlreadyExistsMsg);
    }
}
