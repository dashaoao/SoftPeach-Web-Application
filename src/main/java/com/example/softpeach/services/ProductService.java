package com.example.softpeach.services;

import com.example.softpeach.models.Image;
import com.example.softpeach.models.Product;
import com.example.softpeach.repositories.ImageRepository;
import com.example.softpeach.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void saveProduct(Product product, MultipartFile file) throws IOException {
        if (!productRepository.existsByTitle(product.getTitle())){
            Image image;
            if (file.getSize() != 0) {
                image = toImageEntity(file);
                product.addImage(image);
            }
            log.info("Saving new product '{}'", product.getTitle());
            productRepository.save(product);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void changeProduct(Long id, Product product, MultipartFile file) throws IOException {
        Product updateProduct = productRepository.findById(id).orElse(null);
        Image image;
        if (file.getSize() != 0) {
            assert updateProduct != null;
            imageRepository.deleteById(updateProduct.getImage().getId());
            image = toImageEntity(file);
            updateProduct.addImage(image);
        }
        assert updateProduct != null;
        updateProduct.setPrice(product.getPrice());
        updateProduct.setCategory(product.getCategory());
        updateProduct.setTitle(product.getTitle());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setWeight(product.getWeight());
        log.info("Updating product '{}'", updateProduct.getTitle());
        productRepository.save(updateProduct);
    }
}
