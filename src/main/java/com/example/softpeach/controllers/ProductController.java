package com.example.softpeach.controllers;


import com.example.softpeach.models.Product;
import com.example.softpeach.services.CategoryService;
import com.example.softpeach.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.listProducts());
        model.addAttribute("categories", categoryService.listCategories());
        return "products";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/add_product")
    public String addProduct(Model model) {
        model.addAttribute("categories", categoryService.listCategories());
        model.addAttribute("productForm", new Product());
        return "add_product";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/add_product")
    public String addProduct(@RequestParam("file") MultipartFile file,
                             @ModelAttribute("productForm") Product productForm)
            throws IOException {
        productService.saveProduct(productForm, file);
        return "redirect:/admin/add_product";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/products/update/{id}")
    public String updateProduct(@PathVariable Long id, Model model) {

        model.addAttribute("categories", categoryService.listCategories());
        model.addAttribute("productForm", productService.getProductById(id));
        return "update";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/products/update/{id}")
    public String updateProduct(@PathVariable Long id, @RequestParam("file") MultipartFile file,
                                @ModelAttribute("productForm") Product productForm)
            throws IOException {
        productService.changeProduct(id, productForm, file);

        return "redirect:/products";
    }
}
