package com.webshop.assignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.webshop.assignment.dao.OrderDAO;
import com.webshop.assignment.service.OrderService;
import com.webshop.assignment.shopping.Address;
import com.webshop.assignment.shopping.CustomerInfo;
import com.webshop.assignment.shopping.Order;
import com.webshop.assignment.shopping.Payment;

public class OrderServiceimpl implements OrderService {
    @Autowired
    private OrderDAO orderRepository;

    @Override
    public Order getOrderById(String orderId) {

        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public void saveOrder(Order order) {

        orderRepository.save(order);
    }

    @Override
    public void updateOrder(Order order) {

        if (orderRepository.existsById(order.getOrderNumber())) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order does not exist");
        }
    }

    @Override
    public void deleteOrder(Order order) {

        orderRepository.delete(order);
    }

    @Override
    public void addPaymentToOrder(String orderId, Payment payment) {

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.addPayment(payment);
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order does not exist");
        }
    }

    @Override
    public void addCustomerInfoToOrder(String orderId, CustomerInfo customerInfo) {

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.addCustomerInfo(customerInfo);
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order does not exist");
        }
    }

    @Override
    public void addShippingAddressToOrder(String orderId, Address address) {

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.addShippingAddress(address);
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order does not exist");
        }
    }

    @Override
    public void addBillingAddressToOrder(String orderId, Address address) {

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.addBillingAddress(address);
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order does not exist");
        }
    }

    @Override
    public void placeOrder(String orderId) {

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.placeOrder();
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order does not exist");
        }
    }

}