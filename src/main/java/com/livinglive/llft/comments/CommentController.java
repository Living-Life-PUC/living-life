package com.livinglive.llft.comments;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.livinglive.llft.comments.dto.CreateCommentDto;

@Controller
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    @PostMapping("/tweets/{tweetId}/comments")
    public ResponseEntity<Void> createComment(@PathVariable Long tweetId, @RequestBody CreateCommentDto dto, JwtAuthenticationToken token){
        commentService.newComment(UUID.fromString(token.getName()), tweetId, dto);
        return ResponseEntity.ok().build();
    }
}
