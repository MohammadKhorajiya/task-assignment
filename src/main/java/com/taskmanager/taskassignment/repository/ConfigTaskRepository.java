package com.taskmanager.taskassignment.repository;

import com.taskmanager.taskassignment.model.ConfigTaskEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigTaskRepository extends MongoRepository<ConfigTaskEntity, String> {
}