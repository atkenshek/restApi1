package com.example.restapi1.Service;

import com.example.restapi1.Entity.User;
import com.example.restapi1.exception.ResourceNotFoundException;
import com.example.restapi1.exception.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServiceInterface {
     User createUser(User user) throws UserAlreadyExistsException;
     Iterable<User> getUsers();
     User updateUser(Long id, User userDetails) throws ResourceNotFoundException;
     User getUserById(Long id) throws ResourceNotFoundException;
     User deleteUser(Long id) throws ResourceNotFoundException;
}
