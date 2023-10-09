package com.example.softpeach.controllers;

import com.example.softpeach.models.Warehouse;
import com.example.softpeach.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping("")
    public String showWarehouse(Model model) {
        model.addAttribute("warehouse", warehouseService.listProducts());
        return "warehouse";
    }

    @GetMapping("/add/{id}")
    public String addWarehouse(@PathVariable Long id, Model model) {
        Warehouse warehouse = warehouseService.getWarehouseById(id);
        model.addAttribute("warehouseForm", warehouse);
        return "add_warehouse";
    }

    @PostMapping("/add/{id}")
    public String addWarehouse(@PathVariable Long id, @ModelAttribute("warehouseForm") Warehouse warehouseForm) {
        warehouseService.changeWarehouse(id, warehouseForm);
        return "redirect:/warehouse";
    }
}