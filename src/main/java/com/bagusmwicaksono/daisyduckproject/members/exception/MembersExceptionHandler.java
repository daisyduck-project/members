package com.bagusmwicaksono.daisyduckproject.members.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MembersExceptionHandler {
    @ExceptionHandler(DuplicatedCredentialException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleMemberDuplicateEmail(DuplicatedCredentialException ex){
        ProblemDetail problemDetail= ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Duplicated email");
        return problemDetail;
    }

    @ExceptionHandler(CredentialNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleCredentialNotFound(CredentialNotFoundException ex){
        ProblemDetail problemDetail= ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Credential Not Found");
        return problemDetail;
    }
}
