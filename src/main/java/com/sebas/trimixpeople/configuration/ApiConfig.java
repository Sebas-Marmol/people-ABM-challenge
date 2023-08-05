package com.sebas.trimixpeople.configuration;

import com.sebas.trimixpeople.controllers.PersonController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig extends ResourceConfig {

    public ApiConfig(){
            register(PersonController.class);
    }

}
