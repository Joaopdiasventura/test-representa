package com.test_internship.server.domain.auth;

import com.test_internship.server.common.MessageResponse;
import com.test_internship.server.domain.user.entities.User;

public class AuthResponse extends MessageResponse {
    public String token;
    public User user;

    public AuthResponse(String message, String token, User user) {
        super(message);
        this.token = token;
        this.user = user;
    }
}
