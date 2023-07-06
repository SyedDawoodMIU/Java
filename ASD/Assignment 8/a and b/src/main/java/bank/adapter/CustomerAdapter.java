package bank.adapter;

import bank.domain.Customer;
import bank.dto.CustomerDTO;

public class CustomerAdapter {
    public static CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(customer.getName());
        return customerDTO;
    }
}
