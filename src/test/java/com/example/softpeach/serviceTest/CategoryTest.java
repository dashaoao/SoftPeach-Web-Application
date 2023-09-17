package com.example.softpeach.serviceTest;

import com.example.softpeach.models.Category;
import com.example.softpeach.repositories.CategoryRepository;
import com.example.softpeach.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryTest {
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    public void testListCategories() {
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category("Category 1");
        Category category2 = new Category("Category 2");
        categories.add(category1);
        categories.add(category2);

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.listCategories();

        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getName());
        assertEquals("Category 2", result.get(1).getName());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testSaveCategory() {
        Category category = new Category("Category 1");

        when(categoryRepository.existsByName("Category 1")).thenReturn(false);

        categoryService.saveCategory(category);

        verify(categoryRepository, times(1)).existsByName("Category 1");
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testDeleteCategory() {
        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }
}
