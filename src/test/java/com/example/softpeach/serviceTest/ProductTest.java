package com.example.softpeach.serviceTest;

import com.example.softpeach.models.Image;
import com.example.softpeach.models.Product;
import com.example.softpeach.repositories.ProductRepository;
import com.example.softpeach.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    public ProductTest() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testListProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.listProducts();

        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testSaveProduct() throws IOException {
        Product product = new Product();
        product.setTitle("Test Product");

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());

        productService.saveProduct(product, file);

        verify(productRepository, times(1)).existsByTitle("Test Product");
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testDeleteProduct() {
        Long id = 1L;

        productService.deleteProduct(id);

        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetProductById() {
        Long id = 1L;
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(java.util.Optional.of(product));

        Product result = productService.getProductById(id);

        assertEquals(product, result);
        verify(productRepository, times(1)).findById(id);
    }
}