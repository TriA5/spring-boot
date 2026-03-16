package com.example.buoi5.service.impl;

import com.example.buoi5.model.Course;
import com.example.buoi5.repository.CourseRepository;
import com.example.buoi5.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Override
    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Page<Course> searchByName(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return courseRepository.findAll(pageable);
        }
        return courseRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course save(Course course, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" +
                        StringUtils.cleanPath(imageFile.getOriginalFilename());
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Files.copy(imageFile.getInputStream(),
                        uploadPath.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);
                course.setImage("/uploads/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store image file", e);
            }
        }
        return courseRepository.save(course);
    }

    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}
