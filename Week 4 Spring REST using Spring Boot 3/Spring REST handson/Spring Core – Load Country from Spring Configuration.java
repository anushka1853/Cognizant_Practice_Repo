package com.cognizant.spring_learn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ByteArrayResource;

public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        LOGGER.debug("START main()");
        displayCountry();
        LOGGER.debug("END main()");
    }

    /** Loads XML, gets the bean, prints it. */
    private static void displayCountry() {

        // ---------- country.xml as a string ----------
        String xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <beans xmlns="http://www.springframework.org/schema/beans"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://www.springframework.org/schema/beans
                                       https://www.springframework.org/schema/beans/spring-beans.xsd">

                <bean id="country" class="com.cognizant.spring_learn.SpringLearnApplication$Country">
                    <property name="code" value="IN"/>
                    <property name="name" value="India"/>
                </bean>
            </beans>
            """;

        // ---------- bootstrap a context from the XML string ----------
        GenericApplicationContext ctx = new GenericApplicationContext();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
        reader.loadBeanDefinitions(new ByteArrayResource(xml.getBytes()));
        ctx.refresh();

        // ---------- fetch the bean ----------
        Country country = ctx.getBean("country", Country.class);
        LOGGER.debug("Country  : {}", country);

        ctx.close();
    }

    /** Inner static Country class (acts like com.cognizant.springlearn.Country). */
    public static class Country {

        private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

        private String code;
        private String name;

        public Country() {                       // empty constructor
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
