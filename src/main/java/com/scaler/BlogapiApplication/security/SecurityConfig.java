package com.scaler.BlogapiApplication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
    private final TokenService tokenService;

    SecurityConfig(TokenService tokenService){
        this.tokenService=tokenService;
    }
    @Autowired MvcRequestMatcher.Builder mvc;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(mvc.pattern(HttpMethod.GET,"/articles")).permitAll()
                .requestMatchers(mvc.pattern(HttpMethod.POST,"/users/signup")).permitAll()
                .requestMatchers(mvc.pattern(HttpMethod.POST,"/users/login")).permitAll()
                .requestMatchers(mvc.pattern("/h2-console/**")).permitAll()
                .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer :: disable);
        http.addFilterBefore(new JWTAuthenticationFilter(tokenService), AnonymousAuthenticationFilter.class);
        return http.build();
    }
}
