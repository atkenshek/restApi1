package com.example.restapi1;

import com.example.restapi1.business.entity.User;
import com.example.restapi1.business.service.UserServiceImpl;
import com.example.restapi1.exception.StatusFailedException;
import com.example.restapi1.exception.UserAlreadyExistsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import spock.lang.Specification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestApi1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestApi1ApplicationTests extends Specification {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserServiceImpl userService;


    @LocalServerPort
    private int port;

    private String getUrl(){
        return "http://localhost:" + port;
    }
    @Test
    void contextLoads() {
    }


    @Autowired
    private MockMvc mockMvc;




    @Test
    public void testGetAllUsers(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = testRestTemplate.exchange(getUrl() + "/user",
                HttpMethod.GET, entity, String.class);

        Assertions.assertNotNull(response.getBody());
    }

    @Test
    public void testGetUserById(){
        User expectedUser = new User(
                "Meiram Sopy Temirzhanov",
                "Male",
                "87477775454",
                "meiram@gmail.com"
        );
        User userTakenById = testRestTemplate.getForObject(getUrl() + "/user/1", User.class);
        assertEquals(userTakenById.getId(), expectedUser.getId());
    }

    @Test
    public void testCreateUser(){
        User expectedUser = new User(
                "Meiram Sopy Temirzhanov",
                "Male",
                "87477775454",
                "meiram@gmail.com"
        );
        ResponseEntity<User> postResponse = testRestTemplate.postForEntity(getUrl() + "/user", expectedUser, User.class);
        Assertions.assertNotNull(postResponse);
        Assertions.assertNotNull(postResponse.getBody());
        assertEquals("Meiram Sopy Temirzhanov", expectedUser.getFullName());
        assertEquals("Male", expectedUser.getGender());
        assertEquals("87477775454", expectedUser.getPhoneNumber());
        assertEquals("meiram@gmail.com", expectedUser.getEmail());

    }

    @Test
    public void testUpdateUser()  {
        int id = 1;
        User user =  testRestTemplate.getForObject(getUrl() + "/user/" + id, User.class);
        user.setFullName("Meiram Sopy Temirzhanov");

        testRestTemplate.put(getUrl() + "/user", id, user);
        User updateUser = testRestTemplate.getForObject(getUrl() + "/user" + id, User.class);

        Assertions.assertNotNull(updateUser);
    }

    @Test
    public void testDeleteUser(){
        int id = 2;
        User user =  testRestTemplate.getForObject(getUrl() + "/user/" + id, User.class);
        Assertions.assertNotNull(user);

        testRestTemplate.delete(getUrl() + "/user/" + id);

        try {
             testRestTemplate.getForObject(getUrl() + "/user/" + id, User.class);
        } catch (final HttpClientErrorException e){
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public void testDeleteAllUsers() throws StatusFailedException, UserAlreadyExistsException, JsonProcessingException {
        User expectedUser = new User(
                "Meiram Sopy Temirzhanov",
                "Male",
                "87477775454",
                "meiram@gmail.com"
        );
        userService.saveUserWithCountry(expectedUser);
        userService.deleteAllUsers();
        User user = testRestTemplate.getForObject(getUrl() + "/user/1", User.class);
        assertThat(user.getId()).isNull();
    }

}
