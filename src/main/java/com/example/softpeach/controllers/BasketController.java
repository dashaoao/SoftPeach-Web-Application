package com.example.softpeach.controllers;

import com.example.softpeach.models.Basket;
import com.example.softpeach.models.Order;
import com.example.softpeach.models.Product;
import com.example.softpeach.services.OrderService;
import com.example.softpeach.services.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@SessionAttributes("basket")
public class BasketController {
    private static final String BASKET_SESSION_KEY = "basketProducts";
    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("/basket")
    public String showBasket(Model model, HttpSession session) {
        Basket basket = getBasket(session);
        model.addAttribute("basket", basket);
        model.addAttribute("orderForm", new Order());
        return "basket";
    }

    @PostMapping("/basket/order")
    public String order(@ModelAttribute("orderForm") Order order,
                        @ModelAttribute("basket") Basket basket,
                        HttpSession session) {
        order.setInformation(new ArrayList<>());
        order.setAmount(basket.getAmount());
        for (Product product : basket.getProducts()){
            order.getInformation().add(product.getTitle());
        }
        orderService.saveOrder(order);
        session.invalidate();
        return "redirect:/order/complete";
    }

    @PostMapping("/basket/add/{id}")
    public String addProduct(@PathVariable Long id, HttpSession session) {
        Basket basket = getBasket(session);
        basket.addProduct(productService.getProductById(id));
        saveBasket(basket, session);
        return "redirect:/products";
    }

    @PostMapping("/basket/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session) {
        Basket basket = getBasket(session);
        basket.deleteProduct(id);
        saveBasket(basket, session);
        return "redirect:/basket";
    }

    private Basket getBasket(HttpSession session) {
        Basket basket = (Basket) session.getAttribute(BASKET_SESSION_KEY);
        if (basket == null) {
            basket = new Basket();
            session.setAttribute(BASKET_SESSION_KEY, basket);
        }
        return basket;
    }

    private void saveBasket(Basket basket, HttpSession session) {
        session.setAttribute(BASKET_SESSION_KEY, basket);
    }
}
