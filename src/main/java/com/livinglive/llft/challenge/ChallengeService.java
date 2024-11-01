package com.livinglive.llft.challenge;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.livinglive.llft.challenge.dto.CreateChallengeDto;
import com.livinglive.llft.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    private final UserRepository userRepository;
    
    
    public ChallengeService(ChallengeRepository challengeRepository, UserRepository userRepository) {
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public void newChallenge(UUID userId, CreateChallengeDto dto){
        var admin = userRepository.findById(userId);
        if (!admin.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        Challenge challenge = new Challenge();
        challenge.setTitle(dto.title());
        challenge.setDescription(dto.description());
        challenge.setAdmin(admin.get());
        challenge.setStartDate(dto.startDate());
        challenge.setEndDate(dto.endDate());
        challenge.setWorkouts(null);
        challenge.setTweets(null);
        challenge.setParticipants(null);

        challengeRepository.save(challenge);
    }
}
