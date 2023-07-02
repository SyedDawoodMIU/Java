package customers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class CustomerCollection {
	private List<Customer> customers = new ArrayList<Customer>();

	public void add(Customer customer) {
		customers.add(customer);
	}

	public void print() {
		for (Customer customer : customers) {
			System.out.println(customer);
		}
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public Iterator<Customer> getAgeIterator() {
		return new AgeIterator(this);
	}

	public Iterator<Customer> getAddressFilterIterator(Predicate<? super Address> filterAddress) {
		return new AddressFilterIterator(this, filterAddress);
	}

	public Iterator<Customer> getSkipperIterator() {
		return new SkipperIterator(this);
	}

}
