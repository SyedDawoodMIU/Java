package shopping;

public class Address {
    private String street;
    private String city;
    private String zip;

    public Address(String street, String city, String zip) {
        this.street = street;
        this.city = city;
        this.zip = zip;

    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {

        return zip;
    }
}