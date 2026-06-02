package com.taskmanager.taskassignment.repository;

import com.taskmanager.taskassignment.model.Priority;
import com.taskmanager.taskassignment.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task,String>
{
    List<Task> findByTitleContainingIgnoreCaseAndUsername(String keyword, String username);

    Page<Task> findByUsername(String username,Pageable pageable);
}
