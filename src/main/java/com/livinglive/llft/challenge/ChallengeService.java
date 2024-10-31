package com.livinglive.llft.challenge;

import org.springframework.stereotype.Service;

@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }
}
