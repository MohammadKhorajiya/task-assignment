package com.taskmanager.taskassignment.controller;

import com.taskmanager.taskassignment.model.UserDTO;
import com.taskmanager.taskassignment.model.UserResponseDTO;
import com.taskmanager.taskassignment.service.AdminService;
import com.taskmanager.taskassignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/profile")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/my-profile")
    public ResponseEntity<UserResponseDTO> getMyProfile()
    {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.getUserProfile(currentUserName));
    }

    @PutMapping("/update-profile")
    public ResponseEntity<String> updateProfile(@RequestBody UserDTO userDto) {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateProfile(currentUsername,userDto);
        return ResponseEntity.ok("Profile updated successfully");
    }

    @DeleteMapping("/delete-profile")
    public ResponseEntity<String> deleteProfile()
    {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        adminService.deleteUser(currentUsername);
        return ResponseEntity.ok("Your account has been deleted successfully");
    }
}