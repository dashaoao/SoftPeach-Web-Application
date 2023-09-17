package com.example.softpeach.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    private String name;
    private String comm;
    private String email;
    private String phone;

    @Override
    public String toString() {
        return "Заказ от: " + name + "\n"
                + email + "\n"
                + phone + "\n"
                + "Комментарий к заказу: " + comm;
    }
}
