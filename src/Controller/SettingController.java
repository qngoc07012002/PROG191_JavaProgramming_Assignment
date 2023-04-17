package Controller;

import Model.Person.Customer;
import Model.Person.Person;
import Model.Person.Staff;


import Exception.InvalidAgeException;
import Exception.InvalidPhoneNumberException;
import Exception.PhoneNumberAlreadyUsedException;
import Exception.EmailAlreadyUsedExeption;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingController {
    CustomerController customerController;
    StaffController staffController;

    public SettingController(){
        customerController = new CustomerController();
        staffController = new StaffController();
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

    public boolean saveProfile(Person person) throws InvalidAgeException, IOException, InvalidPhoneNumberException, ClassNotFoundException, PhoneNumberAlreadyUsedException, EmailAlreadyUsedExeption {
        if (person.getClass().getSimpleName().equals("Staff")){
            Staff staff = (Staff) person;
            if (checkValidAge(staff.getAge())){
                if (checkEmailAlreadyUsedStaff(staff)) {
                    writeProfile(staff);
                    return true;
                } throw new EmailAlreadyUsedExeption("Email Already Used");
            } else throw new InvalidAgeException("Invalid Age");
        } else {
            Customer customer = (Customer) person;
            if (checkValidAge(customer.getAge())){
                if (checkValidPhoneNumber(customer.getPhoneNumber())){
                    if (checkPhoneAlreadyUsed(customer)) {
                        if (checkEmailAlreadyUsedCustomer(customer)) {
                            writeProfile(customer);
                            return true;
                        } throw new EmailAlreadyUsedExeption("Email Already Used");
                    } else throw new PhoneNumberAlreadyUsedException("Phone Already Used");
                } else throw new InvalidPhoneNumberException("Invalid Phone Number");
            } else throw new InvalidAgeException("Invalid Age");
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

    public boolean checkEmailAlreadyUsedStaff(Staff staff) throws IOException, ClassNotFoundException {
        boolean check = true;
        ArrayList<Staff> staffs = staffController.readStaff();
        for (Staff stf : staffs) {
            if (stf.getID() != staff.getID()) {
                if (stf.getEmail().equals(staff.getEmail())) {
                    check = false;
                    break;
                }
            }
        }
        return check;
    }

    public boolean checkEmailAlreadyUsedCustomer(Customer customer) throws IOException, ClassNotFoundException {
        boolean check = true;
        ArrayList<Customer> customers = customerController.readCustomer();
        for (Customer ctm : customers) {
            if (ctm.getID() != customer.getID()) {
                if (ctm.getEmail().equals(customer.getEmail())) {
                    check = false;
                    break;
                }
            }
        }
        return check;
    }

    public boolean checkPhoneAlreadyUsed(Customer customer) throws IOException, ClassNotFoundException {
        boolean check = true;
        ArrayList<Customer> customers = customerController.readCustomer();
        for (Customer ctm : customers) {
            if (ctm.getID() != customer.getID()) {
                if (ctm.getPhoneNumber().equals(customer.getPhoneNumber())) {
                    check = false;
                    break;
                }
            }
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
