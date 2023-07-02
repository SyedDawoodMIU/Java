import java.util.ArrayList;
import java.util.List;

interface CatalogComponent {
    void display();
}

class Category implements CatalogComponent {
    private String name;
    private List<CatalogComponent> components;

    public Category(String name) {
        this.name = name;
        this.components = new ArrayList<>();
    }

    public void addComponent(CatalogComponent component) {
        components.add(component);
    }

    public void removeComponent(CatalogComponent component) {
        components.remove(component);
    }

    @Override
    public void display() {
        System.out.println("Category: " + name);
        for (CatalogComponent component : components) {
            component.display();
        }
    }
}

class Product implements CatalogComponent {
    private String name;

    public Product(String name) {
        this.name = name;
    }

    @Override
    public void display() {
        System.out.println("Product: " + name);
    }
}

public class ProductCatalogApp {
    public static void main(String[] args) {
        Category electronics = new Category("Electronics");
        Category computers = new Category("Computers");
        Category laptops = new Category("Laptops");
        Category smartphones = new Category("Smartphones");

        Product laptop1 = new Product("Laptop 1");
        Product laptop2 = new Product("Laptop 2");
        Product smartphone1 = new Product("Smartphone 1");
        Product smartphone2 = new Product("Smartphone 2");

        electronics.addComponent(computers);
        electronics.addComponent(smartphones);
        computers.addComponent(laptops);
        laptops.addComponent(laptop1);
        laptops.addComponent(laptop2);
        smartphones.addComponent(smartphone1);
        smartphones.addComponent(smartphone2);

        electronics.display();
    }
}
