package Controller;

import Model.Person.Customer;
import Exception.EmailAlreadyUsedExeption;
import Exception.InvalidEmailException;


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

    public void addCustomer(Customer customer) throws IOException, ClassNotFoundException, EmailAlreadyUsedExeption, InvalidEmailException {
        ArrayList<Customer> customers = readCustomer();
        customer.setID(customers.size()+1);
        if (checkValidEmail(customer.getEmail())) {
            if (checkEmailAlreadyUsed(customer.getEmail())) {
                customers.add(customer);
            } else throw new EmailAlreadyUsedExeption("Email Already Used");
        } else throw new InvalidEmailException("Invalid Email");
        writeCustomer(customers);
    }

    public void editCustomer(Customer customer) throws IOException, ClassNotFoundException, EmailAlreadyUsedExeption, InvalidEmailException {
        ArrayList<Customer> customers = readCustomer();
        if (checkValidEmail(customer.getEmail())) {
            if (checkEmailAlreadyUsed(customer.getEmail())) {
                for (int i = 0; i < customers.size(); i++) {
                    Customer ctm = customers.get(i);
                    if (ctm.getID() == customer.getID()) {
                        customers.set(i, customer);
                        break;
                    }
                }
            } else throw new EmailAlreadyUsedExeption("Email Already Used!");
        } else throw new InvalidEmailException("Invalid Email");
        writeCustomer(customers);
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

    public ArrayList<Customer> findByName(String name) throws IOException, ClassNotFoundException {
        ArrayList<Customer> customers = readCustomer();
        ArrayList<Customer> resultList = new ArrayList<>();

        for (int i=0; i< customers.size();i++){
          Customer ctm = customers.get(i);
          if (ctm.getName().contains(name)){
              resultList.add(customers.get(i));
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
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public boolean register(String email, String password) throws IOException, ClassNotFoundException, EmailAlreadyUsedExeption, InvalidEmailException {
        boolean check = true;
        ArrayList<Customer> customers = readCustomer();
        //Check email valid

        if (checkValidEmail(email)) {
//            if (!check) throw new AlreadyUsedRegisterExeption("User already Used");
//            else {
//                addCustomer(new Customer());
//            }
        } else throw new InvalidEmailException("Invalid Email");
        return check;
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

    public boolean checkValidEmail(String email) {
        boolean check = false;
        var regex ="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            check = true;
        }
         else check = false;
         return check;
    }
}
