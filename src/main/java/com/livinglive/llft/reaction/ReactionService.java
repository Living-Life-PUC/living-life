package com.livinglive.llft.reaction;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.livinglive.llft.reaction.dto.CreateReactionDto;
import com.livinglive.llft.score.ScoreService;
import com.livinglive.llft.score.Score.ScoreType;
import com.livinglive.llft.score.dto.CreateScoreDto;
import com.livinglive.llft.tweet.TweetRepository;
import com.livinglive.llft.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ReactionService {
    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final ScoreService scoreService;

    public ReactionService(ReactionRepository reactionRepository, UserRepository userRepository,
            TweetRepository tweetRepository, ScoreService scoreService) {
        this.reactionRepository = reactionRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.scoreService = scoreService;
    }


    @Transactional
    public void newReaction(Long tweetId, UUID userId, CreateReactionDto dto){
        var tweetFromDb = tweetRepository.findById(tweetId);
        var userFromDb = userRepository.findById(userId);
        if(!userFromDb.isPresent() || !tweetFromDb.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Reaction reaction = new Reaction();
        reaction.setUser(userFromDb.get());
        reaction.setTweet(tweetFromDb.get());
        reaction.setReactionType(dto.reactionType());
        
        CreateScoreDto scoreDto = new CreateScoreDto(ScoreType.REACTION, reaction.getCreationTimestamp());
        scoreService.newScore(userFromDb.get(), tweetFromDb.get().getChallenge(), scoreDto);
        reactionRepository.save(reaction);
    }

    
}
