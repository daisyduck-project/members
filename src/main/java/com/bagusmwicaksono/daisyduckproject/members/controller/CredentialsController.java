package com.bagusmwicaksono.daisyduckproject.members.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bagusmwicaksono.daisyduckproject.members.controller.dto.CredentialsDto;
import com.bagusmwicaksono.daisyduckproject.members.service.CredentialsService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("v1/creds")
@Slf4j
public class CredentialsController {
    private final CredentialsService credentialsService;

    public CredentialsController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }
    
    @PostMapping("")
    public Mono<CredentialsDto> createCred(@RequestBody CredentialsDto credDto){
        log.info("[CredentialsController] createCred credDto="+credDto.toString());
        return credentialsService.performCreateCredential(credDto);
    }

    @GetMapping("")
    public Flux<CredentialsDto> getAllCred() {
        log.info("[CredentialsController] getAllCred");
        return credentialsService.getAllCredentials();
    }
    
}
