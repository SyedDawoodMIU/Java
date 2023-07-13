
import shopping.*;
import dao.AddressDAO;
import dao.CustomerInfoDAO;
import dao.ProductCatalogueDAO;
import service.*;

public class App {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        PaymentService paymentService = new PaymentService();
        ShoppingCartService shoppingCartService = new ShoppingCartService();
        OrderService orderService = new OrderService();

        Product product1 = new Product("P001", "Product 1", 9.99);

        productService.saveProduct(product1);

        ProductCategory catalogue = new ProductCategory();

        catalogue.addProduct(product1);

        ProductCatalogueDAO catalogueDAO = new ProductCatalogueDAO();
        catalogueDAO.save(catalogue);

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(product1.getId(), 2);

        shoppingCartService.saveShoppingCart(cart);

        CustomerInfo customerInfo = new CustomerInfo("John", "Doe", "221211221");

        CustomerInfoDAO customerInfoDAO = new CustomerInfoDAO();
        customerInfoDAO.save(customerInfo);

        Address shippingAddress = new Address("Main Street", "New York", "10001");

        AddressDAO addressDAO = new AddressDAO();
        addressDAO.save(shippingAddress);

        PayPalPayment payPalPayment = new PayPalPayment("paypal-transaction-id-123", "USD");

        paymentService.savePayment(payPalPayment);

        Order placedOrder = shoppingCartService.checkout(cart.getId());
        placedOrder.addPayment(payPalPayment);
        placedOrder.addCustomerInfo(customerInfo);
        placedOrder.addShippingAddress(shippingAddress);
        orderService.placeOrder(placedOrder.getOrderNumber());
        orderService.saveOrder(placedOrder);

    }
}
