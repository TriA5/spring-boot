package com.example.buoi5.service;

import com.example.buoi5.model.Enrollment;
import com.example.buoi5.model.Student;

import java.util.List;

public interface EnrollmentService {
    Enrollment enroll(Student student, Long courseId);

    List<Enrollment> getEnrollmentsByStudent(Student student);

    boolean isEnrolled(Student student, Long courseId);
}
