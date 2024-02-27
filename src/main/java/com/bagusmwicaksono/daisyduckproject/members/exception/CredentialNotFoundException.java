package com.bagusmwicaksono.daisyduckproject.members.exception;

public class CredentialNotFoundException extends RuntimeException{
    public CredentialNotFoundException(String email){
        super("Credential not found for email:"+email);
    }
}
