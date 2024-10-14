package com.livinglive.llft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livinglive.llft.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
