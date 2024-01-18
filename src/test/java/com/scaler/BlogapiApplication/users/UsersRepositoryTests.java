package com.scaler.BlogapiApplication.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UsersRepositoryTests {
    @Autowired private UsersRepository usersRepository;

    @Test
    public void createUser(){
        UserEntity userEntity=UserEntity.builder()
                .username("akhil")
                .email("akhil@gmail.com")
                .password("akhil@123")
                .bio("akhil is created")
                .build();

        var user =usersRepository.save(userEntity);
        Assertions.assertNotNull(user.getId());
    }

    @Test
    public void findByUsername(){
        UserEntity userEntity1=UserEntity.builder()
                .username("akhil")
                .email("akhil@gmail.com")
                .password("akhil@123")
                .bio("akhil is created")
                .build();
        UserEntity userEntity2=UserEntity.builder()
                .username("akhil1")
                .email("akhil1@gmail.com")
                .password("akhil@123")
                .bio("akhil1 is created")
                .build();
        usersRepository.save(userEntity1);
        usersRepository.save(userEntity2);

        var user1=usersRepository.findByUsername("akhil");
        Assertions.assertEquals("akhil@gmail.com",user1.getEmail());
    }
}
