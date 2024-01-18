package com.scaler.BlogapiApplication.users;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface UsersRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
}
