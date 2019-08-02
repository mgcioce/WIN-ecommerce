package com.example.ecommercesite.util;

public enum CardProvider {

    AMEX("American Express"),
    VISA("Visa"),
    MASTERCARD("Mastercard"),
    DISCOVER("Discover"),
    DINERS_CLUB("Diner's Club"),
    PAYPAL("Paypal");

    private  String provider;

    CardProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return this.provider;
    }
}
