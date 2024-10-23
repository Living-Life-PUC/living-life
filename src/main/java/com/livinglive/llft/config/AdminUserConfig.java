package com.livinglive.llft.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import com.livinglive.llft.user.UserService;
import com.livinglive.llft.user.dto.CreateUserDto;

@Configuration
public class AdminUserConfig implements CommandLineRunner{
    private final UserService userService;

    public AdminUserConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception{

        var userAdmin = userService.findByName("admin");
        userAdmin.ifPresentOrElse(
            user -> System.out.println("admin ja existe"), 
            ()-> {
                var dto = new CreateUserDto("admin", "123", "admin@gmail.com", "picture.com");

                userService.newAdmin(dto);
            }
            
        );
    }
}
