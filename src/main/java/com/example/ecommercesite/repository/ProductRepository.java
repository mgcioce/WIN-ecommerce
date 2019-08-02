package com.example.ecommercesite.repository;

import com.example.ecommercesite.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository <Product, Integer> {
    List<Product> findByBrandAndCategory(String brand, String category);
    List<Product> findByBrand(String brand);
    List<Product> findByCategory(String category);

    Product findById(long id);

    @Query("SELECT DISTINCT p.brand FROM Product p")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findDistinctCategories();
}
