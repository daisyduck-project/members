package com.bagusmwicaksono.daisyduckproject.members.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.bagusmwicaksono.daisyduckproject.members.controller.dto.CredentialsDto;
import com.bagusmwicaksono.daisyduckproject.members.exception.DuplicatedCredentialException;
import com.bagusmwicaksono.daisyduckproject.members.service.CredentialsService;
import com.bagusmwicaksono.daisyduckproject.members.utils.TestUtils;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(CredentialsController.class)
public class CredentialsControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CredentialsService credentialsService;

    @Test
    void testCreateCred_WhenSuccess_ShouldReturnValid() throws StreamReadException, DatabindException, BeansException, IOException {
        CredentialsDto credentialsDto = TestUtils.getCredentialDtoTestData();
        when(credentialsService.performCreateCredential(any())).thenReturn(Mono.just(credentialsDto));
        
        webTestClient.post().uri("/v1/creds")
            .bodyValue(credentialsDto)
            .exchange()
            .expectStatus().isOk()
            .expectBody(CredentialsDto.class);        
    }

    @Test
    void testCreateCred_WhenDuplicated_ShouldReturnError() throws StreamReadException, DatabindException, BeansException, IOException {
        CredentialsDto credentialsDto = TestUtils.getCredentialDtoTestData();
        when(credentialsService.performCreateCredential(any())).thenThrow(new DuplicatedCredentialException("dummy@email"));
        
        webTestClient.post().uri("/v1/creds")
            .bodyValue(credentialsDto)
            .exchange()
            .expectStatus().is4xxClientError();        
    }

    @Test
    void testGetAllCred_WhenSuccess_ShouldReturnValid() throws StreamReadException, DatabindException, BeansException, IOException {
        CredentialsDto credentialsDto = TestUtils.getCredentialDtoTestData();
        
        when(credentialsService.getAllCredentials()).thenReturn(Flux.just(credentialsDto));

        webTestClient.get().uri("/v1/creds")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(CredentialsDto.class);
    }
}
