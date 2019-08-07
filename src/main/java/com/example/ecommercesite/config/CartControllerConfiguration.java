package com.example.ecommercesite.config;

import com.example.ecommercesite.util.CartItemDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartControllerConfiguration {

    @Bean
    public CartItemDTO getCartItemDTO() {
        return new CartItemDTO();
    }
}
