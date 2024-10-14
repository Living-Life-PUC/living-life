package com.livinglive.llft.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livinglive.llft.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    
}
