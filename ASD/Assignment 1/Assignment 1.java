public class CarRentalApp {
    public void addRental(Rental rental) {
        // Implementation
    }

    public void deleteRental(Rental rental) {
        // Implementation
    }

    public List<Rental> searchRental(String query) {
        // Implementation
        return new ArrayList<>();
    }
}

public class Car {
    private String make;
    private String model;
    private String year;
    private String color;
    private String licensePlate;
    private String vin;
    private String type;
    private String status;

    public Car(String make, String model, String year, String color, String licensePlate, String vin, String type,
            String status) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.licensePlate = licensePlate;
        this.vin = vin;
        this.type = type;
        this.status = status;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getVin() {
        return vin;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

public class Rental {
    private Customer customer;
    private Date startDate;
    private int maxDuration;
    private Date endDate;
    private Car car;

    public Customer getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

public class Customer {
    private String name;
    private String phone;
    private String email;
    private String street;
    private String city;
    private String zipCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}

public class Reservation {
    private Rental rental;
    private Date reservationDate;

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }
}

public class Main {
    public static void main(String[] args) {
        // Create instances of classes
        CarRentalApp carRentalApp = new CarRentalApp();
        Rental rental = new Rental();
        Customer customer = new Customer();
        Reservation reservation = new Reservation();

        Car car = new Car("Toyota", "Camry", "2018", "Black", "123456", "123456789", "Sedan", "Available");

        // Set attributes for rental
        rental.setCustomer(customer);
        rental.setStartDate(new Date());
        rental.setMaxDuration(7);
        rental.setEndDate(new Date());
        rental.setCar(car);

        // Set attributes for customer
        customer.setName("John Doe");
        customer.setPhone("1234567890");
        customer.setEmail("johndoe@example.com");
        customer.setStreet("123 Main St");
        customer.setCity("City");
        customer.setZipCode("12345");

        // Set attributes for reservation
        reservation.setRental(rental);
        reservation.setReservationDate(new Date());

        // Add rental and make a reservation
        carRentalApp.addRental(rental);
        // ...

        // Delete rental
        carRentalApp.deleteRental(rental);
        // ...

        // Search for rentals
        carRentalApp.searchRental("query");
        // ...
    }
}
