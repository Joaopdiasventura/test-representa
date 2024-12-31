package com.test_internship.server.domain.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.test_internship.server.common.MessageResponse;
import com.test_internship.server.domain.auth.AuthResponse;
import com.test_internship.server.domain.auth.JwtService;
import com.test_internship.server.domain.user.dto.CreateUserDto;
import com.test_internship.server.domain.user.dto.LoginUserDto;
import com.test_internship.server.domain.user.dto.UpdateUserDto;
import com.test_internship.server.domain.user.entities.User;
import com.test_internship.server.domain.user.repositories.MongoUserRepository;

@Service
public class UserService {

    @Autowired
    private MongoUserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public AuthResponse create(CreateUserDto createUserDto) {
        this.throwIfEmailIsUsed(createUserDto.getEmail());
        createUserDto.hashPassword();

        User user = this.userRepository.save(createUserDto.toUser());

        String token = this.jwtService.generateToken(user.getId());

        user.removePassword();

        return new AuthResponse("Usuário criado com sucesso", token, user);
    }

    public AuthResponse login(LoginUserDto loginUserDto) {
        User user = this.findByEmail(loginUserDto.getEmail());
        boolean isPasswordValid = loginUserDto.validatePassword(user.getPassword());

        if (!isPasswordValid)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha inválida");

        String token = this.jwtService.generateToken(user.getId());

        user.removePassword();

        return new AuthResponse("Login realizado com sucesso", token, user);
    }

    public User decodeToken(String token) {
        String id = this.jwtService.decodeToken(token);
        User user = this.findById(id);
        user.removePassword();
        return user;
    }

    public MessageResponse update(String id, UpdateUserDto updateUserDto) {
        User user = this.findById(id);

        if (updateUserDto.getEmail() != null && updateUserDto.getEmail() != user.getEmail()) {
            this.throwIfEmailIsUsed(updateUserDto.getEmail());
            user.setEmail(updateUserDto.getEmail());
        }

        if (updateUserDto.getPassword() != null && updateUserDto.getPassword() != user.getPassword()) {
            updateUserDto.hashPassword();
            user.setPassword(updateUserDto.getPassword());
        }

        if (updateUserDto.getName() != null)
            user.setName(updateUserDto.getName());

        this.userRepository.save(user);

        return new MessageResponse("Usuário atualizado com sucesso");
    }

    public MessageResponse delete(String id) {
        this.findById(id);

        this.userRepository.deleteById(id);

        return new MessageResponse("Usuário deletado com sucesso");
    }

    public User findById(String id) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        return user.get();
    }

    private User findByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        return user;
    }

    private void throwIfEmailIsUsed(String email) {
        User user = this.userRepository.findByEmail(email);
        if (user != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esse email já está sendo utilizado");
    }
}
