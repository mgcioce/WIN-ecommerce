package com.example.ecommercesite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.Map;
import javax.validation.constraints.NotNull;
import java.util.Collection;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @ElementCollection
    private Map<Product,Integer> cart;


    public Map<Product, Integer> getCart() {
        return this.cart;
    }

    public void setCart(Map<Product,Integer> cart) {
        this.cart = cart;
    }

    private String firstName;
    private String lastName;

    //    private List<Order> orderList;
//    @OneToMany
//    private Set<DeliveryAddress> deliveryAddressSet;
//    @OneToMany
//    private Set<PaymentMethod> paymentMethodSet;

    @Override
    public String getUsername() {
        return this.email;
    }

    @Transient
    private boolean accountNonExpired = true;
    @Transient
    private boolean accountNonLocked = true;
    @Transient
    private boolean credentialsNonExpired = true;
    @Transient
    private boolean enabled = true;
    @Transient
    private Collection<GrantedAuthority> authorities = null;

}
