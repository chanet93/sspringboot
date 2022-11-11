package com.example.demo.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StudentService studentService;

    private static final String URL = "/api/v1/student/";

    Student student_1 = new Student(1L, "Raul", "raul@gmail.com", LocalDate.now(), 12);
    Student student_2 = new Student(1L, "Hope", "hope@gmail.com", LocalDate.now(), 12);
    Student student_3 = new Student(1L, "Agatha", "agatha@gmail.com", LocalDate.now(), 12);

    @Test
    @Order(2)
    void it_should_return_all_students() throws Exception {

        List<Student> students = List.of(student_1, student_2, student_3);

        //when
       Mockito.when(studentService.getStudents()).thenReturn(students);

      //then
        mockMvc.perform(get("/api/v1/student")
              .contentType(APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$",hasSize(3)))
              .andExpect(jsonPath("$[0].name",is("Raul")));
    }

    @Test
    @Order(1)
    void it_should_register_student() throws Exception {
        //given

        //when
        Mockito.when(studentService.addNewStudent(student_1)).thenReturn(student_1);

        //then
        mockMvc.perform(post("/api/v1/student")
                .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student_1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name", is(student_1.getName())));

    }

    @Test
    void it_should_delete_student_with_valid_id() throws Exception {
        //given

        //when
        Mockito.when(studentService.deleteStudent(student_1.getId())).thenReturn(student_1);
        //then
        mockMvc.perform(delete(URL + student_1.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(student_1.getName())));
    }

    @Test
    void it_should_not_delete_student_with_not_valid_id() throws Exception {
        //given

        //when
        Mockito.when(studentService.deleteStudent(student_1.getId())).thenReturn(null);
        //then
        mockMvc.perform(delete(URL + student_1.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("JUnit test for updateStudent method")
    void it_should_update_student_with_valid_id() throws Exception {
        //given
        Student student = new Student(1L, "Raul", "hope@gmail.com", LocalDate.now(), 12);
        //when
        Mockito.when(studentService.updateStudent(student_1.getId(), student_1.getName(), "hope@gmail.com")).thenReturn(student);
        //then
        mockMvc.perform(put(URL+ student.getId()+"?email=hope@gmail.com")
                .contentType(APPLICATION_JSON))
                //.andExpect(jsonPath("$",notNullValue()))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("email", is(student.getEmail())));
    }

    @Test
    void it_should_update_student_with_null_parameters() throws Exception {
        //when
        Mockito.when(studentService.updateStudent(student_1.getId(),null, null)).thenReturn(student_1);

        //then
        mockMvc.perform(put(URL+ student_1.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("email", is(student_1.getEmail())));
    }

//    @Test
//    void it_should_update_student_with_not_valid_id() throws Exception {
//        //when
//        Mockito.when(studentService.updateStudent(5L,null, null)).thenThrow(IllegalStateException.class);
//
//        //then
//        mockMvc.perform(put(URL+ 5L)
//                        .contentType(APPLICATION_JSON))
//                .andExpect(status().is(500))
//                .andExpect(jsonPath("$",notNullValue()))
//               ;
//    }



}