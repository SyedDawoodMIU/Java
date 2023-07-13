package customers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mock")
public class CustomerDAOMock implements ICustomerDAO {
    private Logger logger;

    public CustomerDAOMock(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void save(Customer customer) {
        // Mock implementation to save customer
        logger.log("Customer saved in mock: " + customer.getName());
    }
}