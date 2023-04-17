package Controller;

import Model.Person.Customer;
import Model.Person.Person;
import Model.Person.Staff;

import java.io.*;
import java.util.ArrayList;

public class SettingController {


    public SettingController(){

    }

    public void writeProfile(Person person) throws IOException {
        FileOutputStream fo = new FileOutputStream("src/Data/CurrentProfile.DAT");
        ObjectOutputStream oos = new ObjectOutputStream(fo);
        oos.writeObject(person);
        oos.flush();
        oos.close();
        fo.close();
    }

    public Person readProfile() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream("src/Data/CurrentProfile.DAT");
        ObjectInputStream ois = new ObjectInputStream(fi);
        return (Person) ois.readObject();
    }

    public boolean checkVerifyProfile() throws IOException, ClassNotFoundException {
        Person person = readProfile();
        if (person.getClass().getSimpleName().equals("Staff")){
            Staff staff = (Staff) person;
            return staff.getName() != "" && staff.getAge() != "" && staff.getSex() != "" && staff.getRole() != "";
        }
        else {
            Customer customer = (Customer) person;
            return customer.getName() != "" && customer.getAge() != "" && customer.getSex() != ""
                    && customer.getAddress() != "" && customer.getPhoneNumber() !="";
        }
    }


}
