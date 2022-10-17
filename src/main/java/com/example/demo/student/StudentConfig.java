package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            Student mariam = new Student(
                    "Mariam",
                    "mariam@gmail.com",
                    LocalDate.of(2000, Month.JANUARY,
                    5),
                    21);
            Student raul = new Student(
                    "Raul",
                    "raul@gmail.com",
                    LocalDate.of(1990, Month.OCTOBER, 18),
                    31);

            studentRepository.saveAll(List.of(mariam,raul));
        };
    }
}
