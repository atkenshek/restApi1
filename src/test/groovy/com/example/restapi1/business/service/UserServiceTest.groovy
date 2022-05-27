package com.example.restapi1.business.service

import com.example.restapi1.business.entity.User
import com.example.restapi1.repository.UserRepo
import org.springframework.web.client.RestTemplate
import spock.lang.Specification


class UserServiceTest extends Specification{

    def userRepository = Mock(UserRepo)

    def restTemplate = Mock(RestTemplate)

    def userService = new UserServiceImpl(userRepository, restTemplate, config)

    def user = new User(
            1L,
            "Meiram Sopy Temirzhanov",
            "Male",
            "87477775454",
            "meiram@gmail.com"
    )

    def "should return all users"(){
        given:
            userRepository.findAll() >> List.of(user)
        when:
            def result = userService.getUsers()
        then:
            result == List.of(user)
    }

    def "should return user by id"(){
        given:
            def id = 1L
            userRepository.findById(id) >> Optional.of(user)
        when:
            def result = userService.getUserById(id)
        then:
            result == user
    }

    def "should create user"(){
        given:
            userRepository.save(user) >> user
        when:
            User savedUser = userService.saveUser(user)
        then:
            savedUser == user
    }


    def "should update user"(){
        given:
            def id = 1L
            def newUser = new User(
                    "Meiram Sopy Temirzhanov",
                    "Male",
                    "87477775454",
                    "meiram@gmail.com"
            )
            userRepository.findById(id) >> Optional.of(user)
        when:
            userService.updateUserById(id, newUser)
            User updatedUser = userService.getUserById(id)
        then:
            updatedUser == user
    }

    def "should delete user by id"(){
        given:
            def id = 1L
            userRepository.findById(id) >> Optional.of(user)
        when:
            userService.deleteUserById(id)
            User deletedUser = userService.getUserById(1L)
        then:
            deletedUser == user
    }

    def "one plus one should equal two"(){
        expect:
        1 + 1 == 2
    }

}


