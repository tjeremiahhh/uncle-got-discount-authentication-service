package com.security.user;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    public User getUserByEmail(String email) {
        return null;
    }
}
