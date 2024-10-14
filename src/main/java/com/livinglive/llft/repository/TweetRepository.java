package com.livinglive.llft.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livinglive.llft.entities.Role;
import com.livinglive.llft.entities.Tweet;
import com.livinglive.llft.entities.User;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>{
    
}
