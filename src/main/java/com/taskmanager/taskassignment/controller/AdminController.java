package com.taskmanager.taskassignment.controller;

import com.taskmanager.taskassignment.model.User;
import com.taskmanager.taskassignment.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create-admin/{username}")
    public ResponseEntity<String> createAdmin(@PathVariable String username) {
        adminService.promoteToAdmin(username);
        return new ResponseEntity<>("User " + username + " is now an ADMIN", HttpStatus.OK);
    }

    @PostMapping("/refresh-cache")
    public ResponseEntity<String> refreshCache()
    {
        adminService.refreshAppCache();
        return new ResponseEntity<>("Cache refreshed", HttpStatus.OK);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers()
    {
        return new ResponseEntity<>(adminService.getAllUsers(),HttpStatus.OK);
    }

    @DeleteMapping("/delete-user/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        adminService.deleteUser(username);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}