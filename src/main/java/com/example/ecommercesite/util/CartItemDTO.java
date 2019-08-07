package com.example.ecommercesite.util;

import com.example.ecommercesite.model.Product;

import java.util.Map;

public class CartItemDTO {

    private Map<Product,Integer> productMap;

    public CartItemDTO(Map<Product,Integer> map) {
        this.productMap = map;
    }

    public CartItemDTO() {
        this.productMap = null;
    }

    public void setProductMap(Map<Product, Integer> productMap) {
        this.productMap = productMap;
    }

    public Map<Product, Integer> getProductMap() {
        return productMap;
    }
}
