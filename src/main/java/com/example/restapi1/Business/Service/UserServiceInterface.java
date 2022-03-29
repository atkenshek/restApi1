package com.example.restapi1.Business.Service;

import com.example.restapi1.Business.Entity.User;
import com.example.restapi1.exception.ResourceNotFoundException;
import com.example.restapi1.exception.StatusFailedException;
import com.example.restapi1.exception.UserAlreadyExistsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

@Service
public interface UserServiceInterface {
     Iterable<User> getUsers();
     User saveUser(User user) throws UserAlreadyExistsException;
     User saveUserWithCountry(User user) throws JsonProcessingException, UserAlreadyExistsException, UnknownHostException, StatusFailedException;
     User getUserById(Long id) throws ResourceNotFoundException;
     void updateUserById(Long id, User userDetails) throws ResourceNotFoundException;
     void deleteUserById(Long id) throws ResourceNotFoundException;
     void deleteAllUsers();

}
