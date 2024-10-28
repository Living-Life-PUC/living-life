package com.livinglive.llft.token;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.livinglive.llft.role.Role;
import com.livinglive.llft.token.dto.LoginRequest;
import com.livinglive.llft.token.dto.LoginResponse;
import com.livinglive.llft.user.OAuthUserService;
import com.livinglive.llft.user.UserService;

@Service
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private final UserService userService;
    private final OAuthUserService oAuthUserService;
    
    public TokenService(JwtEncoder jwtEncoder, UserService userService, OAuthUserService oAuthUserService) {
        this.jwtEncoder = jwtEncoder;
        this.userService = userService;
        this.oAuthUserService = oAuthUserService;
    }

    public LoginResponse generateToken(LoginRequest dto){
        var user = userService.findByName(dto.username());
        if (user.isEmpty() || !userService.isLoginCorrect(dto, user.get().getPassword())){
            throw new BadCredentialsException("user or password is invalid!");
        }
        var now = Instant.now();
        var expiresIn = 300L;
        
        var scopes = user.get().getRoles()
            .stream()
            .map(Role::getName)
            .collect(Collectors.joining());

        var claims = JwtClaimsSet.builder()
            .issuer("living-life")
            .subject(user.get().getUserId().toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiresIn))
            .claim("scope", scopes)
            .build();

        var jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwt, expiresIn);
    }

    public LoginResponse generateOAuthToken(LoginRequest dto){
        var user = oAuthUserService.findByName(dto.username());
        if (user.isEmpty()){
            throw new BadCredentialsException("user or password is invalid!");
        }
        var now = Instant.now();
        var expiresIn = 300L;
        
        var scopes = user.get().getRoles()
            .stream()
            .map(Role::getName)
            .collect(Collectors.joining());

        var claims = JwtClaimsSet.builder()
            .issuer("living-life")
            .subject(user.get().getUserId().toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiresIn))
            .claim("scope", scopes)
            .build();

        var jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwt, expiresIn);
    }
}
