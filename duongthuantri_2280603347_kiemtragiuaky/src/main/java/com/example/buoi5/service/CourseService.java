package com.example.buoi5.service;

import com.example.buoi5.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Page<Course> findAll(Pageable pageable);

    Page<Course> searchByName(String keyword, Pageable pageable);

    List<Course> findAll();

    Optional<Course> findById(Long id);

    Course save(Course course, MultipartFile imageFile);

    void delete(Long id);
}
