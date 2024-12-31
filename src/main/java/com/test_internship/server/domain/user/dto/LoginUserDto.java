package com.test_internship.server.domain.user.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {

    @NotBlank(message = "Digite um email válido")
    @Email(message = "Digite um email válido")
    protected String email;

    @NotBlank(message = "Digite uma senha válida")
    protected String password;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean validatePassword(String hashedPassword) {
        return passwordEncoder.matches(this.password, hashedPassword);
    }
}
