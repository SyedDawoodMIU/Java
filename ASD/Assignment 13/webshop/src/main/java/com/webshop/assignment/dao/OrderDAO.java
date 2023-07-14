package com.webshop.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.assignment.shopping.Order;

public interface OrderDAO extends JpaRepository<Order, String> {

}