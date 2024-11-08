package com.livinglive.llft.score.dto;

import java.time.Instant;

import com.livinglive.llft.score.Score.ScoreType;

public record CreateScoreDto(ScoreType scoreType, Instant scoreDate) {
}
