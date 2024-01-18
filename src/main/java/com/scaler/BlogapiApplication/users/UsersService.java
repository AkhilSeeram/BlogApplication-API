package com.scaler.BlogapiApplication.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    // we can write @Autowired in constructor. it is optional.
    public UsersService(UsersRepository usersRepository, @Autowired PasswordEncoder passwordEncoder){
        this.usersRepository=usersRepository;
        this.passwordEncoder=passwordEncoder;
    }
    public UserEntity createUser(String name,String password,String email){
        var savedUser=usersRepository.save(UserEntity.builder()
                        .username(name)
                        .password(passwordEncoder.encode(password))
                        .email(email)
                        .build());

        return savedUser;
    }
    public UserEntity loginUser(String name,String password){
        var savedUser=usersRepository.findByUsername(name);
        if(savedUser != null){
            if(passwordEncoder.matches(password,savedUser.getPassword())) return savedUser;
        }
        throw new IllegalArgumentException("Invalid username or password");
    }
    public UserEntity  getUserByUsername(String username){
        var savedUser=usersRepository.findByUsername(username);
        return savedUser;
    }
    public UserEntity getUserById(UUID id){
        var savedUser=usersRepository.findById(id);
        return savedUser.orElseThrow(()-> new UsernameNotFoundException("user not found- enter correct details"));
    }
    public List<UserEntity> getAllUsers(){
        List<UserEntity> users=usersRepository.findAll();
        return users;
    }
    public void addFollowing(UUID id,List<String> following_usersname){
        List<UserEntity> following_users =new ArrayList<>();
        for(String following_username:following_usersname){
            following_users.add(usersRepository.findByUsername(following_username));
        }
        UserEntity user=getUserById(id);
        user.setFollowing(following_users);
        usersRepository.save(user);
    }
    public void addFollowers(UUID id,List<String> followers_usernames){
        List<UserEntity> followers_users =new ArrayList<>();
        for(String followers_username:followers_usernames){
            followers_users.add(usersRepository.findByUsername(followers_username));
        }
        UserEntity user=getUserById(id);
        user.setFollowers(followers_users);
        usersRepository.save(user);
    }
    public List<UserEntity> getFollowers(UUID id){
        UserEntity user=getUserById(id);
        List<UserEntity> followers=user.getFollowers();
        return followers;
    }

}
