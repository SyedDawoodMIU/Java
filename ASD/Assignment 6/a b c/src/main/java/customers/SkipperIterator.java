package customers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SkipperIterator implements Iterator<Customer> {

    private List<Customer> customers;
    private int index;

    public SkipperIterator(CustomerCollection collection) {
        this.customers = new ArrayList<>(collection.getCustomers());
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < customers.size();
    }

    @Override
    public Customer next() {
        var customer = customers.get(index);
        index = index + 2;
        return customer;

    }

}
