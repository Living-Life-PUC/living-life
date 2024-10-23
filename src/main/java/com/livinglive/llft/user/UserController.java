package com.livinglive.llft.user;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.livinglive.llft.role.Role;
import com.livinglive.llft.role.RoleRepository;
import com.livinglive.llft.user.dto.CreateUserDto;

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
        user.setRoles(Set.of(basicRole));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> listUsers() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
