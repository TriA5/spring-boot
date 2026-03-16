package com.example.buoi5.controller;

import com.example.buoi5.model.Student;
import com.example.buoi5.repository.StudentRepository;
import com.example.buoi5.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MyCourseController {

    private final EnrollmentService enrollmentService;
    private final StudentRepository studentRepository;

    @GetMapping("/my-courses")
    public String myCourses(Model model, Principal principal) {
        Student student = studentRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        var enrollments = enrollmentService.getEnrollmentsByStudent(student);
        model.addAttribute("enrollments", enrollments);
        return "enroll/my-courses";
    }
}
