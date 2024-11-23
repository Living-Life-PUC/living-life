package com.livinglive.llft.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.livinglive.llft.token.TokenService;
import com.livinglive.llft.token.dto.LoginRequest;
import com.livinglive.llft.user.OAuthUserService;
import com.livinglive.llft.user.dto.CreateUserDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler{

    private final OAuthUserService oAuthUserService;
    private final TokenService tokenService;

    
    public CustomOAuth2SuccessHandler(@Lazy OAuthUserService oAuthUserService, @Lazy TokenService tokenService) {
        this.oAuthUserService = oAuthUserService;
        this.tokenService = tokenService;
    }


    public void onAuthenticationSuccess(
        HttpServletRequest request, 
        HttpServletResponse response,                
        Authentication authentication) throws IOException, ServletException {
    
        System.out.println("Login Successful");
        System.out.println( (DefaultOidcUser)(authentication.getPrincipal()) );

        var oAuthUser = authentication.getPrincipal();


        if ( oAuthUser instanceof DefaultOidcUser){
            DefaultOidcUser oidcUser = (DefaultOidcUser)oAuthUser;
            CreateUserDto dto = new CreateUserDto(
                oidcUser.getName(),
                oidcUser.getNickName(),
                "",
                oidcUser.getEmail(),
                oidcUser.getPicture()
            );

            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> tokenResponse = new HashMap<>();
            
            var user = oAuthUserService.findByName(dto.username());
            user.ifPresentOrElse(
                (u)->{         
                    LoginRequest loginRequest = new LoginRequest(u.getUsername(), u.getPassword());
                    var token = tokenService.generateOAuthToken(loginRequest);
                    tokenResponse.put("token", token.accessToken());
                    tokenResponse.put("expiresIn", Long.toString(token.expiresIn()));           
                },
                ()->{
                    oAuthUserService.newUser(dto);
                    LoginRequest loginRequest = new LoginRequest(dto.username(), dto.password());
                    var token = tokenService.generateOAuthToken(loginRequest);
                    tokenResponse.put("token", token.accessToken());
                    tokenResponse.put("expiresIn", Long.toString(token.expiresIn()));

                }
            );
            objectMapper.writeValue(response.getWriter(), tokenResponse);
        }
    }
}
