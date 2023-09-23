package com.security.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authentication/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}
