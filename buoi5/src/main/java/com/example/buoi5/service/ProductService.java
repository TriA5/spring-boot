package com.example.buoi5.service;

import com.example.buoi5.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    void deleteById(Long id);

    /**
     * Tìm kiếm + lọc + phân trang + sắp xếp
     * 
     * @param keyword    từ khóa tìm kiếm tên sản phẩm (nullable)
     * @param categoryId lọc theo danh mục (nullable)
     * @param page       số trang (0-based)
     * @param size       số sản phẩm mỗi trang
     * @param sortField  trường sắp xếp (vd: "price")
     * @param sortDir    hướng sắp xếp ("asc" hoặc "desc")
     */
    Page<Product> findPaginated(String keyword, Long categoryId, int page, int size,
            String sortField, String sortDir);

    Product createProduct(Product product, Long categoryId, MultipartFile imageFile) throws IOException;

    Product updateProduct(Long id, Product product, Long categoryId, MultipartFile imageFile) throws IOException;
}
