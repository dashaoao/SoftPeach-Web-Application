package com.example.softpeach.repositories;

import com.example.softpeach.models.Product;
import com.example.softpeach.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Warehouse getByProduct(Product product);
    Boolean existsByProduct(Product product);
}