package com.bagusmwicaksono.daisyduckproject.members.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bagusmwicaksono.daisyduckproject.members.controller.dto.CredentialsDto;
import com.bagusmwicaksono.daisyduckproject.members.exception.CredentialNotFoundException;
import com.bagusmwicaksono.daisyduckproject.members.exception.DuplicatedCredentialException;
import com.bagusmwicaksono.daisyduckproject.members.model.Credentials;
import com.bagusmwicaksono.daisyduckproject.members.repository.CredentialsRepository;
import com.bagusmwicaksono.daisyduckproject.members.utils.TestUtils;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class CredentialsServiceTest {
    @InjectMocks
    CredentialsService credentialsService;

    @Mock
    CredentialsRepository credentialsRepository;
    

    @Test
    void getAllCredentials_whenSuccess_returnAll() throws StreamReadException, DatabindException, IOException {
        Credentials credentials = TestUtils.getCredentialTestData();
        when(credentialsRepository.findAll()).thenReturn(Flux.just(credentials));

        Flux<CredentialsDto> allCredentials = credentialsService.getAllCredentials();

        StepVerifier.create(allCredentials).expectNextCount(1)
            .verifyComplete();
    }

    @Test
    void testPerformCreateCredential_whenSuccess_returnValidResponse() throws StreamReadException, DatabindException, IOException {
        Credentials credentials = TestUtils.getCredentialTestData();

        when(credentialsRepository.existsByEmail(anyString())).thenReturn(Mono.just(false));
        when(credentialsRepository.save(any())).thenReturn(Mono.just(credentials));

        CredentialsDto newCredentialsDto = new CredentialsDto();
        BeanUtils.copyProperties(credentials, newCredentialsDto);
        Mono<CredentialsDto> resultDto = credentialsService.performCreateCredential(newCredentialsDto);

        StepVerifier.create(resultDto).consumeNextWith(newCred -> {
            assertEquals(newCred.getId(), credentials.getId());
            assertEquals(newCred.getEmail(), credentials.getEmail());
            assertEquals(newCred.getUsername(), credentials.getUsername());
        }).verifyComplete();
    }
    @Test
    void testPerformCreateCredential_whenDuplicateEmail_returnDuplicateError() throws StreamReadException, DatabindException, IOException {
        Credentials credentials = TestUtils.getCredentialTestData();

        when(credentialsRepository.existsByEmail(anyString())).thenReturn(Mono.just(true));

        CredentialsDto newCredentialsDto = new CredentialsDto();
        BeanUtils.copyProperties(credentials, newCredentialsDto);
        Mono<CredentialsDto> resultDto = credentialsService.performCreateCredential(newCredentialsDto);

        StepVerifier.create(resultDto)
            .expectErrorMatches(throwable -> throwable instanceof DuplicatedCredentialException)
            .verify();
    }
    @Test
    void performLogin_whenSuccess_returnValidResponse() throws StreamReadException, DatabindException, IOException{
        Credentials credentials = TestUtils.getCredentialTestData();

        when(credentialsRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(Mono.just(credentials));

        CredentialsDto cred = new CredentialsDto();
        BeanUtils.copyProperties(credentials, cred);

        Mono<CredentialsDto> resultDto = credentialsService.performLogin(cred);

        StepVerifier.create(resultDto).consumeNextWith(foundCred ->{
            assertEquals(foundCred.getEmail(), credentials.getEmail());
            assertEquals(foundCred.getPassword(), credentials.getPassword());
            assertEquals(foundCred.getId(), credentials.getId());
        }).verifyComplete();

    }

    @Test
    void performLogin_whenNotFOund_returnNotfoundError() throws StreamReadException, DatabindException, IOException{
        Credentials credentials = TestUtils.getCredentialTestData();

        when(credentialsRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(Mono.empty());

        CredentialsDto cred = new CredentialsDto();
        BeanUtils.copyProperties(credentials, cred);

        Mono<CredentialsDto> resultDto = credentialsService.performLogin(cred);

        StepVerifier.create(resultDto)
            .expectError()
            .verify();
    }
}
