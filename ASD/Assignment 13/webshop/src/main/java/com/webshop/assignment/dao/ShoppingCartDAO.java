package com.webshop.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.assignment.shopping.ShoppingCart;

public interface ShoppingCartDAO extends JpaRepository<ShoppingCart, String> {

}