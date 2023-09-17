package com.example.softpeach.controllers;

import com.example.softpeach.models.Purchase;
import com.example.softpeach.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/purchase")
@Controller
@RequiredArgsConstructor
public class PurchaseController {
    public final EmailService emailService;

    @GetMapping("")
    public String purchase(Model model) {
        model.addAttribute("purchaseForm", new Purchase());
        return "purchase";
    }

    @PostMapping("")
    public String purchase(@ModelAttribute("purchaseForm") Purchase purchaseForm) {
        emailService.sendMessage("fudomashka@yandex.ru", "Запрос на заказ.", purchaseForm.toString());
        return "redirect:purchase/success";
    }
}
