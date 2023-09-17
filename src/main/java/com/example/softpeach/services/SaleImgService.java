package com.example.softpeach.services;

import com.example.softpeach.models.SaleImg;
import com.example.softpeach.repositories.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleImgService {
    private final SaleRepository saleRepository;

    public List<SaleImg> listSales() {
        return saleRepository.findAll();
    }

    public void saveSale(SaleImg saleImg) throws IOException {
        if (!saleRepository.existsByName(saleImg.getName())){
            saleRepository.save(saleImg);
        }
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }

    public SaleImg getProductById(Long id) {
        return saleRepository.findById(id).orElse(null);
    }
}