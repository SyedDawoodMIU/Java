package service;

import dao.AddressDAO;
import dao.CustomerInfoDAO;
import dao.OrderDAO;
import dao.PaymentDAO;
import shopping.Address;
import shopping.CustomerInfo;
import shopping.Order;
import shopping.Payment;

public class OrderService {
    private OrderDAO orderDAO;
    private PaymentDAO paymentDAO;
    private CustomerInfoDAO customerInfoDAO;
    private AddressDAO addressDAO;

    public OrderService() {
        orderDAO = new OrderDAO();
        paymentDAO = new PaymentDAO();
        customerInfoDAO = new CustomerInfoDAO();
        addressDAO = new AddressDAO();
    }

    public Order getOrderById(String orderId) {
        return orderDAO.getById(orderId);
    }

    public void saveOrder(Order order) {
        orderDAO.save(order);
    }

    public void updateOrder(Order order) {
        orderDAO.update(order);
    }

    public void deleteOrder(Order order) {
        orderDAO.delete(order);
    }

    public void addPaymentToOrder(String orderId, Payment payment) {
        Order order = orderDAO.getById(orderId);
        if (order != null) {
            order.addPayment(payment);
        }
    }

    public void addCustomerInfoToOrder(String orderId, CustomerInfo customerInfo) {
        Order order = orderDAO.getById(orderId);
        if (order != null) {
            order.addCustomerInfo(customerInfo);
        }
    }

    public void addShippingAddressToOrder(String orderId, Address address) {
        Order order = orderDAO.getById(orderId);
        if (order != null) {
            order.addShippingAddress(address);
        }
    }

    public void addBillingAddressToOrder(String orderId, Address address) {
        Order order = orderDAO.getById(orderId);
        if (order != null) {
            order.addBillingAddress(address);
        }
    }

    public void placeOrder(String orderId) {
        Order order = orderDAO.getById(orderId);
        if (order != null) {
            order.placeOrder();
        }
    }
}