package com.example.buoi5.config;

import com.example.buoi5.model.Category;
import com.example.buoi5.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryConverter implements Converter<String, Category> {

    private final CategoryRepository categoryRepository;

    @Override
    public Category convert(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }
        try {
            return categoryRepository.findById(Long.parseLong(id)).orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
