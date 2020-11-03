package com.vironit.onlinepharmacy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "com.vironit.onlinepharmacy")
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
}
