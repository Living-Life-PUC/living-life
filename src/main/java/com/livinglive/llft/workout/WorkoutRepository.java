package com.livinglive.llft.workout;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long>{
    Optional <Workout> findByName(String name);

    Set<Workout> findAllByUserUserId(UUID userId);

}
