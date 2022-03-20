package com.example.restapi1;

import com.example.restapi1.Entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestApi1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestApi1ApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private String getUrl(){
        return "http://localhost:" + port;
    }
    @Test
    void contextLoads() {
    }

    @Test
    public void testGetAllUsers(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = testRestTemplate.exchange(getUrl() + "/users",
                HttpMethod.GET, entity, String.class);

        Assertions.assertNotNull(response.getBody());
    }

    @Test
    public void testGetUserById(){
        User user = testRestTemplate.getForObject(getUrl() + "/users/1", User.class);
        System.out.println(user.getFullName());
        Assertions.assertNotNull(user);
    }

    @Test
    public void testCreateUser(){
        User user = new User();
        user.setFullName("Meiram Sopy Temirzhanov");
        user.setEmail("meiram@gmail.com");
        user.setPhoneNumber("87477775454");
        user.setGender("Male");
        ResponseEntity<User> postResponse = testRestTemplate.postForEntity(getUrl() + "/users", user, User.class);
        Assertions.assertNotNull(postResponse);
        Assertions.assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateUser(){
        int id = 1;
        User user =  testRestTemplate.getForObject(getUrl() + "/users/" + id, User.class);
        user.setFullName("Meiram Sopy Temirzhanov");

        testRestTemplate.put(getUrl() + "/users", id, user);
        User updateUser = testRestTemplate.getForObject(getUrl() + "/users" + id, User.class);

        Assertions.assertNotNull(updateUser);
    }

    @Test
    public void testDeleteUser(){
        int id = 2;
        User user =  testRestTemplate.getForObject(getUrl() + "/users/" + id, User.class);
        Assertions.assertNotNull(user);

        testRestTemplate.delete(getUrl() + "/users/" + id);

        try {
             testRestTemplate.getForObject(getUrl() + "/users/" + id, User.class);
        } catch (final HttpClientErrorException e){
            Assertions.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }


}
