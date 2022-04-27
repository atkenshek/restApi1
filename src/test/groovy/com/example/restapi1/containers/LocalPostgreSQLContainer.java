package com.example.restapi1.containers;

import org.testcontainers.containers.PostgreSQLContainer;

public class LocalPostgreSQLContainer extends PostgreSQLContainer<LocalPostgreSQLContainer> {
    private static final String IMAGE_VERSION = "postgres:latest";
    private static LocalPostgreSQLContainer container;

    private LocalPostgreSQLContainer() {
        super(IMAGE_VERSION);
    }

    public static LocalPostgreSQLContainer getInstance() {
        if (container == null) {
            container = new LocalPostgreSQLContainer();
        }
        return container;
    }
}
