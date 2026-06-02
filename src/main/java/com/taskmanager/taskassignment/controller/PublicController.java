package com.taskmanager.taskassignment.controller;

import com.taskmanager.taskassignment.model.User;
import com.taskmanager.taskassignment.service.PublicService;
import com.taskmanager.taskassignment.service.UserService;
import com.taskmanager.taskassignment.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController
{
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PublicService publicService;

    @PostMapping("/signup")
    public String signup(@RequestBody User user)
    {
        return publicService.signup(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user)
    {
        return publicService.login(user);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck()
    {
        return ResponseEntity.ok("Task Assignment Server is Up and Running");
    }
}
