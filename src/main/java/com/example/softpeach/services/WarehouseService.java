package com.example.softpeach.services;

import com.example.softpeach.models.Product;
import com.example.softpeach.models.Warehouse;
import com.example.softpeach.repositories.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public List<Warehouse> listProducts () {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    public void changeWarehouse(Long id, Warehouse warehouse){
        Warehouse updateWarehouse = warehouseRepository.findById(id).orElse(null);
        assert updateWarehouse != null;
        updateWarehouse.setCount(warehouse.getCount());
        warehouseRepository.save(updateWarehouse);
    }

    public Integer getCountOfProduct(Product product){
        Warehouse warehouse = warehouseRepository.getByProduct(product);
        return warehouse.getCount();
    }

    public void reduceProduct(Product product, int cnt){
        Warehouse warehouse = warehouseRepository.getByProduct(product);
        if (warehouse.getCount()>=cnt){
            warehouse.setCount(warehouse.getCount()-cnt);
            warehouseRepository.save(warehouse);
        }
    }

    public void increaseProduct(Product product, int cnt){
        Warehouse warehouse = warehouseRepository.getByProduct(product);
        warehouse.setCount(warehouse.getCount()+cnt);
        warehouseRepository.save(warehouse);
    }
}