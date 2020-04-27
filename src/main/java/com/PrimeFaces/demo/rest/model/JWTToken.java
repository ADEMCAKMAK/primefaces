package com.PrimeFaces.demo.rest.model;

import com.PrimeFaces.demo.security.jwt.JWTConfigurer;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object to return as body in JWT Authentication.
 */
public class JWTToken {

    private final String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @JsonProperty(JWTConfigurer.AUTHORIZATION_HEADER)
    public String getToken() {
        return token;
    }
}
