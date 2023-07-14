package com.webshop.assignment.service;

import com.webshop.assignment.shopping.Address;
import com.webshop.assignment.shopping.CustomerInfo;
import com.webshop.assignment.shopping.Order;
import com.webshop.assignment.shopping.Payment;

public interface OrderService {
   
    public Order getOrderById(String orderId);

    public void saveOrder(Order order);
    public void updateOrder(Order order);

    public void deleteOrder(Order order);

    public void addPaymentToOrder(String orderId, Payment payment);

    public void addCustomerInfoToOrder(String orderId, CustomerInfo customerInfo);

    public void addShippingAddressToOrder(String orderId, Address address);

    public void addBillingAddressToOrder(String orderId, Address address);
    public void placeOrder(String orderId);
}