package com.example.softpeach.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Data
@NoArgsConstructor
//@Embeddable
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Basket {
//    @ElementCollection
    private List<List<Object>> products = new ArrayList<>();
    private int amount = 0;
    private int quantity = 0;
    private Boolean success = true;

    public List<Object> getProduct(Product product){
        for(List<Object> objects : products){
            Product prod = (Product) objects.get(0);
            if (prod.getId().equals(product.getId())){
                return objects;
            }
        }
        List<Object> prod = new ArrayList<>();
        prod.add(product);
        prod.add(0);
        prod.add(true);
        return prod;
    }

    public void addProduct(Product product) {
        for (List<Object> objects : products) {
            Product prod = (Product) objects.get(0);
            if (prod.getId().equals(product.getId())) {
                objects.add(1, (int) objects.get(1) + 1);
                quantity += 1;
                amount += product.getPrice();
                return;
            }
        }

        List<Object> newProduct = new ArrayList<>();
        newProduct.add(product);
        newProduct.add(1);
        newProduct.add(true);
        products.add(newProduct);
        quantity += 1;
        amount += product.getPrice();
    }

    public void setFalseProduct(Long id){
        for (List<Object> objects : products) {
            Product prod = (Product) objects.get(0);
            if ((Objects.equals(prod.getId(), id))) {
                objects.add(2, false);
                return;
            }
        }
    }

    public void setTrueProduct(Long id){
        for (List<Object> objects : products) {
            Product prod = (Product) objects.get(0);
            if ((Objects.equals(prod.getId(), id))) {
                objects.add(2, true);
                return;
            }
        }
    }

    public void deleteProduct(Long id) {
        for (List<Object> objects : products) {
            Product prod = (Product) objects.get(0);
            if ((Objects.equals(prod.getId(), id))) {
                amount -= (int) objects.get(1)*prod.getPrice();
                quantity -= (int) objects.get(1);
                products.remove(objects);
                return;
            }
        }
    }

    public void deleteAllProducts() {
        products.clear();
        amount = 0;
        quantity = 0;
    }

    public void oneMoreProduct(Long id) {
        for (List<Object> objects : products) {
            Product prod = (Product) objects.get(0);
            if ((Objects.equals(prod.getId(), id))) {
                amount += prod.getPrice();
                quantity += 1;
                objects.add(1, (int) objects.get(1) + 1);
                return;
            }
        }
    }

    public void oneLessProduct(Long id) {
        for (List<Object> objects : products) {
            Product prod = (Product) objects.get(0);
            if ((Objects.equals(prod.getId(), id))) {
                if ((int)objects.get(1)==1) return;
                amount -= prod.getPrice();
                quantity -= 1;
                objects.add(1, (int) objects.get(1) - 1);
                return;
            }
        }
    }
}
