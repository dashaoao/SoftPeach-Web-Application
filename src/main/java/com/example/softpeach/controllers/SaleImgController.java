package com.example.softpeach.controllers;

import com.example.softpeach.models.Product;
import com.example.softpeach.models.SaleImg;
import com.example.softpeach.repositories.SaleRepository;
import com.example.softpeach.services.OrderService;
import com.example.softpeach.services.SaleImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class SaleImgController {

    private final SaleImgService saleImgService;

    @GetMapping("/sale")
    public String sale(Model model) {
        model.addAttribute("sales", saleImgService.listSales());
        return "sale";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/add_sale")
    public String addSale(Model model) {
        model.addAttribute("saleForm", new SaleImg());
        return "add_sale";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/add_sale")
    public String addSale(@ModelAttribute("saleForm") SaleImg saleForm)
            throws IOException {
        saleImgService.saveSale(saleForm);
        return "redirect:/admin/add_sale";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/sale/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        saleImgService.deleteSale(id);
        return "redirect:/sale";
    }
}

