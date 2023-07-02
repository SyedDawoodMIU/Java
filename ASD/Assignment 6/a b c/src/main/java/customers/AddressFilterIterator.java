package customers;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.Collections;

public class AddressFilterIterator implements Iterator<Customer> {
    private List<Customer> customers;
    private int index;
    private Predicate<? super Address> filterAddress;

    public AddressFilterIterator(CustomerCollection collection, Predicate<? super Address> filterAddress) {
        this.customers = new ArrayList<>(collection.getCustomers());
        this.filterAddress = filterAddress;
        filterCustomers();
        this.index = 0;
    }

    private void filterCustomers() {
        customers.removeIf(customer -> !filterAddress.test(customer.getAddress()));
    }

    @Override
    public boolean hasNext() {
        return index < customers.size();
    }

    @Override
    public Customer next() {
            return customers.get(index++);
    }
}
