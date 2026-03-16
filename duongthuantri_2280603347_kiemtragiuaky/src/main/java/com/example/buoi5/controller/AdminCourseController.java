package com.example.buoi5.controller;

import com.example.buoi5.model.Course;
import com.example.buoi5.service.CategoryService;
import com.example.buoi5.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/courses")
@RequiredArgsConstructor
public class AdminCourseController {

    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "admin/courses/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/courses/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Course course,
                         BindingResult bindingResult,
                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/courses/create";
        }
        courseService.save(course, imageFile);
        redirectAttributes.addFlashAttribute("success", "Tạo course thành công!");
        return "redirect:/admin/courses";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        model.addAttribute("course", course);
        model.addAttribute("categories", categoryService.findAll());
        return "admin/courses/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute Course course,
                         BindingResult bindingResult,
                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/courses/edit";
        }
        course.setId(id);
        if (imageFile == null || imageFile.isEmpty()) {
            String existingImage = courseService.findById(id).map(Course::getImage).orElse(null);
            course.setImage(existingImage);
        }
        courseService.save(course, imageFile);
        redirectAttributes.addFlashAttribute("success", "Cập nhật course thành công!");
        return "redirect:/admin/courses";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Xóa course thành công!");
        return "redirect:/admin/courses";
    }
}
