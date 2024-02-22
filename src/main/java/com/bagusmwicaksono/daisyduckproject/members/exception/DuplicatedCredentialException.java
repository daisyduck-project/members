package com.bagusmwicaksono.daisyduckproject.members.exception;

public class DuplicatedCredentialException extends RuntimeException {
    public DuplicatedCredentialException(String email){
        super("Duplicate credential with email:"+email);
    }
}
