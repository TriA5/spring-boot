package com.example.buoi5.controller;

import com.example.buoi5.model.Student;
import com.example.buoi5.repository.StudentRepository;
import com.example.buoi5.service.CourseService;
import com.example.buoi5.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final StudentRepository studentRepository;

    @GetMapping({ "/", "/home" })
    public String home(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, 5);
        var coursePage = courseService.searchByName(keyword, pageable);

        model.addAttribute("coursePage", coursePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("keyword", keyword);

        if (userDetails != null) {
            Student student = studentRepository.findByUsername(userDetails.getUsername()).orElse(null);
            if (student != null) {
                boolean isStudent = student.getRoles().stream()
                        .anyMatch(r -> r.getName().equals("STUDENT"));
                if (isStudent) {
                    Set<Long> enrolledIds = enrollmentService.getEnrollmentsByStudent(student)
                            .stream()
                            .map(e -> e.getCourse().getId())
                            .collect(Collectors.toSet());
                    model.addAttribute("enrolledIds", enrolledIds);
                }
            }
        }

        return "home";
    }
}
