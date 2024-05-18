package com.projects.notas4int.bankservce.security.exceptions;

public class RequestParamNotFoundException extends RuntimeException {
    public RequestParamNotFoundException(String phoneAndEmailNotEntered) {
        super(phoneAndEmailNotEntered);
    }
}
