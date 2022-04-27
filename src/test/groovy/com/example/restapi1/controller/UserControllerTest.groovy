package com.example.restapi1.controller

import com.example.restapi1.business.entity.User
import com.example.restapi1.business.service.UserServiceImpl
import com.example.restapi1.containers.LocalPostgreSQLContainer
import com.example.restapi1.repository.UserRepo
import com.example.restapi1.testproperties.TestInitializer
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
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
    UserServiceImpl userService

    @Autowired
    UserRepo userRepository

    private JacksonTester<User> jsonUser

    def setup() {
        JacksonTester.initFields(this, new ObjectMapper())
    }

    def cleanup() {
        userRepository.deleteAll()
    }

    private static final String BASE_PATH = "/api/user"


    def userExample = new User(
            1L,
            "Meiram Sopy Temirzhanov",
            "Male",
            "87477775454",
            "meiram@gmail.com"
    )

    def "should return status 200 when finding user by ID"() {
        given:
            userRepository.save(userExample)
            Optional<User> savedUser = userRepository.findById(1L)
        when:
            def response = mockMvc.perform(get(BASE_PATH + "/${savedUser.get().getId()}")
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
        then:
            response.getStatus() == HttpStatus.OK.value()
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
        then:
            response.getStatus() == HttpStatus.OK.value()

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

}
