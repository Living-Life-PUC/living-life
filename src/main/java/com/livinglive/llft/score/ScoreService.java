package com.livinglive.llft.score;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
        if(user == null || challenge == null){
            return;
        }
        LocalDateTime ldt = dto.scoreDate().atZone(ZoneOffset.UTC).toLocalDateTime();
        var scoreFromDb = scoreRepository.findByScoreTypeAndScoreDate(dto.scoreType(), ldt);
        if(scoreFromDb.isPresent()){
            return;
        }
        Score score = new Score();
        score.setScoreType(dto.scoreType());
        score.setScoreDate(ldt);
        score.setUser(user);
        score.setChallenge(challenge);
        scoreRepository.save(score);
    }
}
