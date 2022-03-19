package com.example.restapi1.Controller;

import com.example.restapi1.Entity.User;
import com.example.restapi1.Service.UserServiceImpl;
import com.example.restapi1.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Iterable<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/save")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value="id") Long userId) throws ResourceNotFoundException{
        final User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/users/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userDetails) throws ResourceNotFoundException {
        final User userNew = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok(userNew);
    }

    @DeleteMapping("/users/delete/{id}")
    public User deleteUser(@PathVariable(value = "id") Long userId) throws Exception{
        return userService.deleteUser(userId);
    }

}
