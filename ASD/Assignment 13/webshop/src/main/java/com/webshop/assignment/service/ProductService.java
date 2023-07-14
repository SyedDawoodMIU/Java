package com.webshop.assignment.service;

import com.webshop.assignment.shopping.Product;

public interface ProductService {

    public Product getProductById(String productId);

    public void saveProduct(Product product);

    public void updateProduct(Product product);

    public void deleteProduct(Product product);
}
