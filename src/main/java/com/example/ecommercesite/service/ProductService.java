package com.example.ecommercesite.service;

import com.example.ecommercesite.model.Product;
import com.example.ecommercesite.repository.ProductRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product findById (long id) {
        return productRepository.findById(id);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    public List<Product> findByBrandAndOrCategory(String brand, String category) {
        if (brand != null && category != null) {
            return productRepository.findByBrandAndCategory(brand,category);
        } else {
            if (brand == null && category == null) {
                return (List<Product>) productRepository.findAll();
            }
            if (brand == null) {
                return productRepository.findByCategory(category);
            } else {
                return productRepository.findByBrand(brand);
            }
        }
    }
}
