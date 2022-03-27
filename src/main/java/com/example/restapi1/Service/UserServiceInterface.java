package com.example.restapi1.Service;

import com.example.restapi1.Entity.User;
import com.example.restapi1.exception.ResourceNotFoundException;
import com.example.restapi1.exception.StatusFailedException;
import com.example.restapi1.exception.UserAlreadyExistsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.List;

@Service
public interface UserServiceInterface {
     User saveUser(User user) throws UserAlreadyExistsException, JsonProcessingException, UnknownHostException;
     User saveUserNew(User user) throws JsonProcessingException, UserAlreadyExistsException, UnknownHostException, StatusFailedException;
     Iterable<User> getUsers();
     User updateUser(Long id, User userDetails) throws ResourceNotFoundException;
     User getUserById(Long id) throws ResourceNotFoundException;
     User deleteUser(Long id) throws ResourceNotFoundException;
}
