package Model;

public class Customer extends Person{

  protected String address;
  protected String phoneNumber;


    public Customer(String ID, String name, String age, String sex) {
        super(ID, name, age, sex);
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
