package com.example.demo.student;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @Test
    void it_should_return_all_students() throws Exception {
      Student student = new Student();
      student.setDob(LocalDate.now());
      student.setName("Raul");
      List<Student> students = Collections.singletonList(student);

      Mockito.when(studentService.getStudents()).thenReturn(students);

        mockMvc.perform(get("/api/v1/student")
              .contentType(APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$",hasSize(1)))
              .andExpect(jsonPath("$[0].name",is("Raul")));
    }
}