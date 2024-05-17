package com.projects.notas4int.bankservce.security.exceptions;

public class ClientExistsException extends RuntimeException {
    public ClientExistsException(String message) {
        super(message);
    }
}
