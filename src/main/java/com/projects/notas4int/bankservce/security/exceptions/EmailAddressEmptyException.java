package com.projects.notas4int.bankservce.security.exceptions;

public class EmailAddressEmptyException extends RuntimeException {
    public EmailAddressEmptyException(String emailAddressEmptyMsg) {
        super(emailAddressEmptyMsg);
    }
}
