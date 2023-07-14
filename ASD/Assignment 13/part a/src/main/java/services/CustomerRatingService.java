package services;

import org.springframework.context.event.EventListener;

import customers.Customer;
import events.NewCustomerEvent;

public class CustomerRatingService {

    @EventListener
    public void handleNewCustomerEvent(NewCustomerEvent event) {
        Customer customer = event.getCustomer();
        System.out.println("New customer: " + customer.getName());
    }
}
