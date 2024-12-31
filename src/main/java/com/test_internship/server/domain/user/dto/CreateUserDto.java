package com.test_internship.server.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import com.test_internship.server.domain.user.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
public class CreateUserDto {

    @NotBlank(message = "Digite um email válido")
    @Email(message = "Digite um email válido")
    protected String email;

    @NotBlank(message = "Digite um nome válido")
    protected String name;

    @NotBlank(message = "Digite uma senha válida")
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
