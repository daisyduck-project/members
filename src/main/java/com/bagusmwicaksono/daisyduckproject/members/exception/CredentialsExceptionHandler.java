package com.bagusmwicaksono.daisyduckproject.members.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CredentialsExceptionHandler {
    @ExceptionHandler(DuplicatedCredentialException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleMemberDuplicateEmail(DuplicatedCredentialException ex){
        ProblemDetail problemDetail= ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Duplicated email");
        return problemDetail;
    }
}
