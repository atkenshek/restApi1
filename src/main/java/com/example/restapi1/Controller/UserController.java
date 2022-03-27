package com.example.restapi1.Controller;

import com.example.restapi1.Entity.User;
import com.example.restapi1.Service.UserServiceImpl;
import com.example.restapi1.exception.ResourceNotFoundException;
import com.example.restapi1.exception.StatusFailedException;
import com.example.restapi1.exception.UserAlreadyExistsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.UnknownHostException;

@RestController
public class UserController {

    UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@RequestBody User user, HttpServletRequest request) throws UserAlreadyExistsException, JsonProcessingException, StatusFailedException {
          User newUser =  userService.saveUserNew(user);
          return ResponseEntity.ok(newUser);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value="id") Long userId) throws ResourceNotFoundException{
        final User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/users")
    public Iterable<User> getUsers(){
        return userService.getUsers();
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userDetails) throws ResourceNotFoundException {
        final User userNew = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok(userNew);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long userId) throws Exception{
        User newUser =  userService.deleteUser(userId);
        return ResponseEntity.ok(newUser);
    }
}


