package com.cognizant.spring_learn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Build & run (from project root with pom.xml):
 *
 *   mvn spring-boot:run
 *
 * Then browse to:
 *   http://localhost:8080/hello
 */
@SpringBootApplication
@RestController                    // the same file doubles as controller
public class SpringLearnApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        log.debug("START main()");
        SpringApplication.run(SpringLearnApplication.class, args);
        log.debug("END main()");
    } 
  /* REST ENDPOINT: Hello from Spring Boot */


    @GetMapping("/hello")
    public String sayHello() {
        log.debug("START sayHello()");
        String msg = "Hello from Spring Boot";
        log.debug("END sayHello()");
        return msg;
    }
}
