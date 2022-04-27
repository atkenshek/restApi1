package com.example.restapi1.testproperties

import com.example.restapi1.containers.LocalPostgreSQLContainer
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

class TestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private PostgreSQLContainer postgreSQLContainer = LocalPostgreSQLContainer.getInstance()

    @Override
    void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url=${postgreSQLContainer.getJdbcUrl()}",
                "spring.datasource.username=${postgreSQLContainer.getUsername()}",
                "spring.datasource.password=${postgreSQLContainer.getPassword()}"
        ).applyTo(applicationContext.environment)
    }
}