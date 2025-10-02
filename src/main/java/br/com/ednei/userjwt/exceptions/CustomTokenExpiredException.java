package br.com.ednei.userjwt.exceptions;

import com.auth0.jwt.exceptions.TokenExpiredException;

public class CustomTokenExpiredException extends RuntimeException {
    public CustomTokenExpiredException(TokenExpiredException e) {
        super("O Token expirou", e);
    }
}
