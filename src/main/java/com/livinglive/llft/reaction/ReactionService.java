package com.livinglive.llft.reaction;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.livinglive.llft.reaction.dto.CreateReactionDto;
import com.livinglive.llft.tweet.TweetRepository;
import com.livinglive.llft.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ReactionService {
    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;


    public ReactionService(ReactionRepository reactionRepository, UserRepository userRepository,
            TweetRepository tweetRepository) {
        this.reactionRepository = reactionRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }


    @Transactional
    public void newReaction(Long tweetId, CreateReactionDto dto){
        var tweetFromDb = tweetRepository.findById(tweetId);
        var userFromDb = userRepository.findById(dto.userId());

        if(!userFromDb.isPresent() && !tweetFromDb.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Reaction reaction = new Reaction();
        reaction.setUser(userFromDb.get());
        reaction.setTweet(tweetFromDb.get());
        reaction.setReactionType(dto.reactionType());
        reactionRepository.save(reaction);
    }

    
}
