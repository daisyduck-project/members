package com.bagusmwicaksono.daisyduckproject.members.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bagusmwicaksono.daisyduckproject.members.controller.dto.CredentialsDto;
import com.bagusmwicaksono.daisyduckproject.members.exception.DuplicatedCredentialException;
import com.bagusmwicaksono.daisyduckproject.members.model.Credentials;
import com.bagusmwicaksono.daisyduckproject.members.repository.CredentialsRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CredentialsService {
    public CredentialsService(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    private final CredentialsRepository credentialsRepository;

    public Mono<CredentialsDto> performCreateCredential(CredentialsDto newCredential){
        log.info("[CredentialsService] performCreateCredential");
        return credentialsRepository.existsByEmail(newCredential.getEmail()).flatMap(result->{
            if(result){
                return Mono.error(new DuplicatedCredentialException(newCredential.getEmail()));
            }
            Credentials credentials = new Credentials();
            BeanUtils.copyProperties(newCredential, credentials);
            return credentialsRepository.save(credentials).map(saveCred ->{
                CredentialsDto credentialsDto = new CredentialsDto();
                BeanUtils.copyProperties(saveCred, credentialsDto);
                return credentialsDto;
            });
        });
    }
    public Flux<CredentialsDto> getAllCredentials(){
        log.info("[CredentialsService] getAllCredentials");
        return credentialsRepository.findAll().map(cred ->{
            CredentialsDto dto = new CredentialsDto();
            BeanUtils.copyProperties(cred, dto);
            return dto;
        });
    }
}
