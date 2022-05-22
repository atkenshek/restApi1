package com.example.restapi1.controller;

import com.example.restapi1.business.entity.User;
import com.example.restapi1.business.service.UserServiceImpl;
import com.example.restapi1.business.util.ImageUtility;
import com.example.restapi1.exception.ResourceNotFoundException;
import com.example.restapi1.exception.StatusFailedException;
import com.example.restapi1.exception.UserAlreadyExistsException;
import com.example.restapi1.repository.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class UserController {

    private static final String UPLOADED_PATH = "F://temp//";
    UserServiceImpl userService;
    UserRepo userRepo;

    @Autowired
    public UserController(UserServiceImpl userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @GetMapping("/api/user")
    public Iterable<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value="id") Long userId) throws ResourceNotFoundException{
        final User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }


    @RequestMapping(value = "/api/user", method = RequestMethod.POST, consumes="multipart/form-data")
    public ResponseEntity<User> saveUser(@RequestBody User user, HttpServletRequest request, @RequestParam("image") MultipartFile file)
        throws UserAlreadyExistsException, JsonProcessingException, StatusFailedException, IOException {


//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        user.setImageName(fileName);

//        String uploadDir = "user-photos/" + savedUser.getId();

        User newUser =  userService.saveUserWithCountry(user);
        userRepo.save(User.builder()
            .imageName(file.getOriginalFilename())
            .imageType(file.getContentType())
            .image(ImageUtility.compressImage(file.getBytes())).build());

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


