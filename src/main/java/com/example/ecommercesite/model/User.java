package com.example.ecommercesite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @OneToMany
    private Set<DeliveryAddress> deliveryAddressSet;
    @OneToMany
    private Set<PaymentMethod> paymentMethodSet;
}
