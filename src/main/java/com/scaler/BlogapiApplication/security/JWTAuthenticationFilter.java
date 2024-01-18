package com.scaler.BlogapiApplication.security;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

public class JWTAuthenticationFilter extends AuthenticationFilter {

    public JWTAuthenticationFilter(TokenService tokenService) {
        super(new JWTAuthenticationManger(tokenService), new JWTAuthenticationConverter());
        this.setSuccessHandler((request, response, authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        });
    }
    static class JWTAuthenticationConverter implements AuthenticationConverter{

        @Override
        public Authentication convert(HttpServletRequest request) {
            if(request.getHeader("Authorization") !=null){
                String token=request.getHeader("Authorization").replace("Bearer ","");
                return new UserAuthentication(token);
            }
            return null;
        }
    }
    static class JWTAuthenticationManger implements AuthenticationManager{
        private final TokenService tokenService;
        JWTAuthenticationManger(TokenService tokenService){
            this.tokenService=tokenService;
        }
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            var userAuthentication = (UserAuthentication) authentication;
            var username=tokenService.verifyToken(userAuthentication.getCredentials());
            if (username!=null){
                userAuthentication.setUser(username);
            }
            return userAuthentication;
        }
    }

}
