package com.scaler.BlogapiApplication;

import com.scaler.BlogapiApplication.security.JWTService;
import com.scaler.BlogapiApplication.security.TokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@SpringBootApplication()
public class BlogapiApplication {

	public static void main(String[] args) {

		SpringApplication.run(BlogapiApplication.class, args);

	}
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
    @Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public TokenService tokenService(){
		return new JWTService();
	}


}
