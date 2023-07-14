
package com.webshop.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.assignment.shopping.Product;

public interface ProductDAO extends JpaRepository<Product, String> {

}
