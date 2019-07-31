package com.example.ecommercesite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class ProductController {

//    @Autowired
//    ProductService productService;

//    @GetMapping("/product/{id}")
//    public String getProductPage(@PathVariable(name = "id") Integer id, Model model) {
//        Product product = productService.findById(id);
//        model.addAttribute("product",product);
//        return "product";
//    }
//
//    @RequestMapping(value = "/product", method = {RequestMethod.POST, RequestMethod.PUT})
//    public String createOrUpdate(@Valid Product product) {
//        productService.save(product);
//        return "redirect:/product/" + product.getId();
//    }

//    @GetMapping("/filter")
//    public String filter(@RequestParam(required = false) String category,
//                         @RequestParam(required = false) String brand,
//                         Model model) {
//        List<Product> filtered = productService.findByBrandAndOrCategory(brand, category);
//        model.addAttribute("products", filtered); // Overrides the @ModelAttribute above.
//        return "main";
//    }
}
