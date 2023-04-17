package Model.Person;

import Model.Person.Person;

import java.io.Serializable;

public class Customer extends Person implements Serializable {

  protected String address;
  protected String phoneNumber;

    public Customer(String name, String age, String sex, String email, String password, String address, String phoneNumber) {
        super(name, age, sex, email, password);
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
