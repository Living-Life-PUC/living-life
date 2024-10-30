package com.livinglive.llft.challenge;

import org.springframework.stereotype.Controller;

@Controller
public class ChallengeController {
    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    
}
