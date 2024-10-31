package com.livinglive.llft.user;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.livinglive.llft.role.Role;
import com.livinglive.llft.role.RoleRepository;
import com.livinglive.llft.user.dto.CreateUserDto;
import com.livinglive.llft.workout.Workout;

import jakarta.transaction.Transactional;

@Service
public class OAuthUserService {
    
    private final UserRepository userRepository;
    
    private final RoleRepository roleRepostory;
    
    private final BCryptPasswordEncoder passwordEncoder;

    public OAuthUserService(UserRepository userRepository, RoleRepository roleRepostory,
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
        user.setWorkouts(new HashSet<Workout>());

        userRepository.save(user);
    }


    public Optional<User> findByName(String name) {
        var user = userRepository.findByUsername(name);
        return user;
    }
}
