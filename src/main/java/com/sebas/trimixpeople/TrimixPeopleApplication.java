package com.sebas.trimixpeople;

import com.sebas.trimixpeople.controllers.PersonController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TrimixPeopleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrimixPeopleApplication.class, args);
    }



}
