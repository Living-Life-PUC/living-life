package com.livinglive.llft.score.dto;

import java.time.LocalDateTime;

import com.livinglive.llft.score.Score.ScoreType;

public record CreateScoreDto(ScoreType scoreType, LocalDateTime scoreDate) {
}
