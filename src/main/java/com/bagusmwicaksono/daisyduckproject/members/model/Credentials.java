package com.bagusmwicaksono.daisyduckproject.members.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "credentials")
public class Credentials {
    @Id
    private String id;
    private String email;
    private String username;
    private String password;
}
