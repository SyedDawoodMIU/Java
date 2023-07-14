package com.webshop.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webshop.assignment.shopping.ProductCatalogue;


public interface ProductCatalogueDAO extends JpaRepository<ProductCatalogue, String> {
    
}