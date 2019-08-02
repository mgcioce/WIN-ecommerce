package com.example.ecommercesite.controller;

import com.example.ecommercesite.model.Product;
import com.example.ecommercesite.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@ControllerAdvice
public class MainController {

    @Autowired
    ProductService productService;

    @GetMapping(value = {"/","/home"})
    public String getHomepage(Model model) {
        model.addAttribute("products",productService.findAll());
        return "index";
    }

//    @GetMapping("/filter")
//    public String filter(@RequestParam(required = false) String category,
//                         @RequestParam(required = false) String brand,
//                         Model model) {
//        List<Product> filtered = productService.findByBrandAndOrCategory(brand, category);
//        model.addAttribute("products", filtered); // Overrides the @ModelAttribute above.
//        return "main";
//    }

    @GetMapping(value = "/about")
    public String getAboutPage() {
        return "about";
    }

//    @GetMapping(value = "/signin")
//    public String getSignInPage() {
//        return "signin";
//    }

    @ModelAttribute("products")
    public List<Product> products() {
        return productService.findAll();
    }

    @ModelAttribute("categories")
    public List<String> categories() {
        return productService.findDistinctCategories();
    }

    @ModelAttribute("brands")
    public List<String> brands() {
        return productService.findDistinctBrands();
    }
}
