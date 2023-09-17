package com.example.softpeach.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/auth").setViewName("auth");
        registry.addViewController("/admin/add_sale").setViewName("add_sale");
        registry.addViewController("/admin/logout").setViewName("logout");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/purchase/success").setViewName("success");
        registry.addViewController("/order/complete").setViewName("complete");
    }
}
