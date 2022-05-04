package com.example.restapi1.controller

import com.example.restapi1.business.entity.User
import com.example.restapi1.business.service.UserServiceImpl
import com.example.restapi1.containers.LocalPostgreSQLContainer
import com.example.restapi1.repository.UserRepo
import com.example.restapi1.testproperties.TestInitializer
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification
import javax.servlet.http.HttpServletRequest

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = [TestInitializer])
@AutoConfigureMockMvc
class UserControllerTest extends Specification{

    @Shared
    PostgreSQLContainer postgreSQLContainer = LocalPostgreSQLContainer.getInstance()

    @Autowired
    MockMvc mockMvc

    @Autowired
    private TestRestTemplate testRestTemplate

    @Autowired
    UserServiceImpl userService

    @Autowired
    UserRepo userRepository

    @LocalServerPort
    private int port

    private String getUrl(){
        return "http://localhost:" + port
    }

    private JacksonTester<User> jsonUser

    private static jsonSlurper = new JsonSlurper()

    def setup() {
        JacksonTester.initFields(this, new ObjectMapper())
    }

    def cleanup() {
        userRepository.deleteAll()
    }

    private static final String BASE_PATH = "/api/user"

    private static final userExample = new User(
            "Meiram Sopy Temirzhanov",
            "Male",
            "87477775454",
            "meiram@gmail.com"
    )
    def "should return correct user id when finding user by ID"() {
        given:
            userService.saveUser(userExample)
        when:
            def response = mockMvc.perform(get(BASE_PATH + "/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
            def result = jsonSlurper.parseText(response.getContentAsString())
        then:
            response.getStatus() == HttpStatus.OK.value()
            response.getContentType() == "application/json"
            result.id == userExample.getId()
            result.fullName == userExample.getFullName()
            result.phoneNumber == userExample.getPhoneNumber()
            result.email == userExample.getEmail()


    }



//    def "should return status 200 when finding user by ID"() {
//        given:
//            userRepository.save(userExample)
//            Optional<User> savedUser = userRepository.findById(1L)
//        when:
//            def response = mockMvc.perform(get(BASE_PATH + "/${savedUser.get().getId()}")
//                    .contentType(MediaType.APPLICATION_JSON))
//                    .andReturn()
//                    .getResponse()
//        then:
//            response.getStatus() == HttpStatus.OK.value()
//    }
    def "should return status 200 when creating new user"() {
        given:
            def request = Mock(HttpServletRequest)
        when:
            def response = mockMvc.perform(post(BASE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUser.write(new User(
                            fullName: "asd",
                            gender: "Male",
                            phoneNumber: "+375000000000",
                            email: "asd@gmail.com"
                            )).getJson()))
                    .andReturn()
                    .getResponse()
            def result = jsonSlurper.parseText(response.getContentAsString())
        then:
            response.getStatus() == HttpStatus.OK.value()
            result.id == "1"
            result.fullName == "asd"
            result.gender == "Male"
            result.phoneNumber == "+375000000000"
            result.email == "asd@gmail.com"
    }

    def "should return status 200 when user is updated"() {
        given:
            User savedUser = userRepository.save(userExample)
        when:
            def response = mockMvc.perform(put(BASE_PATH + "/${savedUser.getId()}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUser.write(new User(
                            id: savedUser.getId(),
                            fullName: "Meiram Sopy Temirzhanov",
                            gender: "Male",
                            phoneNumber: "87477775454",
                            email: "meiram@gmail.com"
                    )).getJson()))
                    .andReturn()
                    .getResponse()
            def result = jsonSlurper.parseText(response.getContentAsString())
        then:
            response.getStatus() == HttpStatus.OK.value()
            result.id == savedUser.getId()
            result.fullName == "Meiram Sopy Temirzhanov"
            result.gender == "Male"
            result.phoneNumber == "87477775454"
            result.email == "meiram@gmail.com"
    }

    def "should return status 200 when deleting user by ID"() {
        given:
            def savedUser = userRepository.save(userExample)
        when:
            def response = mockMvc.perform(delete(BASE_PATH + "/${savedUser.getId()}")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
        then:
            response.getStatus() == HttpStatus.OK.value()
    }
    def "should return status 200 when getting all users"() {
        given:
            userRepository.save(userExample)
        when:
            def response = mockMvc.perform(get(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        then:
            response.getStatus() == HttpStatus.OK.value()

    }

}
