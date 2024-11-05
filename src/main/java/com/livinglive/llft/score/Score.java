package com.livinglive.llft.score;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.livinglive.llft.challenge.Challenge;
import com.livinglive.llft.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_scores", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"tweet_id", "score_type"})
})
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "score_id")
    private Long scoreId;

    @Enumerated(EnumType.STRING)
    @Column(name = "score_type", nullable = false)
    private ScoreType scoreType;

    @CreationTimestamp
    private Instant scoreDate;
    
    @ManyToOne
    @Column(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @Column(name = "challenge_id", nullable = false)
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


    public Instant getScoreDate() {
        return scoreDate;
    }


    public void setScoreDate(Instant scoreDate) {
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
        COMMENT, SHARE, TWEET
    }
}