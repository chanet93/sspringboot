package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Profile("test")
@Configuration
public class TestingConfig {

    @PostConstruct
    public void test(){
        System.out.println("Loaded testing environment");
    }
}
