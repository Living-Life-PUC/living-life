package com.livinglive.llft.comments;

import java.time.Instant;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.livinglive.llft.comments.dto.CreateCommentDto;
import com.livinglive.llft.score.ScoreService;
import com.livinglive.llft.score.Score.ScoreType;
import com.livinglive.llft.score.dto.CreateScoreDto;
import com.livinglive.llft.tweet.TweetRepository;
import com.livinglive.llft.user.UserRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final ScoreService scoreService;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository,
            TweetRepository tweetRepository, ScoreService scoreService) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.scoreService = scoreService;
    }

    public void newComment(UUID userId, Long tweetId, CreateCommentDto dto){
        var user = userRepository.findById(userId);
        var tweet = tweetRepository.findById(tweetId);

        if(!user.isPresent() || !tweet.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Comment comment = new Comment();
        comment.setContent(dto.content());
        comment.setUrl(dto.url());
        comment.setUser(user.get());
        comment.setTweet(tweet.get());

        CreateScoreDto scoreDto = new CreateScoreDto(ScoreType.COMMENT, Instant.now());
        scoreService.newScore(user.get(), tweet.get().getChallenge(), scoreDto);
        commentRepository.save(comment);
    }
}
