package com.test_internship.server.domain.user.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.test_internship.server.domain.user.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {
    @Email(message = "Digite um email válido")
    protected String email;

    @Size(min = 1, message = "Digite um nome válido")
    protected String name;

    @Size(min = 1, message = "Digite uma senha válida")
    protected String password;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User toUser() {
        User user = new User();
        user.setEmail(this.email);
        user.setName(this.name);
        user.setPassword(this.password);
        return user;
    }

    public void hashPassword() {
        this.password = passwordEncoder.encode(this.password);
    }
}
