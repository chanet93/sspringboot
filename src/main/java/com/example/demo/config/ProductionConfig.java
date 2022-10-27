package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Profile("production")
@Configuration
public class ProductionConfig {

    @PostConstruct
    public void test(){
        System.out.println("Loaded production environment");
    }
}
