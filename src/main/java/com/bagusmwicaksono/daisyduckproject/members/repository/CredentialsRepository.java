package com.bagusmwicaksono.daisyduckproject.members.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bagusmwicaksono.daisyduckproject.members.model.Credentials;

import reactor.core.publisher.Mono;

public interface CredentialsRepository extends ReactiveMongoRepository<Credentials, String> {
    Mono<Boolean> existsByEmail(String email);
}
