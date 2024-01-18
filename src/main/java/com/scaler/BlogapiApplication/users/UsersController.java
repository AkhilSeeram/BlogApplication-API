package com.scaler.BlogapiApplication.users;

import com.scaler.BlogapiApplication.security.TokenService;
import com.scaler.BlogapiApplication.users.dto.CreateUserRequestDto;
import com.scaler.BlogapiApplication.users.dto.LoginUserRequestDto;
import com.scaler.BlogapiApplication.users.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final ModelMapper modelMapper;
    private  final TokenService tokenService;
    public UsersController(UsersService usersService,@Autowired ModelMapper modelMapper,@Autowired TokenService tokenService){
        this.usersService=usersService;
        this.modelMapper=modelMapper;
        this.tokenService=tokenService;
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signup")
    ResponseEntity<UserResponseDto> signupUser(@RequestBody CreateUserRequestDto createUserRequestDto){
        var savedUser=usersService.createUser(createUserRequestDto.getUsername(), createUserRequestDto.getEmail(), createUserRequestDto.getPassword());
        var userResponse= modelMapper.map(savedUser,UserResponseDto.class);
        userResponse.setToken(tokenService.createToken(savedUser.getUsername()));
        return ResponseEntity.accepted().body(userResponse);
    }

    @PostMapping("/login")
    ResponseEntity<UserResponseDto> loginUser(@RequestBody LoginUserRequestDto loginUserRequestDto){
        var savedUser=usersService.loginUser(loginUserRequestDto.getUsername(),loginUserRequestDto.getPassword());
        var userResponse=modelMapper.map(savedUser,UserResponseDto.class);
        userResponse.setToken(tokenService.createToken(savedUser.getUsername()));
        return ResponseEntity.accepted().body(userResponse);
    }

    @GetMapping("/me")
    ResponseEntity<UserResponseDto> getCurrentUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=(String) authentication.getPrincipal();
        var savedUser=usersService.getUserByUsername(username);
        var userResponse=modelMapper.map(savedUser,UserResponseDto.class);
        return ResponseEntity.accepted().body(userResponse);
    }
    @GetMapping("/{id}")
    ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") UUID id ){
        var savedUser=usersService.getUserById(id);
        var userResponse=modelMapper.map(savedUser,UserResponseDto.class);
        return ResponseEntity.accepted().body(userResponse);
    }
    @GetMapping("")
    ResponseEntity<List<UserResponseDto>> getAllUsers(){
        List<UserEntity> users=usersService.getAllUsers();
        List<UserResponseDto> userResponses=new ArrayList<>();
        for(UserEntity user: users){
            userResponses.add(modelMapper.map(user,UserResponseDto.class));
        }
        return ResponseEntity.accepted().body(userResponses);
    }
    @PatchMapping("/{id}/following")
    ResponseEntity<String> addFollowing(@PathVariable("id") UUID id,
                                        @RequestParam(value = "following") List<String> following_users){
        usersService.addFollowing(id,
                following_users);
        return ResponseEntity.accepted().body("u are following them now");
    }
    @PatchMapping("/{id}/followers")
    ResponseEntity<String> addFollowers(@PathVariable("id") UUID id,
                                       @RequestParam(value = "followers") List<String> follower_usernames){
        usersService.addFollowers(id,follower_usernames);
        return ResponseEntity.accepted().body("ur followers got added");
    }
    @GetMapping("/{id}/followers")
    ResponseEntity<List<UserResponseDto>> getFollowers(@PathVariable("id") UUID id){
        List<UserEntity> followers=usersService.getFollowers(id);
        List<UserResponseDto> userResponseDtos=new ArrayList<>();
        for(UserEntity follower:followers){
            userResponseDtos.add(modelMapper.map(follower, UserResponseDto.class));
        }
        return ResponseEntity.accepted().body(userResponseDtos);
    }
}
