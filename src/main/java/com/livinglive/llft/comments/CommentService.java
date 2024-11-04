package com.livinglive.llft.comments;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.livinglive.llft.comments.dto.CreateCommentDto;
import com.livinglive.llft.tweet.TweetRepository;
import com.livinglive.llft.user.UserRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository,
            TweetRepository tweetRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    public void newComment(UUID userId, Long tweetId, CreateCommentDto dto){
        var user = userRepository.findById(userId);
        var tweet = tweetRepository.findById(tweetId);

        if(!user.isPresent() && !tweet.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Comment comment = new Comment();
        comment.setContent(dto.content());
        comment.setUrl(dto.url());
        comment.setUser(user.get());
        comment.setTweet(tweet.get());
        commentRepository.save(comment);
    }
}
