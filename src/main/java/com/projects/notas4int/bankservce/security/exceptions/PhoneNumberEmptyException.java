package com.projects.notas4int.bankservce.security.exceptions;

public class PhoneNumberEmptyException extends RuntimeException {
    public PhoneNumberEmptyException(String phoneNumberEmptyMsg) {
        super(phoneNumberEmptyMsg);
    }
}
