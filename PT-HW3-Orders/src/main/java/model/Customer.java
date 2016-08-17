package model;

/**
 * Created by gliga on 3/29/2015.
 */
public class Customer {

    private String name;
    private String address;

    /**
     * Simple constructor
     */
    public Customer(){

    }

    /**
     * COnstructor with aprameters
     * @param name
     * @param address
     */
    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
