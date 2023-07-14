package com.webshop.assignment.shopping;

public abstract class Payment {
    private String paymentId;
    private String paymentType;
    private String paymentStatus;

    public Payment() {
    }

    public Payment(String paymentId, String paymentType, String paymentStatus) {
        this.paymentId = paymentId;
        this.paymentType = paymentType;
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    void makePayment() {
        throw new UnsupportedOperationException("Unimplemented method 'makePayment'");
    }
}