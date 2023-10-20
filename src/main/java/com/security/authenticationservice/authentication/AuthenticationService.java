package com.security.authenticationservice.authentication;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.security.authenticationservice.config.JwtService;
import com.security.authenticationservice.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationRepository authenticationRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
            .name(request.getName())
            .emailAddress(request.getEmailAddress())
            .password(passwordEncoder.encode(request.getPassword()))
            .phoneNumber(request.getPhoneNumber())
            .isBusinessOwner(false)
            .build();
        
        //save to db
        authenticationRepository.registerUser(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmailAddress(), request.getPassword()));
        
        var user = authenticationRepository.getUserByEmail(request.getEmailAddress());

        if(user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter a valid email address");
        }

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public User getUserDetails(String emailAddress) {
        return authenticationRepository.getUserDetails(emailAddress);
    }

    public List<User> getBusinessOwners() {
        return authenticationRepository.getBusinessOwners();
    }
}
