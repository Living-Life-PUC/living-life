package com.livinglive.llft.comments;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    
}
