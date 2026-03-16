package com.example.buoi5.service.impl;

import com.example.buoi5.model.Course;
import com.example.buoi5.model.Enrollment;
import com.example.buoi5.model.Student;
import com.example.buoi5.repository.CourseRepository;
import com.example.buoi5.repository.EnrollmentRepository;
import com.example.buoi5.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    @Override
    public Enrollment enroll(Student student, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("Already enrolled in this course");
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudent(Student student) {
        return enrollmentRepository.findByStudent(student);
    }

    @Override
    public boolean isEnrolled(Student student, Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null)
            return false;
        return enrollmentRepository.existsByStudentAndCourse(student, course);
    }
}
