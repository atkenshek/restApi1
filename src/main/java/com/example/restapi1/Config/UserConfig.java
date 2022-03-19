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
                    "random9804@yandex.ru"
            );
            User user2 = new User(
                    "Alex Johnson",
                    "Male",
                    "87024141234",
                    "alex1244@mail.ru"
            );
            repository.saveAll(
                    List.of(user,user2)
            );
        };
    }
}
