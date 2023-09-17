package com.example.softpeach.controllers;

import com.example.softpeach.models.Category;
import com.example.softpeach.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/add_category")
    public String addCategory(Model model) {
        model.addAttribute("categoryForm", new Category());
        return "add_category";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/add_category")
    public String addCategory(@ModelAttribute("categoryForm") Category categoryForm) {
        categoryService.saveCategory(categoryForm);
        return "redirect:/admin/add_category";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/delete_category")
    public String deleteCategory(Model model) {
        model.addAttribute("categories", categoryService.listCategories());
        model.addAttribute("deleteCategory", new Category());
        return "delete_category";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/delete_category")
    public String deleteCategory(@ModelAttribute("deleteCategory") Category category) {
        categoryService.deleteCategory(category.getId());
        return "redirect:/admin/delete_category";
    }
}
