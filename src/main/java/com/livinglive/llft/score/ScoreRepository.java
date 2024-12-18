package com.livinglive.llft.score;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long>{
    Optional<Score> findByScoreTypeAndScoreDate(Score.ScoreType scoreType, LocalDate scoreDate);
}
