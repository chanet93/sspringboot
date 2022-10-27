package com.example.demo.student;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@TestPropertySource(locations = "classpath:test.properties")
@ExtendWith(MockitoExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TestEntityManager entityManager;


    @Test
    @Sql("classpath:test-data.sql")
    void it_should_save_student_first() {
//        Student student = new Student();
//        student.setEmail("hope@gmail.com");
//
//        student = entityManager.persistAndFlush(student);
        assertThat(studentRepository.findStudentByEmail("hope@gmail.com").get()).isNotNull();

    }

    @Test

  //@Rollback(value = false)
//    @Order(1)
    void it_should_save_student() {
        //given
        Student student = new Student();
        student.setEmail("hope@gmail.com");
        //when
        student = studentRepository.save(student);
        //assertThat(studentRepository.findById(student.getId()).get()).isEqualTo(student);
        //then
        assertThat(student).isNotNull();
        assertThat(student.getId()).isGreaterThan(0);

    }
}
