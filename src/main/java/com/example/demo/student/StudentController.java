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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @DeleteMapping(path = "deleteByName/{name}")
    public void deleteStudentByName(@PathVariable(value = "name") String name) {
        studentService.deleteStudentByName(name);
        log.info("deleting student by name {}", name);
    }

    @PutMapping(path = "{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable("studentId") Long studentId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email) {
        Student student = studentService.updateStudent(studentId, name, email);
        log.info("updating the student with id {}",student.getId());
        return ResponseEntity.ok(student);
    }

    @PutMapping(path = "updateByName/{newName}/{name}")
    public ResponseEntity<Object> updateStudentByName(@PathVariable("newName") String newName,
                                                       @PathVariable("name") String name) {
      ;

        log.info("updating the student by name {}",name);
        return ResponseEntity.status(HttpStatus.OK).body(studentService.updateStudentByName(newName, name));
    }

    @GetMapping(path = "startsWith/{startName}")
    public ResponseEntity<List<Student>> findByNameStartsWith(@PathVariable("startName") String startName){
        List<Student> students = studentService.findByNameStartsWith(startName);
        log.info("student/s that start with {}",startName);
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    @GetMapping(path = "endsWith/{endsName}")
    public ResponseEntity<List<Student>> findByNameEndsWith(@PathVariable("endsName") String endsName){
        List<Student> students = studentService.findByNameEndsWith(endsName);
        log.info("student/s´s name that ends with {}",endsName);
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    @GetMapping(path = "contains/{substring}")
    public ResponseEntity<List<Student>> findByNameContains(@PathVariable("substring") String substring){
        List<Student> byNameContains = studentService.findByNameContains(substring);
        log.info("student/s's name or email that starts with {}",substring);
        return ResponseEntity.status(HttpStatus.OK).body(byNameContains);
    }

    @GetMapping(path = "nameOrEmailStartsWith/{name}/{email}")
    public ResponseEntity<List<Student>> findByNameAndEmailStarts(@PathVariable("name") String name, @PathVariable("email") String email){
        List<Student> byNameAndEmailStarts = studentService.findByNameAndEmailStarts(name, email);
        log.info("student/s's name or email that starts with {}{}", name, email);
        return  ResponseEntity.status(HttpStatus.OK).body(byNameAndEmailStarts);
    }

    @GetMapping(path = "sortedDescByDateOfBirth")
    public ResponseEntity<List<Student>> sortStudentsByBirth(){
        List<Student> students = studentService.sortStudentsByBirth();
        log.info("student/s sorted desc by day of birth");
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

    @GetMapping(path = "startsWithPageable/{startName}")
    public ResponseEntity<Page<Student>> findByNameStartsWithPageable(@PathVariable("startName") String startName, Pageable page){
        Page<Student> students = studentService.findByNameStartsWithPageable(startName, page);
        log.info("student/s that start with {} with a total of pages {}",startName, page);
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }


}
