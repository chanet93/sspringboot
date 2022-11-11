package com.example.demo.student;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    Student student_1 = new Student(1L, "Raul", "raul@gmail.com", LocalDate.now(), 12);
    Student student_2 = new Student(1L, "Hope", "hope@gmail.com", LocalDate.now(), 12);
    Student student_3 = new Student(1L, "Agatha", "agatha@gmail.com", LocalDate.now(), 12);

    private static final String EMAIL_TEST = "test@gamil.com";

    @Test
    @DisplayName("It must return all the students records from the db")
    void it_should_return_all_students() {
        //given
        List<Student> studentList = List.of(student_1,student_2,student_3);
        //when
        Mockito.when(studentRepository.findAll()).thenReturn(studentList);
        //then
        List<Student> students = studentService.getStudents();
        assertThat(students).size().isEqualTo(3L);
    }

    @Test
    @DisplayName("It must update student with valid ID")
    void it_should_update_student_with_valid_id() {
        //given
        Mockito.when(studentRepository.findById(student_1.getId())).thenReturn(Optional.ofNullable(student_1));
        Mockito.when(studentRepository.findStudentByEmail(EMAIL_TEST)).thenReturn(Optional.empty());
        Student updateStudent = new Student(1L, "Raul", EMAIL_TEST, LocalDate.now(), 12);

        //when
        updateStudent = studentService.updateStudent(student_1.getId(),student_1.getName(),EMAIL_TEST);

        //then
        assertThat(updateStudent.getEmail().equalsIgnoreCase(EMAIL_TEST));
        assertThat(updateStudent.getId().equals(student_1.getId()));
        assertThat(updateStudent.getName().equals(student_1.getName()));


    }

//    when(mock.isOk()).thenReturn(true);
//    when(mock.isOk()).thenThrow(exception);
//    doThrow(exception).when(mock).someVoidMethod();

    @Test
    @DisplayName("It must throw an exception with invalid ID")
    void it_should_return_error_with_invalid_id() {
        //given
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            throw new IllegalStateException("The student doesn't exist");
        });
        Mockito.when(studentRepository.findById(4L)).thenThrow(exception);
        //then

        assertThatThrownBy(() -> studentService.updateStudent(4L,student_1.getName(),EMAIL_TEST))
       .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("The student doesn't exist");

    }
    }
