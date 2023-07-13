package service;

import dao.PaymentDAO;
import shopping.Payment;

public class PaymentService {
    private PaymentDAO paymentDAO;

    public PaymentService() {
        paymentDAO = new PaymentDAO();
    }

    public Payment getPaymentById(String paymentId) {
        return paymentDAO.getById(paymentId);
    }

    public void savePayment(Payment payment) {
        paymentDAO.save(payment);
    }

    public void updatePayment(Payment payment) {
        paymentDAO.update(payment);
    }

    public void deletePayment(Payment payment) {
        paymentDAO.delete(payment);
    }
}