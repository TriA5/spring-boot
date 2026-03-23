package com.example.buoi5.controller;

import com.example.buoi5.model.Product;
import com.example.buoi5.service.CategoryService;
import com.example.buoi5.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String list(@RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Page<Product> productPage = productService.findPaginated(
                keyword, categoryId, page, size, sortField, sortDir);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("pageSize", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("categories", categoryService.findAll());
        return "products/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        model.addAttribute("product", product);
        return "products/detail";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "products/create";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public String create(@ModelAttribute Product product,
            @RequestParam("categoryId") Long catId,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        productService.createProduct(product, catId, imageFile);
        return "redirect:/products";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        return "products/edit";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
            @ModelAttribute Product product,
            @RequestParam("categoryId") Long catId,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        productService.updateProduct(id, product, catId, imageFile);
        return "redirect:/products";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}
