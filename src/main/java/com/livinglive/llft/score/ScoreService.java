package com.livinglive.llft.score;

import org.springframework.stereotype.Service;

import com.livinglive.llft.challenge.Challenge;
import com.livinglive.llft.score.dto.CreateScoreDto;
import com.livinglive.llft.user.User;

import jakarta.transaction.Transactional;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Transactional
    public void newScore(User user, Challenge challenge, CreateScoreDto dto){
        var scoreInDb = scoreRepository.findByScoreTypeAndScoreDate(dto.scoreType(), dto.scoreDate());
        if(scoreInDb.isPresent()){
            return;
        }
        Score score = new Score();
        score.setScoreType(dto.scoreType());
        score.setScoreDate(dto.scoreDate());
        score.setUser(user);
        score.setChallenge(challenge);
        scoreRepository.save(score);
    }
}
