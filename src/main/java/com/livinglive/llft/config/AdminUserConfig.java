package com.livinglive.llft.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.livinglive.llft.entities.Role;
import com.livinglive.llft.entities.User;
import com.livinglive.llft.repository.RoleRepository;
import com.livinglive.llft.repository.UserRepository;

@Configuration
public class AdminUserConfig implements CommandLineRunner{
    private RoleRepository roleRepository;

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception{
        System.out.println("roleAdmin: " + Role.Values.ADMIN.name().toLowerCase());

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name().toLowerCase());
        var userAdmin = userRepository.findByUsername("admin");
        userAdmin.ifPresentOrElse(
            user -> System.out.println("admin ja existe"), 
            ()-> {
                var user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("123"));
                user.setRoles(Set.of(roleAdmin));
                userRepository.save(user);
            }
            
        );
    }
}