package service;

import dao.ProductDAO;
import dao.ProductDAO;
import shopping.Product;

public class ProductService {
    private ProductDAO productDAO;

    public ProductService() {
        productDAO = new ProductDAO();
    }

    public Product getProductById(String productId) {
        return productDAO.getById(productId);
    }

    public void saveProduct(Product product) {
        productDAO.save(product);
    }

    public void updateProduct(Product product) {
        productDAO.update(product);
    }

    public void deleteProduct(Product product) {
        productDAO.delete(product);
    }
}
