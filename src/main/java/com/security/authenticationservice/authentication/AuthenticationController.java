package com.security.authenticationservice.authentication;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.authenticationservice.user.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/getUserDetails")
    public User getUserDetails(String emailAddress) {
        return authenticationService.getUserDetails(emailAddress);
    }

    @GetMapping(value = "/getBusinessOwners", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getBusinessOwners() {
        return authenticationService.getBusinessOwners();
    }
}
