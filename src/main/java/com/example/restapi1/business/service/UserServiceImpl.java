package com.example.restapi1.business.service;

import com.example.restapi1.business.entity.User;
import com.example.restapi1.repository.UserRepo;
import com.example.restapi1.exception.StatusFailedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.restapi1.exception.ResourceNotFoundException;
import com.example.restapi1.exception.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service @Slf4j
public class UserServiceImpl implements UserServiceInterface{

    private final UserRepo userRepo;
    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RestTemplate restTemplate) {
        this.userRepo = userRepo;
        this.restTemplate = restTemplate;
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        User userNew;
        if (!userRepo.existsByEmail(user.getEmail())) {
            log.info("Saving new user {} to the database", user.getFullName());
            userNew = userRepo.save(user);
        } else {
            log.warn("User with email {} already exists in database", user.getEmail());
            throw new UserAlreadyExistsException("User with email: " + user.getEmail() + " already exists in database");
        }
        return userNew;
    }

    private String getPublicIpAddress() {
        // Find public IP address
        String publicIP = "";
        try {
            URL url_name = new URL("https://ifconfig.me/ip");
            BufferedReader sc =
                    new BufferedReader(new InputStreamReader(url_name.openStream()));
            publicIP = sc.readLine().trim();
        }
        catch (Exception e)
        {
            publicIP = "Cannot Execute Properly";
        }
        return publicIP;
    }
    public User saveUserWithCountry(User user) throws JsonProcessingException, UserAlreadyExistsException, StatusFailedException{
            String userIP = getPublicIpAddress();
            String userInfo = restTemplate.getForObject("http://ip-api.com/json/" + userIP + "?fields=status,country,city,as", String.class);
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(userInfo, Map.class);

            if(map.get("status").equals("fail")){
                throw new StatusFailedException("Status failed for IP address: " + userIP);
            } else {
                user.setCountry((String) map.get("country"));
                user.setCity((String) map.get("city"));
                user.setISP((String) map.get("as"));
            }
        User newUser = new User();
        if(!userRepo.existsByEmail(user.getEmail())){
            log.info("Saving new user {} to the database", user.getFullName());
            newUser = userRepo.save(user);
        }
        else {
            log.warn("User with email {} already exists in database", user.getEmail());
            throw new UserAlreadyExistsException("User with email: " + user.getEmail() + " already exists in database");
        }
        return newUser;
    }

    @Override
    public Iterable<User> getUsers() {
        log.info("Selecting all users");
        return userRepo.findAll();
    }

    @Override
    public void updateUserById(Long id, User userDetails) throws ResourceNotFoundException {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for: " + id));
        user.setFullName(userDetails.getFullName());
        user.setGender(userDetails.getGender());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setEmail(userDetails.getEmail());
        userRepo.save(user);

    }

    @Override
    public User getUserById(Long id) throws ResourceNotFoundException {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for: " + id));
    }

    @Override
    public void deleteUserById(Long id) throws ResourceNotFoundException {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for: " + id));
        userRepo.delete(user);
    }

    @Override
    public void deleteAllUsers(){
        userRepo.deleteAll();
    }


}
