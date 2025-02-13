package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DuplicateKeyException;

import jakarta.validation.*;
import java.time.Instant;
import java.util.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/training-centers")
public class TrainingCenterController {

    private final TrainingCenterService trainingCenterService;

    // Constructor injection
    public TrainingCenterController(TrainingCenterService trainingCenterService) {
        this.trainingCenterService = trainingCenterService;
    }

    @PostMapping
    public ResponseEntity<?> createTrainingCenter(@Valid @RequestBody TrainingCenter trainingCenter) {
        trainingCenter.setCreatedOn(Instant.now().getEpochSecond()); // Set server timestamp
        try {
            // Save the training center to MongoDB
            TrainingCenter savedCenter = trainingCenterService.saveTrainingCenter(trainingCenter);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCenter);
        } catch (DuplicateKeyException e) {
            // Return a structured error response with a 400 status for duplicate
            ErrorResponse errorResponse = new ErrorResponse("Training center with the same center code already exists.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<TrainingCenter>> getAllTrainingCenters() {
        List<TrainingCenter> trainingCenters = trainingCenterService.getAllTrainingCenters();
        if (trainingCenters.isEmpty()) {
            return ResponseEntity.ok().body(trainingCenters); // Return empty list if no centers found
        }
        return ResponseEntity.ok().body(trainingCenters);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TrainingCenter>> filterTrainingCentersByCourse(@RequestParam String course) {
        List<TrainingCenter> filteredCenters = trainingCenterService.getTrainingCentersByCourse(course);
        if (filteredCenters.isEmpty()) {
            return ResponseEntity.ok().body(filteredCenters); // Return empty list if no matching centers found
        }
        return ResponseEntity.ok().body(filteredCenters);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
