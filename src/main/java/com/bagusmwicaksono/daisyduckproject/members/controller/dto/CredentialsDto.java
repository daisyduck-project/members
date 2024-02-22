package com.bagusmwicaksono.daisyduckproject.members.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDto {
    private String id;
    private String email;
    private String username;
    private String password;
}
