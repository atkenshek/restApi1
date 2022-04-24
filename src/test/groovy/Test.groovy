import com.example.restapi1.RestApi1Application
import com.example.restapi1.business.entity.User
import com.example.restapi1.business.service.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import spock.lang.Specification
import static org.junit.jupiter.api.Assertions.assertEquals

@SpringBootTest(classes = RestApi1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Test extends Specification{

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

    def "should return user by id"(){
        given:
        User expectedUser = new User(
                "Meiram Sopy Temirzhanov",
                "Male",
                "87477775454",
                "meiram@gmail.com"
        );
        when:
        User userTakenById = testRestTemplate.getForObject(getUrl() + "/user/1", User.class);

        then:
        userTakenById.getId() == expectedUser.getId();
    }
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

    def "should update user"(){
        given:
        int id = 1;
        User user =  testRestTemplate.getForObject(getUrl() + "/user/" + id, User.class);
        user.setFullName("Meiram Sopy Temirzhanov");

        when:
        testRestTemplate.put(getUrl() + "/user", id, user);

        then:
        User updatedUser = testRestTemplate.getForObject(getUrl() + "/user" + id, User.class);
        updatedUser.getFullName() == "Meiram Sopy Temirzhanov";

    }

    def "should delete user by id"(){
        given:
        int id = 2;
        User user =  testRestTemplate.getForObject(getUrl() + "/user/" + id, User.class);

        when:
        testRestTemplate.delete(getUrl() + "/user/" + id);

        then:
        try {
            testRestTemplate.getForObject(getUrl() + "/user/" + id, User.class);
        } catch (final HttpClientErrorException e){
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    def "should delete all users"(){
        given:
        User expectedUser = new User(
                "Meiram Sopy Temirzhanov",
                "Male",
                "87477775454",
                "meiram@gmail.com"
        );

        when:
        userService.saveUserWithCountry(expectedUser);
        userService.deleteAllUsers();
        User user = testRestTemplate.getForObject(getUrl() + "/user/1", User.class);

        then:
        user.getId() == null;
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


