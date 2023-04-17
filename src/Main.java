import Controller.CustomerController;
import Controller.SettingController;
import Controller.StaffController;
import Model.Person.Customer;
import Model.Person.Staff;
import Exception.EmailAlreadyUsedExeption;
import Exception.InvalidEmailException;
import Exception.InvalidAgeException;
import Exception.InvalidPhoneNumberException;
import Exception.PhoneNumberAlreadyUsedException;
import View.LoginFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) throws IOException {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        System.out.println(day+" "+month+" "+year);

        Staff staff1 = new Staff("Ngoc","20","Male","admin@gmail.com","admin","Admin");
        Staff staff2 = new Staff("Phuc","20","Female","ngoc@gmail.com","12345","Admin");
//        String name = staff1.getClass().getSimpleName(); //Get class name
//        System.out.println(name);

//        ArrayList<Staff> staffList = new ArrayList<>();
//
//        staffList.add(staff1);
//        staffList.add(staff2);
        StaffController staffController = new StaffController();
        CustomerController customerController = new CustomerController();
        SettingController settingController = new SettingController();

//        try {
//            ArrayList<Customer> customerList = new ArrayList<>();
//            customerController.writeCustomer(customerList);
//            Customer customer1 = new Customer("Nguyen Quang Ngoc","100","FeMale","test@gmail.com","test","ABC","0848448873");
//            Customer customer2 = new Customer("Phuc","20","FeMale","2@gmail.com","1224","ABC","312321");
//            Customer customer3 = new Customer("Nguyen Tien Dung","20","FeMale","3@gmail.com","1224","ABC","312321");
//            Customer customer4 = new Customer("Nguyen Quang Ngoc","20","Male","4@gmail.com","1224","ABC","312321");
//            Customer customer5 = new Customer("Phuc","20","FeMale","5@gmail.com","1224","ABC","312321");
//            Customer customer6 = new Customer("Nguyen Tien Dung","20","FeMale","6@gmail.com","1224","ABC","312321");
//            customerList.add(customer1);
//            customerList.add(customer2);
//            customerList.add(customer3);
//            customerList.add(customer4);
//            customerList.add(customer5);
//            customerList.add(customer6);
//            customerController.addCustomer(customer1);
//            customerController.addCustomer(customer2);
//            customerController.addCustomer(customer3);
//            customerController.addCustomer(customer4);
//            customerController.addCustomer(customer5);
//            customerController.addCustomer(customer6);

//            ArrayList<Staff> staffList = new ArrayList<>();
//            staffController.writeStaff(staffList);
//            staffController.addStaff(staff1);
//          staffController.addStaff(staff2);



//            settingController.writeProfile(new Customer("","","","test1@gmail.com","test","",""));
//            staffController.writeStaff(staffList);
//            Customer editCustomer =new Customer("Ngoc Ahihi","20","FEEEEMaleEEE","@gmail.com","1224","ABC","312321"); //Test edit
//            customerController.editCustomer(editCustomer);
//            ArrayList<Customer> result = customerController.findByName("Nguyen"); //Test Find
//            for (Customer ctm : result){
//                System.out.println(ctm.getName());
//            }
//
//        } catch (IOException e ) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            System.out.println(e.getMessage());
//        } catch (EmailAlreadyUsedExeption e) {
//            System.out.println(e.getMessage());
//        } catch (InvalidEmailException e) {
//            System.out.println(e.getMessage());
//        } catch (InvalidPhoneNumberException e) {
//            System.out.println(e.getMessage());
//        } catch (InvalidAgeException e) {
//            System.out.println(e.getMessage());
//        } catch (PhoneNumberAlreadyUsedException e) {
//            System.out.println(e.getMessage());
//        }

        try {
           ArrayList<Customer> customers = customerController.readCustomer();
           for (Customer customer : customers){
               System.out.println(customer.getID()+" "+customer.getPhoneNumber());
           }
//           ArrayList<Staff> staffs = staffController.readStaff();
//           for (Staff staff : staffs){
//               System.out.println(staff.getID()+" "+staff.getEmail());
//           }



       System.out.println(settingController.checkVerifyProfile());

        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

//        if (!checkAdmin.isSelected()){ //Customer Login
//            String email = username.getText();
//            char[] passwordChars = password.getPassword();
//            String password = new String(passwordChars);
//            try {
//                if (customerController.login(email,password)){
//                    new CustomerFrame().setVisible(true);
//                    this.dispose();} else JOptionPane.showMessageDialog(rootPane,"Wrong Email or Password");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        } else { //Admin Login
//            String email = username.getText();
//            char[] passwordChars = password.getPassword();
//            String password = new String(passwordChars);
//            try {
//                if (staffController.login(email,password)) {
//                    new MenuFrame().setVisible(true);
//                    this.dispose();
//                } else JOptionPane.showMessageDialog(rootPane,"Wrong Email or Password");
//            } catch (IOException e) {
//                JOptionPane.showMessageDialog(rootPane,e.getMessage());
//            } catch (ClassNotFoundException e) {
//                JOptionPane.showMessageDialog(rootPane,e.getMessage());
//            }
//
//        }



    }
}
