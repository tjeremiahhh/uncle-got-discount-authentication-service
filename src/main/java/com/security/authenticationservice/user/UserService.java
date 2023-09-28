package com.security.authenticationservice.user; 

import org.springframework.stereotype.Service; 

import lombok.RequiredArgsConstructor; 

@Service 
@RequiredArgsConstructor 
public class UserService { 
    private final UserRepository userRepository; 

    public User getUserByEmail(String email) { 
        return userRepository.getUserByEmail(email); 
    } 
} 
