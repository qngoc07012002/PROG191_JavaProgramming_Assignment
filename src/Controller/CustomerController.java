package Controller;

import Model.Person.Customer;
import Exception.EmailAlreadyUsedExeption;
import Exception.InvalidEmailException;
import Exception.InvalidAgeException;
import Exception.InvalidPhoneNumberException;
import Exception.PhoneNumberAlreadyUsedException;
import Exception.CustomerNotAvailableException;


import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerController {

    public void writeCustomer(ArrayList<Customer> customers) throws IOException {
        FileOutputStream fo = new FileOutputStream("src/Data/Customer.DAT");
        ObjectOutputStream oos = new ObjectOutputStream(fo);
        oos.writeObject(customers);
        oos.flush();
        oos.close();
        fo.close();
    }

    public ArrayList<Customer> readCustomer() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream("src/Data/Customer.DAT");
        ObjectInputStream ois = new ObjectInputStream(fi);
        return (ArrayList<Customer>) ois.readObject();
    }

    public void addCustomer(Customer customer) throws IOException, ClassNotFoundException, EmailAlreadyUsedExeption, InvalidEmailException, InvalidPhoneNumberException, InvalidAgeException, PhoneNumberAlreadyUsedException {
        ArrayList<Customer> customers = readCustomer();
        customer.setID(customers.size()+1);
        if (checkValidEmail(customer.getEmail())) {
            if (checkValidAge(customer.getAge())) {
                if (checkValidPhoneNumber(customer.getPhoneNumber())) {
                    if (checkEmailAlreadyUsed(customer.getEmail())) {
                        if (checkPhoneAlreadyUsed(customer.getPhoneNumber())) {
                            customers.add(customer);
                            writeCustomer(customers);

                        } else throw new PhoneNumberAlreadyUsedException("Phone Already Used");
                    } else throw new EmailAlreadyUsedExeption("Email Already Used");
                } else throw new InvalidPhoneNumberException("Invalid Phone Number");
            } else throw new InvalidAgeException("Invalid Age");
        } else throw new InvalidEmailException("Invalid Email");

    }

    public void editCustomer(Customer customer) throws IOException, ClassNotFoundException, EmailAlreadyUsedExeption, InvalidEmailException, InvalidAgeException, InvalidPhoneNumberException, PhoneNumberAlreadyUsedException {
        ArrayList<Customer> customers = readCustomer();
        if (checkValidEmail(customer.getEmail())) {
            if (checkValidAge(customer.getAge())) {
                if (checkValidPhoneNumber(customer.getPhoneNumber())) {
                    if (checkEmailAlreadyUsedEdit(customer)) {
                        if (checkPhoneAlreadyUsedEdit(customer)) {
                            for (int i = 0; i < customers.size(); i++) {
                                Customer ctm = customers.get(i);
                                if (ctm.getID() == customer.getID()) {

                                    customers.set(i, customer);

                                    break;
                                }
                            }
                            writeCustomer(customers);
                        } else throw new PhoneNumberAlreadyUsedException("Phone Number Already Used");
                    } else throw new EmailAlreadyUsedExeption("Email Already Used!");
                }else throw new InvalidPhoneNumberException("Invalid Phone Number");
            } else throw new InvalidAgeException("Invalid Age");
        } else throw new InvalidEmailException("Invalid Email");
    }

    public void removeCustomer(int indexID) throws IOException, ClassNotFoundException {
        ArrayList<Customer> customers = readCustomer();

        for (int i = 0; i < customers.size(); i++) {
            Customer ctm = customers.get(i);
            if (ctm.getID() == indexID) {
                customers.remove(i);
                break;
            }
        }
        writeCustomer(customers);
    }

    public Customer returnCustomerbyID(int ID) throws IOException, ClassNotFoundException {
        Customer customer = new Customer();
        ArrayList<Customer> customers = readCustomer();
        for(Customer ctm : customers){
            if (ctm.getID() == ID){
                customer = ctm;
            }
        }
        return customer;
    }

    public ArrayList<Customer> findByName(String name) throws IOException, ClassNotFoundException {
        ArrayList<Customer> customers = readCustomer();
        ArrayList<Customer> resultList = new ArrayList<>();

        for (Customer ctm : customers) {
            if (ctm.getName().contains(name)) {
                resultList.add(ctm);
            }
        }
        return resultList;
    }

    public boolean login(String email, String password) throws IOException, ClassNotFoundException {
        boolean check = false;
        ArrayList<Customer> customers = readCustomer();

        for (int i=0; i< customers.size();i++){
            Customer ctm = customers.get(i);
            if (ctm.getEmail().equals(email)) {
                if (ctm.getPassword().equals(password)){
                    SettingController settingController = new SettingController();
                    settingController.writeProfile(ctm);
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public boolean register(String email, String password) throws IOException, ClassNotFoundException, EmailAlreadyUsedExeption, InvalidEmailException {
        boolean check = false;
        ArrayList<Customer> customers = readCustomer();
        //Check email valid

        if (checkValidEmail(email)) {
            if (checkEmailAlreadyUsed(email))
            {
                SettingController settingController = new SettingController();
                settingController.writeProfile(new Customer("","","",email,password,"",""));
                check = true;
            } else throw new EmailAlreadyUsedExeption("Email Already Used");
        } else throw new InvalidEmailException("Invalid Email");
        return check;
    }

    public void checkCustomerAvailable(int ID) throws IOException, ClassNotFoundException, CustomerNotAvailableException {
        ArrayList<Customer> customers = readCustomer();
        boolean check = false;
        for (Customer customer : customers){
            if (customer.getID() == ID){
                check = true;
                break;
            }
        }
        if (!check) throw new CustomerNotAvailableException("Customer Not Available");
    }

    public boolean checkEmailAlreadyUsed(String email) throws IOException, ClassNotFoundException {
        boolean check = true;
        ArrayList<Customer> customers = readCustomer();
        for (Customer ctm : customers) {
            if (ctm.getEmail().equals(email)){
                check = false;
                break;
            }
        }
        return check;
    }


    public boolean checkEmailAlreadyUsedEdit(Customer customer) throws IOException, ClassNotFoundException {
        boolean check = true;
        ArrayList<Customer> customers = readCustomer();
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

    public boolean checkPhoneAlreadyUsedEdit(Customer customer) throws IOException, ClassNotFoundException {
        boolean check = true;
        ArrayList<Customer> customers = readCustomer();
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

    public boolean checkPhoneAlreadyUsed(String phoneNumber) throws IOException, ClassNotFoundException {
        boolean check = true;
        ArrayList<Customer> customers = readCustomer();
        for (Customer ctm : customers) {
            if (ctm.getPhoneNumber().equals(phoneNumber)){
                check = false;
                break;
            }
        }
        return check;
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
        var regex ="^[0-9\\-\\+]{9,15}$";
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
