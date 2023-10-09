package com.example.softpeach.controllers;

import com.example.softpeach.models.Basket;
import com.example.softpeach.models.Order;
import com.example.softpeach.models.Product;
import com.example.softpeach.services.OrderService;
import com.example.softpeach.services.ProductService;
import com.example.softpeach.services.WarehouseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes("basket")
public class BasketController {
    private static final String BASKET_SESSION_KEY = "basketProducts";
    private final ProductService productService;
    private final OrderService orderService;
    private final WarehouseService warehouseService;

    @GetMapping("/basket")
    public String showBasket(Model model, HttpSession session) {
        Basket basket = getBasket(session);
        boolean check = false;
        for (List<Object> objects : basket.getProducts()){
            Product product = (Product) objects.get(0);
            int cntProduct = (int) objects.get(1);
            int cntWarehouse = warehouseService.getCountOfProduct(product);
            if (cntProduct>cntWarehouse){
                check=true;
                basket.setFalseProduct(product.getId());
            } else basket.setTrueProduct(product.getId());
        }
        basket.setSuccess(!check);
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

        boolean check = false;
        for (List<Object> objects : basket.getProducts()){
            Product product = (Product) objects.get(0);
            int cntProduct = (int) objects.get(1);
            int cntWarehouse = warehouseService.getCountOfProduct(product);
            if (cntProduct>cntWarehouse){
                check=true;
                basket.setFalseProduct(product.getId());
            }
        }

        if (check){
            basket.setSuccess(false);
            return "redirect:/basket";
        }

        for (List<Object> objects : basket.getProducts()){
            Product product = (Product) objects.get(0);
            int count = (int) objects.get(1);
            warehouseService.reduceProduct(product, count);
            order.getInformation().add(product.getTitle()+": количество позиций - "+count);
        }

        orderService.saveOrder(order);
        basket.setSuccess(true);
        session.invalidate();
        return "redirect:/order/complete";
    }

    @PostMapping("/basket/add/{id}")
    public String addProduct(@PathVariable Long id, HttpSession session) {
        Basket basket = getBasket(session);
        Product product = productService.getProductById(id);
        int count = warehouseService.getCountOfProduct(product);
        if (count > (int)basket.getProduct(product).get(1)){
            basket.addProduct(product);
            saveBasket(basket, session);
        }

        return "redirect:/products";
    }

    @PostMapping("/basket/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session) {
        Basket basket = getBasket(session);
        basket.deleteProduct(id);
//        if (basket.getProducts().isEmpty()){
//            basket.setSuccess(true);
//        }
        saveBasket(basket, session);
        return "redirect:/basket";
    }

    @PostMapping("/basket/more/{id}")
    public String moreProduct(@PathVariable Long id, HttpSession session) {
        Basket basket = getBasket(session);
        Product product = productService.getProductById(id);
        int count = warehouseService.getCountOfProduct(product);
        if (count > (int)basket.getProduct(product).get(1)){
            basket.oneMoreProduct(id);
            saveBasket(basket, session);
        }
        return "redirect:/basket";
    }

    @PostMapping("/basket/less/{id}")
    public String lessProduct(@PathVariable Long id, HttpSession session) {
        Basket basket = getBasket(session);
        basket.oneLessProduct(id);
        List <Object> product = basket.getProduct(productService.getProductById(id));
        int cntProduct = (int) product.get(1);
        int cntWarehouse = warehouseService.getCountOfProduct((Product) product.get(0));
        if (cntProduct<=cntWarehouse){
            basket.setTrueProduct(id);
        }
        saveBasket(basket, session);
        return "redirect:/basket";
    }

    @PostMapping("/basket/delete")
    public String deleteAllProducts(HttpSession session) {
        Basket basket = getBasket(session);
        basket.deleteAllProducts();
//        basket.setSuccess(true);
        saveBasket(basket, session);
        return "redirect:/basket";
    }

    private Basket getBasket(HttpSession session) {
        Basket basket = (Basket) session.getAttribute(BASKET_SESSION_KEY);
        if (basket == null) {
            basket = new Basket();
            session.setAttribute(BASKET_SESSION_KEY, basket);
            session.setMaxInactiveInterval(300);
        }
        return basket;
    }

    private void saveBasket(Basket basket, HttpSession session) {
        session.setAttribute(BASKET_SESSION_KEY, basket);
    }
}
