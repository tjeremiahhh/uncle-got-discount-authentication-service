package com.security.authenticationservice.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.authenticationservice.config.JwtService;
import com.security.authenticationservice.user.User;
import com.security.authenticationservice.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
            .name(request.getName())
            .emailAddress(request.getEmailAddress())
            .password(passwordEncoder.encode(request.getPassword()))
            .isBusinessOwner(false)
            .build();
        
        //save to db
        userRepository.registerUser(request);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        
        var user = userRepository.findByEmail(request.getEmail());

        if(user == null) {
            //throw exception
        }

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public String getUserDetails(String email) {
        return userRepository.getUserDetails(email);
    }
}
