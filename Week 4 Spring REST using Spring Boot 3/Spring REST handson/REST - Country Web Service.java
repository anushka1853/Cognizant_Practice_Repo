package com.cognizant.spring_learn;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class CountryWebServiceApplication {

    
    static {
        System.setProperty("server.port", "8083");   
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryWebServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CountryWebServiceApplication.class, args);
        LOGGER.info("Country Web Service started on http://localhost:8083/country");
    }

   
    @RequestMapping("/country")                 
    public Country getCountryIndia() {

        LOGGER.debug("START getCountryIndia()");

      
        String xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <beans xmlns="http://www.springframework.org/schema/beans"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://www.springframework.org/schema/beans
                                       https://www.springframework.org/schema/beans/spring-beans.xsd">

                <bean id="country" class="com.cognizant.spring_learn.CountryWebServiceApplication$Country">
                    <property name="code" value="IN"/>
                    <property name="name" value="India"/>
                </bean>
            </beans>
            """;

        // --- Spring context from XML string ---
        GenericApplicationContext ctx = new GenericApplicationContext();
        new XmlBeanDefinitionReader(ctx).loadBeanDefinitions(new ByteArrayResource(xml.getBytes()));
        ctx.refresh();

        Country country = ctx.getBean("country", Country.class);

        LOGGER.debug("END getCountryIndia()");
        ctx.close();
        return country;         
    }

    public static class Country {

        @JsonProperty("code")  
        private String code;

        @JsonProperty("name")
        private String name;

        public Country() {                             
            LOGGER.debug("Inside Country Constructor.");
        }

        public String getCode() {
            LOGGER.debug("Inside getCode()");
            return code;
        }
        public void setCode(String code) {
            LOGGER.debug("Inside setCode()");
            this.code = code;
        }

        public String getName() {
            LOGGER.debug("Inside getName()");
            return name;
        }
        public void setName(String name) {
            LOGGER.debug("Inside setName()");
            this.name = name;
        }

        @Override
        public String toString() {
            return "Country{code='%s', name='%s'}".formatted(code, name);
        }
    }
}
