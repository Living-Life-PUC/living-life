package com.livinglive.llft.tweet.dto;

import jakarta.annotation.Nullable;

public record CreateTweetDto(String content, @Nullable Long challengeId, @Nullable Long workoutId) {
    
}
