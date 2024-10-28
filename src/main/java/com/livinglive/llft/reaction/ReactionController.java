package com.livinglive.llft.reaction;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.livinglive.llft.reaction.dto.CreateReactionDto;

@RestController
public class ReactionController {

    private final ReactionService reactionService;
    
    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @PostMapping("/tweets/{tweetId}/reactions")
        public ResponseEntity<Void> createReaction(@RequestBody CreateReactionDto dto, JwtAuthenticationToken token){
        
        return ResponseEntity.ok().build();
    }
}
