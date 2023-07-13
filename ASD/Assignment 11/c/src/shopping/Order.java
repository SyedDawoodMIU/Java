package shopping;

public class Order {
    private String orderNumber;
    private Payment payment;
    private CustomerInfo customerInfo;
    private Address shippingAddress;
    private Address billingAddress;


    public String getOrderNumber() {
        return orderNumber;
    }

    public Payment getPayment() {
        return payment;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }



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
        
        return "";
    }

    public void placeOrder() {
        
    }
}