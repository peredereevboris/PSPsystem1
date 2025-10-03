package com.example.psp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Main Spring Boot application class (PSP System).
 * Creates embedded Tomcat and initializes the application context.
 */
@SpringBootApplication
public class PspApplication {

    public static void main(String[] args) {
        SpringApplication.run(PspApplication.class, args);
    }
}
