package com.example.restapi1.Config;

import com.example.restapi1.Entity.User;
import com.example.restapi1.Repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepo repository) {
        return args -> {
            User user = new User(
                    "Meiram Sopy Temirzhanov",
                    "Male",
                    "87477775454",
                    "random9804@yandex.ru",
                    "Kazakhstan",
                    "Nur-Sultan"
            );
            User user2 = new User(
                    "Alex Johnson",
                    "Male",
                    "87024141234",
                    "alex1244@mail.ru",
                    "USA",
                    "New-York"
            );
            User user3 = new User(
                    "Maria Anatolievna",
                    "Female",
                    "87003242355",
                    "maria1978@gmail.com",
                    "France",
                    "Paris"
            );
            repository.saveAll(
                    List.of(user,user2, user3)
            );
        };
    }
}
