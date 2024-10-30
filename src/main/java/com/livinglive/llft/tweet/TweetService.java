package com.livinglive.llft.tweet;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.livinglive.llft.role.Role;
import com.livinglive.llft.tweet.dto.CreateTweetDto;
import com.livinglive.llft.tweet.dto.FeedDto;
import com.livinglive.llft.tweet.dto.FeedItemDto;
import com.livinglive.llft.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TweetService {
    private final TweetRepository tweetRepository;

    private final UserRepository userRepository;

    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
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
        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(dto.content());
        tweet.setChallenge(null);
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
