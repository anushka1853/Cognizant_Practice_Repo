/*code for HelloWorldApllication.java: */

package com.cognizant.spring_learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A self-contained Hello World Spring Boot REST app in one Java file.
 */
@SpringBootApplication
@RestController
public class HelloWorldApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
        LOGGER.debug("Application started successfully.");
    }

    /**
     * GET /hello → "Hello World!!"
     */
    @GetMapping("/hello")
    public String sayHello() {
        LOGGER.debug("Start sayHello()");
        String message = "Hello World!!";
        LOGGER.debug("End sayHello()");
        return message;
    }
}
