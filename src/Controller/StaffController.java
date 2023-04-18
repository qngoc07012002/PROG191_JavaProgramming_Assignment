package Controller;



import Model.Person.Customer;
import Model.Person.Staff;

import Exception.InvalidEmailException;
import Exception.InvalidAgeException;
import Exception.EmailAlreadyUsedExeption;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaffController {

    public void writeStaff(ArrayList<Staff> staffs) throws IOException {
        FileOutputStream fo = new FileOutputStream("src/Data/Staff.DAT");
        ObjectOutputStream oos = new ObjectOutputStream(fo);
        oos.writeObject(staffs);
        oos.flush();
        oos.close();
        fo.close();
    }

    public ArrayList<Staff> readStaff() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream("src/Data/Staff.DAT");
        ObjectInputStream ois = new ObjectInputStream(fi);
        return (ArrayList<Staff>) ois.readObject();
    }

    public void addStaff(Staff staff) throws IOException, ClassNotFoundException, EmailAlreadyUsedExeption, InvalidEmailException, InvalidAgeException {
        ArrayList<Staff> staffs = readStaff();
        staff.setID(staffs.size()+1);
        if (checkValidEmail(staff.getEmail())) {
           if (checkValidAge(staff.getAge())) {
               if (checkEmailAlreadyUsed(staff.getEmail())) {
                   staffs.add(staff);
                   writeStaff(staffs);
               } else throw new EmailAlreadyUsedExeption("Email Already Used");
           }else throw new InvalidAgeException("Invalid Age");
        } else throw new InvalidEmailException("Invalid Email");

    }

    public void editStaff(Staff staff) throws IOException, ClassNotFoundException, EmailAlreadyUsedExeption, InvalidEmailException, InvalidAgeException {
        ArrayList<Staff> staffs = readStaff();
        if (checkValidEmail(staff.getEmail())) {
            if (checkValidAge(staff.getAge())) {
                if (checkEmailAlreadyUsedEdit(staff)) {
                    for (int i = 0; i < staffs.size(); i++) {
                        Staff stf = staffs.get(i);
                        if (stf.getID() == staff.getID()) {
                            staffs.set(i, staff);
                            break;
                        }
                    }
                    writeStaff(staffs);
                } else throw new EmailAlreadyUsedExeption("Email Already Used!");
            } else throw new InvalidAgeException("Invalid Age");
        } else throw new InvalidEmailException("Invalid Email");

    }

    public void removeStaff(int indexID) throws IOException, ClassNotFoundException {
        ArrayList<Staff> staffs = readStaff();

        for (int i = 0; i < staffs.size(); i++) {
            Staff stf = staffs.get(i);
            if (stf.getID() == indexID) {
                staffs.remove(i);
                break;
            }
        }
        writeStaff(staffs);
    }

    public ArrayList<Staff> findByName(String name) throws IOException, ClassNotFoundException {
        ArrayList<Staff> staffs = readStaff();
        ArrayList<Staff> resultList = new ArrayList<>();
        for (Staff staff : staffs){
            if (staff.getName().contains(name)){
                resultList.add(staff);
            }
        }
        return resultList;
    }

    public boolean login(String email, String password) throws IOException, ClassNotFoundException {
        boolean check = false;
        ArrayList<Staff> staffs = readStaff();

        for (int i=0; i< staffs.size();i++){
            Staff stf = staffs.get(i);
            if (stf.getEmail().equals(email)) {
                if (stf.getPassword().equals(password)){
                    SettingController settingController = new SettingController();
                    settingController.writeProfile(stf);
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public boolean register(String email, String password) throws IOException, ClassNotFoundException, EmailAlreadyUsedExeption, InvalidEmailException {
        boolean check = false;
        ArrayList<Staff> staffs = readStaff();
        //Check email valid

        if (checkValidEmail(email)) {
            if (checkEmailAlreadyUsed(email))
            {
                SettingController settingController = new SettingController();
                settingController.writeProfile(new Staff("","","",email,password));
                check = true;
            } else throw new EmailAlreadyUsedExeption("Email Already Used");
        } else throw new InvalidEmailException("Invalid Email");
        return check;
    }


    public boolean checkEmailAlreadyUsed(String email) throws IOException, ClassNotFoundException {
        boolean check = true;
        ArrayList<Staff> staffs = readStaff();
        for (Staff stf : staffs) {
            if (stf.getEmail().equals(email)){
                check = false;
                break;
            }
        }
        return check;
    }

    public boolean checkEmailAlreadyUsedEdit(Staff staff) throws IOException, ClassNotFoundException {
        boolean check = true;
        ArrayList<Staff> staffs = readStaff();
        for (Staff stf : staffs) {
            if (stf.getID() != stf.getID()) {
                if (staff.getEmail().equals(staff.getEmail())) {
                    check = false;
                    break;
                }
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
