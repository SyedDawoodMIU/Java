package product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import customers.CustomerService;

@SpringBootApplication
@ComponentScan ({"customers", "product"})
public class Application implements CommandLineRunner {

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		productService.addProduct();
		customerService.addCustomer("John Doe", "test@mai.com", "Mainstreet 1", "New York", "12345");

	}
}
