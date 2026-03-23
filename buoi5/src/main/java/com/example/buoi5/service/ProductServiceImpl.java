package com.example.buoi5.service;

import com.example.buoi5.model.Category;
import com.example.buoi5.model.Product;
import com.example.buoi5.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    private static final String UPLOAD_DIR = "static/uploads/";

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findPaginated(String keyword, Long categoryId,
            int page, int size,
            String sortField, String sortDir) {
        // Xây dựng Sort
        Sort sort = (sortDir != null && sortDir.equalsIgnoreCase("desc"))
                ? Sort.by(sortField != null ? sortField : "id").descending()
                : Sort.by(sortField != null ? sortField : "id").ascending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // Dùng query tổng hợp tìm kiếm + lọc category
        String kw = (keyword != null && !keyword.isBlank()) ? keyword : null;
        Long catId = (categoryId != null && categoryId > 0) ? categoryId : null;
        return productRepository.findWithFilters(kw, catId, pageRequest);
    }

    @Override
    public Product createProduct(Product product, Long categoryId, MultipartFile imageFile) throws IOException {
        Category category = categoryService.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
        product.setCategory(category);
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImage(saveImage(imageFile));
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product updated, Long categoryId, MultipartFile imageFile)
            throws IOException {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        existing.setName(updated.getName());
        existing.setPrice(updated.getPrice());
        existing.setDescription(updated.getDescription());
        Category category = categoryService.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
        existing.setCategory(category);
        if (imageFile != null && !imageFile.isEmpty()) {
            existing.setImage(saveImage(imageFile));
        }
        return productRepository.save(existing);
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        String originalFilename = imageFile.getOriginalFilename() != null
                ? imageFile.getOriginalFilename()
                : "image";
        String filename = System.currentTimeMillis() + "_" + StringUtils.cleanPath(originalFilename);
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(imageFile.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + filename;
    }
}
