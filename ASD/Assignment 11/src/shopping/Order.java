package shopping;

public class Order {
    private String orderNumber;
    private Payment payment;
    private CustomerInfo customerInfo;
    private Address shippingAddress;
    private Address billingAddress;

    public void addPayment(Payment payment) {
        this.payment = payment;
    }

    public void addCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public void addShippingAddress(Address address) {
        this.shippingAddress = address;
    }

    public void addBillingAddress(Address address) {
        this.billingAddress = address;
    }

    public String getContent() {
        // Implementation to get order content as a string
        return "";
    }

    public void placeOrder() {
        // Implementation for placing the order
    }
}