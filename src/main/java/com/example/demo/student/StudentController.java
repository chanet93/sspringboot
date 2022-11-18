package com.example.demo.student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
@AllArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Get all products", description = "Get a list of products", tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the product",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))}),
            @ApiResponse(responseCode = "404", description = "Products not found",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<Student>>getStudents() {
        log.info("get all the students");
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping(path = "byEmail/{email}")
    public ResponseEntity<Student> findByEmail(@PathVariable(value = "email") String email){
        log.info("get student by email {}",email);
        Student student = studentService.findByEmail(email);
        return (student != null)?ResponseEntity.ok().body(student):ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Student> registerNewStudent(@RequestBody @Valid Student student) {
        studentService.addNewStudent(student);
        log.info("register a new student with id {}",student.getId());
        return new ResponseEntity<>(student,HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{studentId}")
    public ResponseEntity<Student> deleteStudentById(@PathVariable(value = "studentId") Long studentId) {
        Student student = studentService.deleteStudent(studentId);
        log.info("deleting this student {}",student);
        return (student != null)?ResponseEntity.ok().body(student):ResponseEntity.notFound().build();
    }

    @PutMapping(path = "{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable("studentId") Long studentId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email) {
        Student student = studentService.updateStudent(studentId, name, email);
        log.info("updating the student with id {}",student.getId());
        return ResponseEntity.ok(student);
    }

    @GetMapping(path = "startsWith/{startName}")
    public ResponseEntity<Object> findByNameStartsWith(@PathVariable("startName") String startName){
        List<Student> students = studentService.findByNameStartsWith(startName);
        log.info("student/s that start with {}",startName);
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }


}
