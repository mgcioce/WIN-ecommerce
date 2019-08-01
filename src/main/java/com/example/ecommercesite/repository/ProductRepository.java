package com.example.ecommercesite.repository;

import com.example.ecommercesite.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository <Product, Integer> {
    List<Product> findByBrandAndCategory(String brand, String category);
    List<Product> findByBrand(String brand);
    List<Product> findByCategory(String category);

    Product findById(long id);
}
