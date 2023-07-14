package com.webshop.assignment.shopping;

public class PayPalPayment implements Payment {
    private String id;
    private String currency;

    public PayPalPayment(String id, String currency) {
        this.id = id;
        this.currency = currency;

    }

    public String getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public void makePayment() {

    }
}