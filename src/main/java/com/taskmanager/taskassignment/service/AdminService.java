package com.taskmanager.taskassignment.service;

import com.taskmanager.taskassignment.model.User;
import com.taskmanager.taskassignment.repository.TaskRepository;
import com.taskmanager.taskassignment.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    public void promoteToAdmin(String username) {
        logger.debug("promoteToAdmin");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRoles().contains("ADMIN")) {
            user.getRoles().add("ADMIN");
            userRepository.save(user);
        }
    }

    public List<User> getAllUsers()
    {
        logger.debug("getAllUsers");
        return userRepository.findAll();
    }

    public void deleteUser(String username)
    {
        logger.debug("deleteUser");
        User user=userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
            taskRepository.deleteAll(user.getTaskEntries());
            userRepository.deleteByUsername(username);
    }
}