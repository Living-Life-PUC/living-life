package com.livinglive.llft.score;

import java.time.LocalDate;

import com.livinglive.llft.challenge.Challenge;
import com.livinglive.llft.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_scores", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id","challenge_id", "score_type"})
})
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "score_id")
    private Long scoreId;

    @Enumerated(EnumType.STRING)
    @Column(name = "score_type", nullable = false)
    private ScoreType scoreType;

    @Column(name = "score_date", nullable = false)
    private LocalDate scoreDate;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;


    public Long getScoreId() {
        return scoreId;
    }


    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }


    public ScoreType getScoreType() {
        return scoreType;
    }


    public void setScoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
    }


    public LocalDate getScoreDate() {
        return scoreDate;
    }


    public void setScoreDate(LocalDate scoreDate) {
        this.scoreDate = scoreDate;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Challenge getChallenge() {
        return challenge;
    }


    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public enum ScoreType {
        COMMENT, REACTION, TWEET
    }
}