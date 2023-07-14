package com.webshop.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.assignment.shopping.CustomerInfo;

public interface CustomerInfoDAO extends JpaRepository<CustomerInfo, String> {
}