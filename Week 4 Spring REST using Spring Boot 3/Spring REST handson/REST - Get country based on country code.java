package com.cognizant.spring_learn;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@SpringBootApplication
@RestController
@RequestMapping("/countries")         
public class CountryLookupApplication {

    static { System.setProperty("server.port", "8083"); }

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryLookupApplication.class);



    /** Loads XML once and exposes service methods. */
    private static final CountryService countryService = initService();

    @GetMapping("/{code}")
    public Country getCountry(@PathVariable String code) {
        LOGGER.debug("START getCountry({})", code);
        Country c = countryService.getCountry(code);
        LOGGER.debug("END   getCountry({}) → {}", code, c);
        return c;
    }

    public static void main(String[] args) {
        SpringApplication.run(CountryLookupApplication.class, args);
        LOGGER.info("GET http://localhost:8083/countries/in");
    }

    private static CountryService initService() {

        String xml = """
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://www.springframework.org/schema/beans
                                   https://www.springframework.org/schema/beans/spring-beans.xsd">

          <!-- list of four countries -->
          <bean id="countryList" class="java.util.ArrayList">
              <constructor-arg>
                  <list>
                      <bean class="com.cognizant.spring_learn.CountryLookupApplication$Country">
                          <property name="code" value="US"/>
                          <property name="name" value="United States"/>
                      </bean>
                      <bean class="com.cognizant.spring_learn.CountryLookupApplication$Country">
                          <property name="code" value="DE"/>
                          <property name="name" value="Germany"/>
                      </bean>
                      <bean class="com.cognizant.spring_learn.CountryLookupApplication$Country">
                          <property name="code" value="IN"/>
                          <property name="name" value="India"/>
                      </bean>
                      <bean class="com.cognizant.spring_learn.CountryLookupApplication$Country">
                          <property name="code" value="JP"/>
                          <property name="name" value="Japan"/>
                      </bean>
                  </list>
              </constructor-arg>
          </bean>
        </beans>
        """;

        GenericApplicationContext ctx = new GenericApplicationContext();
        new XmlBeanDefinitionReader(ctx).loadBeanDefinitions(new ByteArrayResource(xml.getBytes()));
        ctx.refresh();
        List<Country> list = ctx.getBean("countryList", List.class);
        return new CountryService(list);
    }

    public static class Country {
        @JsonProperty private String code;
        @JsonProperty private String name;

        public Country() { LOGGER.debug("Inside Country constructor"); }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        @Override public String toString() { return code + " - " + name; }
    }


    public static class CountryService {

        private final List<Country> countries;
        CountryService(List<Country> list) { this.countries = list; }

        public Country getCountry(String code) {
            return countries.stream()
                    .filter(c -> c.getCode().equalsIgnoreCase(code))
                    .findFirst()
                    .orElse(null);             
        }
    }
}
