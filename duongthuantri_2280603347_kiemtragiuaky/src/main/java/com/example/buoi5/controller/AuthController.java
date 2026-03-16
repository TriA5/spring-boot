package com.example.buoi5.controller;

import com.example.buoi5.model.Student;
import com.example.buoi5.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final StudentService studentService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("student", new Student());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute Student student,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        try {
            studentService.register(student);
            redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}
