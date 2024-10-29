package com.livinglive.llft.workout;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.livinglive.llft.user.UserRepository;
import com.livinglive.llft.workout.dto.CreateWorkoutDto;

import jakarta.transaction.Transactional;

@Service
public class WorkoutService {
    private final UserRepository userRepository;
    

    private final WorkoutRepository workoutRepository;

    public WorkoutService(UserRepository userRepository,
            WorkoutRepository workoutRepository) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
    }

    @Transactional
    public void newWorkout(UUID userId, CreateWorkoutDto dto){
        var userFromDb = userRepository.findById(userId);
        if(!userFromDb.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Workout workout = new Workout();
        workout.setUser(userFromDb.get());
        workout.setName(dto.name());
        workout.setDescription(dto.description());
        workout.setDuration(dto.duration());
        workoutRepository.save(workout);
    }
}
