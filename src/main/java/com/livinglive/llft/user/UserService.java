package com.livinglive.llft.user;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.livinglive.llft.role.Role;
import com.livinglive.llft.role.RoleRepository;
import com.livinglive.llft.token.dto.LoginRequest;
import com.livinglive.llft.user.dto.CreateUserDto;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    
    private final RoleRepository roleRepostory;
    
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepostory,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepostory = roleRepostory;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void newUser(CreateUserDto dto){
        var basicRole = roleRepostory.findByName(Role.Values.BASIC.name());
        var userFromDb = userRepository.findByUsername(dto.username());
        if(userFromDb.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(Set.of(basicRole));
        user.setEmail(dto.email());
        user.setPicture(dto.picture());
        userRepository.save(user);
    }

    @Transactional
    public void newAdmin(CreateUserDto dto){
        
        var adminRole = roleRepostory.findByName(Role.Values.ADMIN.name());
        var userFromDb = userRepository.findByUsername(dto.username());
        if(userFromDb.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(Set.of(adminRole));
        user.setEmail(dto.email());
        user.setPicture(dto.picture());
        userRepository.save(user);
    }

    public Optional<User> findByName(String name) {
        var user = userRepository.findByUsername(name);
        return user;
    }

    public List<User> listUsers() {
        var users = userRepository.findAll();
        return users;
    }

    public boolean isLoginCorrect(LoginRequest loginRequest,  String password){
        return passwordEncoder.matches(loginRequest.password(), password);
    }
}
