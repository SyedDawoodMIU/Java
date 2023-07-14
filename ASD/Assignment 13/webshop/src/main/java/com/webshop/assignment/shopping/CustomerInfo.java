package com.webshop.assignment.shopping;

public class CustomerInfo {
    private String name;
    private String email;
    private String phone;

   
    public CustomerInfo(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {

        return phone;
    }

    
}