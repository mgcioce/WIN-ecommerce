package com.example.ecommercesite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String streetNumberOrPOBoxNumber;
    private String streetOrPOBox;
    private String unitNumber;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    @ManyToOne
    private User user;
}
