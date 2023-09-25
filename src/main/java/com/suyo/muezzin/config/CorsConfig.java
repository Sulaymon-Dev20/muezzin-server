package com.suyo.muezzin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${spring.application.cors:false}")
    private boolean corsStatus;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Add your mapping pattern here
            .allowedOrigins("*") // Allow all origins
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
            .allowedHeaders("*") // Allow all headers
            .allowCredentials(corsStatus); // Allow credentials
    }
}
