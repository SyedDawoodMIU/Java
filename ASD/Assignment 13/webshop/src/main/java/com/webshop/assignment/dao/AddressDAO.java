package com.webshop.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.assignment.shopping.Address;

public interface AddressDAO extends JpaRepository<Address, Long> {
}
