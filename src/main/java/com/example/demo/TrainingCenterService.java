package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Service
public class TrainingCenterService {

    private final TrainingCenterRepository trainingCenterRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public TrainingCenterService(TrainingCenterRepository trainingCenterRepository, MongoTemplate mongoTemplate) {
        this.trainingCenterRepository = trainingCenterRepository;
        this.mongoTemplate = mongoTemplate;
    }

    // Method to save a training center to MongoDB
    public TrainingCenter saveTrainingCenter(TrainingCenter trainingCenter) throws DuplicateKeyException {
        if (trainingCenterRepository.existsByCenterCode(trainingCenter.getCenterCode())) {
            throw new DuplicateKeyException("Training center with the same center code already exists.");
        }
        return trainingCenterRepository.save(trainingCenter);
    }

    // Method to get all training centers
    public List<TrainingCenter> getAllTrainingCenters() {
        return trainingCenterRepository.findAll();
    }

    // Method to filter by course
    public List<TrainingCenter> getTrainingCentersByCourse(String course) {
        // Using MongoTemplate to query based on coursesOffered containing the specified course as a substring
        Query query = new Query();
        query.addCriteria(Criteria.where("coursesOffered").regex(".*" + course + ".*", "i")); // "i" makes it case-insensitive
        return mongoTemplate.find(query, TrainingCenter.class);
    }
}
