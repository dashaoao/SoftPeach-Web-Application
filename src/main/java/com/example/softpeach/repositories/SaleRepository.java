package com.example.softpeach.repositories;

import com.example.softpeach.models.SaleImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<SaleImg, Long> {

    boolean existsByName(String name);
}
