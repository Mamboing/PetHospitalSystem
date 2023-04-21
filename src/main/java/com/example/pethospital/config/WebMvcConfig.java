package com.example.pethospital.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String route = "/phFiles/**";
        String location = System.getProperty("user.dir") + File.separator + "petHospitalFiles";
        registry.addResourceHandler(route).addResourceLocations("file:" + location + File.separator);
    }
}
