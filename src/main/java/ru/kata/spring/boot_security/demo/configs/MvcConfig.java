package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/access-denied-error").setViewName("access-denied-error");
        registry.addViewController("/login").setViewName("redirect:/");
        registry.addViewController("/admin").setViewName("redirect:/admin/table");
    }
}
