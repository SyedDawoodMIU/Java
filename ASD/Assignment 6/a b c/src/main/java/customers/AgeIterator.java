package customers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AgeIterator implements Iterator<Customer> {

    private List<Customer> customers;
    private int index;

    public AgeIterator(CustomerCollection collection) {
        this.customers = new ArrayList<>(collection.getCustomers());

        Collections.sort(customers, (c1, c2) -> Integer.compare(c1.getAge(), c2.getAge()));
        this.index = 0;
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
