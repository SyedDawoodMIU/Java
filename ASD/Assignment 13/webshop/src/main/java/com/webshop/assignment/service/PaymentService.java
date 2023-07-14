package com.webshop.assignment.service;
import com.webshop.assignment.shopping.Payment;

public interface PaymentService {

    public Payment getPaymentById(String paymentId);

    public void savePayment(Payment payment);

    public void updatePayment(Payment payment);

    public void deletePayment(Payment payment);
}