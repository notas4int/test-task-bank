package com.projects.notas4int.bankservce.security.exceptions;

public class OldAndNewEmailIdenticalException extends RuntimeException {
    public OldAndNewEmailIdenticalException(String s) {
        super(s);
    }
}
