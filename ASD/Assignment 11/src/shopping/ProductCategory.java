package shopping;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory {
    private List<ProductCatalogue> products;

    public ProductCategory() {
        products = new ArrayList<>();
    }

    public void addProduct(ProductCatalogue product) {
        products.add(product);
    }

    public void removeProduct(ProductCatalogue product) {
        products.remove(product);
    }

    public List<ProductCatalogue> getProducts() {
        return products;
    }
}