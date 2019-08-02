package com.example.ecommercesite.controller;

import com.example.ecommercesite.model.ChargeRequest;
import com.example.ecommercesite.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Set;

@Controller
public class CheckoutController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    private int calculateAmount(Map<Product, Integer> cart) {
        double sum = 0;
        Set<Map.Entry<Product,Integer>> cartContents = cart.entrySet();
        for(Map.Entry<Product,Integer> e : cartContents) {
            Product p = e.getKey();
            Integer i = e.getValue();
            sum += p.getPrice() * i.intValue();
        }
        return (int) sum * 100;
    }
    @RequestMapping("/checkout")
    public String checkout(Map<Product,Integer> cart, Model model) {
        model.addAttribute("amount", calculateAmount(cart)); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);
        return "checkout";
    }
}
