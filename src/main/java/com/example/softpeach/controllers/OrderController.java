package com.example.softpeach.controllers;

import com.example.softpeach.models.Order;
import com.example.softpeach.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public String showOrders(Model model) {
        model.addAttribute("orders", orderService.listOrders());
        return "orders";
    }

    @GetMapping("/orders/more/{id}")
    public String moreOrder(Model model, @PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        model.addAttribute(order);
        return "more";
    }

    @PostMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
