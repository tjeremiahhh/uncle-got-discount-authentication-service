package com.security.authenticationservice.user; 

import org.springframework.stereotype.Service;

import com.security.authenticationservice.authentication.AuthenticationRepository;

import lombok.RequiredArgsConstructor; 

@Service 
@RequiredArgsConstructor 
public class UserService { 
    private final AuthenticationRepository authenticationRepository; 

    public User getUserByEmail(String emailAddress) { 
        return authenticationRepository.getUserByEmail(emailAddress); 
    } 
} 
