package com.taskmanager.taskassignment.service;

import com.taskmanager.taskassignment.model.Task;
import com.taskmanager.taskassignment.model.User;
import com.taskmanager.taskassignment.model.UserDTO;
import com.taskmanager.taskassignment.model.UserResponseDTO;
import com.taskmanager.taskassignment.repository.TaskRepository;
import com.taskmanager.taskassignment.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
@Transactional
public class UserService
{

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AdminService adminService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO getUserProfile(String username)
    {
        logger.debug("getUserProfile");
        User user =userRepository.findByUsername(username).orElse(null);
        if (user == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return new UserResponseDTO(user);
    }

    public String saveNewUser(User user)
    {
        logger.debug("Saving new user");
        if(userRepository.findByUsername(user.getUsername()).isPresent())
        {
            return "Username already exists";
        }
        else if (user.getPassword() == null || user.getPassword().isEmpty())
        {
            return "Password cannot be null or empty";
        }
        else if (user.getPassword().length()<6)
        {
            return  "Password should be at least 6 characters";
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return "Signup Successfully!";
        }
    }

    public void saveUser(User user)
    {
        logger.debug("Saving user");
        userRepository.save(user);
    }

    public User findByUsername(String username)
    {
        logger.debug("Finding user by username {}", username);
        return userRepository.findByUsername(username).orElse(null);
    }

    public void updateProfile(String oldUsername, UserDTO userDto) {
        logger.debug("Updating user profile");
        User user = findByUsername(oldUsername);

        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        boolean usernameChanged=false;
        String newUsername = userDto.getUsername();

        if (newUsername != null && !newUsername.isEmpty() && !newUsername.equals(oldUsername)) {
            if (userRepository.findByUsername(newUsername).isPresent()) {
                throw new RuntimeException("Username already exists");
            }
            user.setUsername(newUsername);
            usernameChanged=true;
        }

        if (userDto.getEmail() != null && !userDto.getEmail().isEmpty())
        {
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            if (userDto.getPassword().length() < 6) {
                throw new RuntimeException("Password should be at least 6 characters");
            }
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(user);

        if (usernameChanged)
        {
            for (Task task:user.getTaskEntries())
            {
                task.setUsername(newUsername);
                taskRepository.save(task);
            }
        }
    }

    public void deleteAccount(String username)
    {
        logger.warn("Deleting account for username:{}", username);
        User user=findByUsername(username);
        if (user != null) {
            taskRepository.deleteAll(user.getTaskEntries());
            userRepository.deleteByUsername(username);
        }
            else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}