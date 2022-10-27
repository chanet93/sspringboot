package com.example.demo.student;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    public void  getStudentsSetUp() {

        Student mariam = new Student(
                "Mariam",
                "mariam@gmail.com",
                LocalDate.of(2000, Month.JANUARY,
                        5));
        Student raul = new Student(
                "Raul",
                "raul@gmail.com",
                LocalDate.of(1990, Month.OCTOBER, 18));
        List<Student> studentList = List.of(mariam, raul);

        Mockito.when(studentRepository.findAll()).thenReturn(studentList);

    }

    @Test
    @DisplayName("It must return all the students records from the db")
    void getStudents() {
        List<Student> students = studentService.getStudents();
        assertThat(students).size().isEqualTo(2L);
    }

}