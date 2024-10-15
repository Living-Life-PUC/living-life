package com.livinglive.llft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livinglive.llft.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>{
    
}
