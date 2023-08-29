package com.tomlegodais.api.config;

import com.tomlegodais.shared.service.CheckoutService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SharedServiceConfig {

    @Bean
    public CheckoutService checkoutService() {
        return new CheckoutService();
    }
}
