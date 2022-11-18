package com.example.demo.student;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email is already taken");
        }
        studentRepository.save(student);
        return student;
    }

    public Student deleteStudent(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isEmpty()) {
            throw new IllegalStateException("it doesn´t exist an student with id " + studentId);
        }
        studentRepository.deleteById(studentId);
        return student.get();
    }

    @Transactional
    public Student updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("The student doesn't exist"));
        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }
        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("the email is already taken");
            }
            student.setEmail(email);
        }
        studentRepository.save(student);
        return student;
    }

    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public List<Student> findByNameStartsWith(String startName) {
        return studentRepository.findByNameStartsWith(startName);
    }
}
