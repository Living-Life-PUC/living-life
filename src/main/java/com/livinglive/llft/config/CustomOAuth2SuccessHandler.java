package com.livinglive.llft.config;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.livinglive.llft.user.OAuthUserService;
import com.livinglive.llft.user.dto.CreateUserDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler{

    private final OAuthUserService oAuthUserService;

    public CustomOAuth2SuccessHandler(@Lazy OAuthUserService oAuthUserService) {
        this.oAuthUserService = oAuthUserService;
    }

    public void onAuthenticationSuccess(
        HttpServletRequest request, 
        HttpServletResponse response,                
        Authentication authentication) throws IOException, ServletException {
    
        System.out.println("Login Successful");
        System.out.println( (DefaultOidcUser)(authentication.getPrincipal()) );

        var oAuthUser = authentication.getPrincipal();
        if ( oAuthUser instanceof DefaultOidcUser){
            CreateUserDto dto = new CreateUserDto(
                ((DefaultOidcUser)oAuthUser).getEmail(),
                "",
                ((DefaultOidcUser)oAuthUser).getEmail(),
                ((DefaultOidcUser)oAuthUser).getPicture()
            );
            oAuthUserService.newUser(dto);
        }
    }
}
