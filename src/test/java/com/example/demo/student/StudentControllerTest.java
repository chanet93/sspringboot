package com.example.demo.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

    @Test
    @Order(2)
    void it_should_return_all_students() throws Exception {
        //given
      Student student = new Student();
      student.setDob(LocalDate.now());
      student.setName("Raul");
      List<Student> students = Collections.singletonList(student);

      //when
      Mockito.when(studentService.getStudents()).thenReturn(students);

      //then
        mockMvc.perform(get("/api/v1/student")
              .contentType(APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$",hasSize(1)))
              .andExpect(jsonPath("$[0].name",is("Raul")));
    }

    @Test
    @Order(1)
    void it_should_register_student() throws Exception {
        //given
        Student student = new Student();
        student.setName("Hope");
        student.setEmail("hope@gmail.com");
        student.setDob(LocalDate.now());
        //when
        Mockito.when(studentService.addNewStudent(student)).thenReturn(student);

        //then
        mockMvc.perform(post("/api/v1/student")
                .contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated());


    }

    @Test
    void it_should_delete_student_with_valid_id() throws Exception {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setName("Hope");
        //when
        Mockito.when(studentService.deleteStudent(1L)).thenReturn(student);
        //then
        mockMvc.perform(delete(URL + student.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(student.getName())));
    }

}