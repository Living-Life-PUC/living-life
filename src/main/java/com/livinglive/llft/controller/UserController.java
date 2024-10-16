package com.livinglive.llft.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.livinglive.llft.controller.dto.CreateUserDto;
import com.livinglive.llft.entities.Role;
import com.livinglive.llft.entities.User;
import com.livinglive.llft.repository.RoleRepository;
import com.livinglive.llft.repository.UserRepository;

import jakarta.transaction.Transactional;

@RestController
public class UserController {
    
    private final UserRepository userRepository;
    
    private final RoleRepository roleRepostory;
    
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepostory, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepostory = roleRepostory;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto dto){
        
        var basicRole = roleRepostory.findByName(Role.Values.BASIC.name());
        var userFromDb = userRepository.findByUsername(dto.username());
        if(userFromDb.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        //  user.setRoles(Set.of(basicRole));
        //userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
