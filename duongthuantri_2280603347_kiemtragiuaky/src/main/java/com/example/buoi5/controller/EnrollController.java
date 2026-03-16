package com.example.buoi5.controller;

import com.example.buoi5.model.Student;
import com.example.buoi5.repository.StudentRepository;
import com.example.buoi5.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/enroll")
@RequiredArgsConstructor
public class EnrollController {

    private final EnrollmentService enrollmentService;
    private final StudentRepository studentRepository;

    @PostMapping("/{courseId}")
    public String enroll(@PathVariable Long courseId,
                         Principal principal,
                         RedirectAttributes redirectAttributes) {
        try {
            Student student = studentRepository.findByUsername(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            enrollmentService.enroll(student, courseId);
            redirectAttributes.addFlashAttribute("success", "Đăng ký học phần thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/home";
    }
}
