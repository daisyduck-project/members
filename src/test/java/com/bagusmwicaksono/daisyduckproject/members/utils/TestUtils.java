package com.bagusmwicaksono.daisyduckproject.members.utils;

import java.io.IOException;
import java.io.InputStream;

import com.bagusmwicaksono.daisyduckproject.members.model.Credentials;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    public static Credentials getCredentialTestData() throws StreamReadException, DatabindException, IOException{
        InputStream inJson = Credentials.class.getResourceAsStream("/Credentials.json");
        return new ObjectMapper().readValue(inJson, Credentials.class);
    }
}
