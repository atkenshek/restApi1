import com.example.restapi1.RestApi1Application
import com.example.restapi1.business.entity.User
import com.example.restapi1.business.service.UserServiceImpl
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertEquals


class Test extends Specification{


    def user = Mock(User)

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private String getUrl(){
        return "http://localhost:" + port;
    }

    def "one plus one should equal two"(){
        expect:
        1 + 1 == 2
    }

//    def "should create user"(){
//        given:
//        User expectedUser = new User(
//                "Meiram Sopy Temirzhanov",
//                "Male",
//                "87477775454",
//                "meiram@gmail.com"
//        )
//        when:
//        User expectedUser2 = userService.saveUserWithCountry(expectedUser);
//
//        then:
//        expectedUser.equals(expectedUser2);
//    }
    def "should create user"(){
        given:
        User expectedUser = new User(
                "Meiram Sopy Temirzhanov",
                "Male",
                "87477775454",
                "meiram@gmail.com"
        )
        when:
        ResponseEntity<User> postResponse = testRestTemplate.postForEntity(getUrl() + "/user", expectedUser, User.class);

        then:

        "Meiram Sopy Temirzhanov" == expectedUser.getFullName();
        "Male" == expectedUser.getGender();
        "87477775454" == expectedUser.getPhoneNumber();
        "meiram@gmail.com" == expectedUser.getEmail();

    }

    def "should delete user"(){
        given:
        int id = 2;

    }

//    @Autowired
//    private UserServiceImpl userService;
//
//    @Autowired
//    private MockMvc mvc;
//
//    def "returns hello world"(){
//        expect:
//        mvc.perform(MockMvcRequestBuilders.get("/hello"))
//        .andExpect(MockMvcResultMatchers.status().isOk())
//        .andReturn().response.contentAsString.toLowerCase() == "hello world!"
//    }
//    def "assert bean created"(){
//        expect: "bean creation is successful"
//        userService != null
//    }

}


