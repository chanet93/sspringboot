package com.example.demo.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    Student findByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.name like ?1%")
    List<Student> findByNameStartsWith(String studentStartName);

    @Query("SELECT s FROM Student s WHERE s.name like %?1")
    List<Student> findByNameEndsWith(String endsName);

    @Query("SELECT s FROM Student s WHERE s.name like %?1%")
    List<Student> findByNameContains(String substring);

    @Query("SELECT s FROM Student s WHERE s.name like :studentName% or s.email like :email%")
    List<Student> findByNameAndEmailStarts(@Param("studentName") String name, String email);

    @Query(value = "SELECT * FROM student order by date_of_birth desc", nativeQuery = true)
    List<Student> sortStudentsByBirth();

    @Query("DELETE FROM Student s WHERE s.name =:name")
    @Transactional
    @Modifying
    void deleteByName(String name);

    @Query("UPDATE Student s SET s.name =:newName WHERE s.name =:name")
    @Transactional
    @Modifying
    int updateByName(String newName,String name);

    @Query("SELECT s FROM Student s WHERE s.name like ?1%")
    Page<Student> findByNameStartsWithPageable(String name, Pageable pageable);
}
