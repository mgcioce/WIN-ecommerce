package com.example.ecommercesite.controller;

import com.example.ecommercesite.model.Product;
import com.example.ecommercesite.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/product/{id}")
    public String getProductPage(@PathVariable long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        return "products";
    }

    @RequestMapping(value = "/product", method = {RequestMethod.POST, RequestMethod.PUT})
    public String createOrUpdate(@Valid Product product) {
        productService.save(product);
        return "redirect:/product/" + product.getId();
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) String category,
                         @RequestParam(required = false) String brand,
                         Model model) {
        List<Product> filtered = productService.findByBrandAndOrCategory(brand, category);
        model.addAttribute("products", filtered); // Overrides the @ModelAttribute above.
        return "main";
    }
}
