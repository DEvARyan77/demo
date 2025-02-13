package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrainingCenterRepository extends MongoRepository<TrainingCenter, String> {
    // You can add custom query methods here if needed
    boolean existsByCenterCode(String centerCode);
}
