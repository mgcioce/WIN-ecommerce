package com.example.ecommercesite.controller;

import com.example.ecommercesite.model.Product;
import com.example.ecommercesite.model.User;
import com.example.ecommercesite.service.ProductService;
import com.example.ecommercesite.service.UserService;
import com.example.ecommercesite.util.CartItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@ControllerAdvice
public class CartController {
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    CartItemDTO cartItemDTO;

    @ModelAttribute("loggedInUser")
    public User loggedInUser() {
        return userService.getLoggedInUser();
    }

    private CartItemDTO cartReturnObject() {
        cartItemDTO.setProductMap(loggedInUser().getCart());
        return cartItemDTO;
    }

    @ModelAttribute("cart")
    public Map<Product, Integer> cart() {
        User user = loggedInUser();
        if(user == null) return null;
        System.out.println("Getting cart");
        return user.getCart();
    }

    /**
     * Puts an empty list in the model that thymeleaf can use to sum up the cart total.
     */
    @ModelAttribute("list")
    public List<Double> list() {
        return new ArrayList<>();
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        model.addAttribute("cartReturnObject",cartReturnObject());
        return "cart";
    }

    @PostMapping("/cart")
    public String addToCart(@RequestParam long id, Model model) {
        Product p = productService.findById(id);
        setQuantity(p, cart().getOrDefault(p, 0) + 1);
        model.addAttribute("cartReturnObject",cartReturnObject());
        return "cart";
    }

    @PatchMapping("/cart")
    public String updateQuantities(@RequestParam long[] id, @RequestParam int[] quantity, Model model) {
        for(int i = 0; i < id.length; i++) {
            Product p = productService.findById(id[i]);
            setQuantity(p, quantity[i]);
        }
        model.addAttribute("cartReturnObject",cartReturnObject());
        return "cart";
    }

    @DeleteMapping("/cart")
    public String removeFromCart(@RequestParam long id, Model model) {
        Product p = productService.findById(id);
        setQuantity(p, 0);
        model.addAttribute("cartReturnObject",cartReturnObject());
        return "cart";
    }

    private void setQuantity(Product p, int quantity) {
        if(quantity > 0)
            cart().put(p, quantity);
        else
            cart().remove(p);

        userService.updateCart();
    }
}

