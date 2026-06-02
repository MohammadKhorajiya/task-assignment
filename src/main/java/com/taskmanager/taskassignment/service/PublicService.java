package com.taskmanager.taskassignment.service;

import com.taskmanager.taskassignment.model.User;
import com.taskmanager.taskassignment.repository.UserRepository;
import com.taskmanager.taskassignment.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PublicService
{
    private static final Logger logger = LoggerFactory.getLogger(PublicService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;

    public String signup(User user)
    {
        logger.info("Attempting to signup for username: {}", user.getUsername());
        return userService.saveNewUser(user);
    }

    public String login(User user)
    {
        logger.info("Attempting to login for username: {}", user.getUsername());
        User existingUser=userRepository.findByUsername(user.getUsername()).orElse(null);

        if(existingUser!=null && passwordEncoder.matches(user.getPassword(),existingUser.getPassword()))
        {
            return jwtUtils.generateToken(existingUser.getUsername());
        }

        return "Invalid Username or Password";
    }
}
