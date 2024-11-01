package com.livinglive.llft.challenge;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.livinglive.llft.challenge.dto.CreateChallengeDto;

@Controller
public class ChallengeController {
    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping("/challenges")
    public ResponseEntity<Void> createChallenge(@RequestBody CreateChallengeDto dto, JwtAuthenticationToken token){
        challengeService.newChallenge(UUID.fromString(token.getName()), dto);
        return ResponseEntity.ok().build();
    }

}
