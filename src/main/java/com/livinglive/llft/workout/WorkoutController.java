package com.livinglive.llft.workout;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.livinglive.llft.workout.dto.CreateWorkoutDto;

@Controller
public class WorkoutController {
    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }
    
    @PostMapping("/workouts")
    public ResponseEntity<Void> createWorkout(@RequestBody CreateWorkoutDto dto, JwtAuthenticationToken token){
        workoutService.newWorkout(UUID.fromString(token.getName()), dto);
        return ResponseEntity.ok().build();
    }

}
