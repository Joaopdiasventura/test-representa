package com.test_internship.server.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test_internship.server.common.MessageResponse;
import com.test_internship.server.domain.auth.AuthResponse;
import com.test_internship.server.domain.user.dto.CreateUserDto;
import com.test_internship.server.domain.user.dto.LoginUserDto;
import com.test_internship.server.domain.user.dto.UpdateUserDto;
import com.test_internship.server.domain.user.entities.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public AuthResponse create(@Valid @RequestBody CreateUserDto createUserDto) {
        return this.userService.create(createUserDto);
    }

    @PostMapping("login")
    public AuthResponse login(@Valid @RequestBody LoginUserDto loginUserDto) {
        return this.userService.login(loginUserDto);
    }

    @GetMapping("{token}")
    public User decodeToken(@PathVariable("token") String token) {
        return this.userService.decodeToken(token);
    }

    @PatchMapping("{id}")
    public MessageResponse update(@PathVariable("id") String id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        return this.userService.update(id, updateUserDto);
    }

    @DeleteMapping("{id}")
    public MessageResponse delete(@PathVariable("id") String id) {
        return this.userService.delete(id);
    }
}
