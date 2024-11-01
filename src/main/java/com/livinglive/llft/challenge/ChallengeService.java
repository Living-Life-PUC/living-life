package com.livinglive.llft.challenge;

import java.util.HashSet;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.livinglive.llft.challenge.dto.CreateChallengeDto;
import com.livinglive.llft.challenge.dto.CreateParticipantDto;
import com.livinglive.llft.user.UserRepository;
import com.livinglive.llft.workout.WorkoutRepository;

import jakarta.transaction.Transactional;

@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    private final UserRepository userRepository;
    
    private final WorkoutRepository workoutRepository;

    public ChallengeService(ChallengeRepository challengeRepository, UserRepository userRepository, WorkoutRepository workoutRepository) {
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
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
        challenge.setWorkouts(new HashSet<>());
        challenge.setTweets(null);
        challenge.setParticipants(new HashSet<>());

        challengeRepository.save(challenge);
    }

    @Transactional
    public void joinChallenge(UUID userId, Long challengeId, CreateParticipantDto dto){
        var user = userRepository.findById(userId);
        var challenge = challengeRepository.findById(challengeId);
        var workouts = workoutRepository.findAllByUserUserId(userId);

        if (!user.isPresent() || !challenge.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        workouts.stream()
            .filter(workout -> dto.workoutIds().contains(workout.getId()))
            .forEach(workout -> challenge.get().getWorkouts().add(workout));

        challenge.get().getParticipants().add(user.get());
        challengeRepository.save(challenge.get());
    }
}