package com.webshop.assignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.webshop.assignment.dao.OrderDAO;
import com.webshop.assignment.dao.PaymentDAO;
import com.webshop.assignment.service.OrderService;
import com.webshop.assignment.service.PaymentService;
import com.webshop.assignment.shopping.Address;
import com.webshop.assignment.shopping.CustomerInfo;
import com.webshop.assignment.shopping.Order;
import com.webshop.assignment.shopping.Payment;

public class PaymentServiceimpl implements PaymentService {
    @Autowired
    private PaymentDAO paymentRepository;

    @Override
    public Payment getPaymentById(String paymentId) {

        return paymentRepository.findById(paymentId).orElse(null);
    }

    @Override
    public void savePayment(Payment payment) {

        paymentRepository.save(payment);
    }

    @Override
    public void updatePayment(Payment payment) {

        if (paymentRepository.existsById(payment.getPaymentId())) {
            paymentRepository.save(payment);
        } else {
            throw new IllegalArgumentException("Payment does not exist");
        }
    }

    @Override
    public void deletePayment(Payment payment) {
      
        paymentRepository.delete(payment);
    }

}