package com.example.softpeach.repositories;

import com.example.softpeach.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByTitle(String title);
}
