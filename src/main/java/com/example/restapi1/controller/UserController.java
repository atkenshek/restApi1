package com.example.restapi1.controller;

import com.example.restapi1.business.entity.User;
import com.example.restapi1.business.service.UserServiceImpl;
import com.example.restapi1.exception.ResourceNotFoundException;
import com.example.restapi1.exception.StatusFailedException;
import com.example.restapi1.exception.UserAlreadyExistsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/api//user")
    public Iterable<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/api//user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value="id") Long userId) throws ResourceNotFoundException{
        final User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/api/user")
    public ResponseEntity<User> saveUser(@RequestBody User user, HttpServletRequest request) throws UserAlreadyExistsException, JsonProcessingException, StatusFailedException {
        User newUser =  userService.saveUserWithCountry(user);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userDetails) throws ResourceNotFoundException {
        userService.updateUserById(userId, userDetails);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/user")
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long userId) throws Exception{
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

}


