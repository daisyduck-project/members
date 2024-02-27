package com.bagusmwicaksono.daisyduckproject.members.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.bagusmwicaksono.daisyduckproject.members.model.Credentials;
import com.bagusmwicaksono.daisyduckproject.members.utils.TestUtils;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CredentialsRepositoryTest {
    @Autowired
    CredentialsRepository credentialsRepository;

    Credentials credentials;

    @BeforeAll
    void setup() throws StreamReadException, DatabindException, IOException{
        credentials = TestUtils.getCredentialTestData();        
    }

    @AfterAll
    void cleanup(){
        credentialsRepository.delete(credentials);
    }

    @Test
    void testExistsByEmail_whenNoEmailExist_returnFalse() {
        Mono<Boolean> isCredentialExist = credentialsRepository.save(credentials).then(credentialsRepository.existsByEmail("fake"));
        StepVerifier.create(isCredentialExist).consumeNextWith(isExist ->{
            assertFalse(isExist);
        }).verifyComplete();
    }

    @Test
    void testExistsByEmail_whenEmailExist_returnTrue() {
        Mono<Boolean> isCredentialExist = credentialsRepository.save(credentials).then(credentialsRepository.existsByEmail(credentials.getEmail()));        
        StepVerifier.create(isCredentialExist).consumeNextWith(isExist ->{
            assertTrue(isExist);
        }).verifyComplete();
    }

    @Test
    void findByEmailAndPassword_whenCredentialExist_returnValue() {
        Mono<Credentials> cred = credentialsRepository.save(credentials).then(credentialsRepository.findByEmailAndPassword(credentials.getEmail(), credentials.getPassword()));
        StepVerifier.create(cred).consumeNextWith(foundCred ->{
            assertNotNull(foundCred);
            assertEquals(foundCred.getId(), credentials.getId());
            assertEquals(foundCred.getEmail(), credentials.getEmail());
        }).verifyComplete();
    }

    @Test
    void findByEmailAndPassword_whenNotFound_emmitNothing() {
        Mono<Credentials> cred = credentialsRepository.save(credentials).then(credentialsRepository.findByEmailAndPassword("fake", "fake"));
        StepVerifier.create(cred).expectNextCount(0).verifyComplete();
    }
}
