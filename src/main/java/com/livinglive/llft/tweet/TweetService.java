package com.livinglive.llft.tweet;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.livinglive.llft.challenge.Challenge;
import com.livinglive.llft.challenge.ChallengeRepository;
import com.livinglive.llft.role.Role;
import com.livinglive.llft.score.Score.ScoreType;
import com.livinglive.llft.score.ScoreService;
import com.livinglive.llft.score.dto.CreateScoreDto;
import com.livinglive.llft.tweet.dto.CreateTweetDto;
import com.livinglive.llft.tweet.dto.FeedDto;
import com.livinglive.llft.tweet.dto.FeedItemDto;
import com.livinglive.llft.user.UserRepository;
import com.livinglive.llft.workout.Workout;
import com.livinglive.llft.workout.WorkoutRepository;

import jakarta.transaction.Transactional;

@Service
public class TweetService {
    private final TweetRepository tweetRepository;

    private final UserRepository userRepository;

    private final ChallengeRepository challengeRepository;

    private final WorkoutRepository workoutRepository;

    private final ScoreService scoreService;
    
    public TweetService(TweetRepository tweetRepository, UserRepository userRepository,
            ChallengeRepository challengeRepository, WorkoutRepository workoutRepository, ScoreService scoreService) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.challengeRepository = challengeRepository;
        this.workoutRepository = workoutRepository;
        this.scoreService = scoreService;
    }

    public FeedDto listFeed(int page, int pageSize){
        var tweets = tweetRepository.findAll(PageRequest.of(page, pageSize, Direction.DESC, "creationTimestamp"))
        .map(tweet -> 
            new FeedItemDto(
                tweet.getTweetId(), 
                tweet.getContent(), 
                tweet.getUser().getUsername()));
        return new FeedDto(tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements());
    }

    @Transactional
    public void newTweet(UUID userId, CreateTweetDto dto){
        var user = userRepository.findById(userId);
        if(!user.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(dto.content());

        if(dto.challengeId() != null){
            Optional<Challenge> challenge = challengeRepository.findById(dto.challengeId());
            if(challenge.isPresent() && challenge.get().getParticipants().contains(user.get())){
                tweet.setChallenge(challenge.get());
                challenge.get().getTweets().add(tweet); // updates bidirectional relationship: tweet and challenge
            }
        }
        if(dto.workoutId() != null){
            Optional<Workout> workout = workoutRepository.findById(dto.workoutId());
            if(workout.isPresent() 
            && tweet.getChallenge() != null
            && tweet.getChallenge().getWorkouts().contains(workout.get())){
                tweet.setWorkout(workout.get());
            }
        }
        CreateScoreDto scoreDto = new CreateScoreDto(ScoreType.TWEET, Instant.now());
        scoreService.newScore(tweet.getUser(), tweet.getChallenge(), scoreDto);
        tweetRepository.save(tweet);
    }
    
    public  void removeTweet(UUID userId, Long tweetId){
        var user = userRepository.findById(userId);
        var tweet = tweetRepository.findById(tweetId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var isAdmin = user.get().getRoles()
            .stream()
            .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if(isAdmin || tweet.getUser().getUserId().equals(userId)){
            tweetRepository.deleteById(tweetId);
        }
    }
}
