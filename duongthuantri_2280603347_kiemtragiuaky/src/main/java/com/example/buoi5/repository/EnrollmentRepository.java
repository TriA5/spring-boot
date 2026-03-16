package com.example.buoi5.repository;

import com.example.buoi5.model.Enrollment;
import com.example.buoi5.model.Student;
import com.example.buoi5.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(Student student);

    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);

    boolean existsByStudentAndCourse(Student student, Course course);
}
