package com.example.buoi5.service;

import com.example.buoi5.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Optional<Category> findById(Long id);

    Category save(Category category);

    void delete(Long id);
}
