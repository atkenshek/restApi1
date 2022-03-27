package com.example.restapi1.Service;

import com.example.restapi1.Entity.User;
import com.example.restapi1.Repository.UserRepo;
import com.example.restapi1.exception.ResourceNotFoundException;
import com.example.restapi1.exception.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Slf4j
public class UserServiceImpl implements UserServiceInterface{

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {
        User userNew = new User();
        if (!userRepo.existsByEmail(user.getEmail())) {
            log.info("Saving new user {} to the database", user.getFullName());
            userNew = userRepo.save(user);
        } else {
            log.warn("User with email {} already exists in database", user.getEmail());
            throw new UserAlreadyExistsException("User with email: " + user.getEmail() + " already exists in database");
        }
        return userNew;
    }

    @Override
    public Iterable<User> getUsers() {
        log.info("Selecting all users");
        return userRepo.findAll();
    }

    @Override
    public User updateUser(Long id, User userDetails) throws ResourceNotFoundException {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for: " + id));
        user.setFullName(userDetails.getFullName());
        user.setGender(userDetails.getGender());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        return userRepo.save(user);
    }

    @Override
    public User getUserById(Long id) throws ResourceNotFoundException {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for: " + id));
    }

    @Override
    public User deleteUser(Long id) throws ResourceNotFoundException {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for: " + id));
        userRepo.delete(user);
        return user;
    }


}
