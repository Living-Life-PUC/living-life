package com.livinglive.llft.challenge.dto;

import java.time.Instant;

public record CreateChallengeDto (String title, String description, Instant startDate, Instant endDate){
    
}
