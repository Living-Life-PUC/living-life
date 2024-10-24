package com.livinglive.llft.token;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.livinglive.llft.token.dto.LoginRequest;
import com.livinglive.llft.token.dto.LoginResponse;

@RestController
public class TokenController {
    private final TokenService tokenService;
    
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = tokenService.generateToken(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
