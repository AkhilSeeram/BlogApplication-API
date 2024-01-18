package com.scaler.BlogapiApplication.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTService implements TokenService{
    private final String SIGNING_KEY="signing key";
    private final long TOKEN_EXPIRY_MILLIS = 1000 * 60 * 60 * 24;
    private final Algorithm algorithm=Algorithm.HMAC256(SIGNING_KEY);
    @Override
    public String createToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer("blog-api")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+TOKEN_EXPIRY_MILLIS))
                .sign(algorithm);
    }

    @Override
    public String verifyToken(String token) throws IllegalStateException {
        var verifier=JWT.require(algorithm)
                .withIssuer("blog-api")
                .build();
        var decodedToken=verifier.verify(token);
        return decodedToken.getSubject();
    }
}
