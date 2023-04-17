package Controller;

import Model.Person.Customer;
import Model.Person.Person;
import Model.Person.Staff;

import Exception.EmailAlreadyUsedExeption;
import Exception.InvalidEmailException;
import Exception.InvalidAgeException;
import Exception.InvalidPhoneNumberException;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void saveProfile(Person person) {
        if (person.getClass().getSimpleName().equals("Staff")){

        } else {

        }
    }

    public boolean checkVerifyProfile() throws IOException, ClassNotFoundException {
        Person person = readProfile();
        if (person.getClass().getSimpleName().equals("Staff")){
            Staff staff = (Staff) person;

            return !staff.getName().equals("") && !staff.getAge().equals("") && !staff.getSex().equals("") && !staff.getRole().equals("");
        }
        else {
            Customer customer = (Customer) person;

            return !customer.getName().equals("") && !customer.getAge().equals("") && !customer.getSex().equals("")
                    && !customer.getAddress().equals("") && !customer.getPhoneNumber().equals("");
        }
    }


    public boolean checkValidEmail(String email) {
        boolean check = false;
        var regex ="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            check = true;
        }
        return check;
    }

    public boolean checkValidPhoneNumber(String phoneNumber){
        boolean check = false;
        var regex ="^(?:\\+84|0)(?:1\\d{9}|3\\d{8}|5\\d{8}|7\\d{8}|8\\d{8}|9\\d{8})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.matches()) {
            check = true;
        }
        return check;
    }


    public boolean checkValidAge(String age){
        boolean check = false;
        var regex ="^(1[0-2][0-9]|[1-9][0-9]|[1-9]|[1][3][0])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(age);
        if (matcher.matches()) {
            check = true;
        }
        return check;
    }

}
