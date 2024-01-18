package com.scaler.BlogapiApplication.security;

public interface TokenService {
    public String createToken(String username);
    public String verifyToken(String token);

}
