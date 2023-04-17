package Controller;



import Model.Person.Customer;
import Model.Person.Staff;

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

    public void addStaff(Staff staff) throws IOException, ClassNotFoundException {
        ArrayList<Staff> staffs = readStaff();
        staff.setID(staffs.size()+1);
        staffs.add(staff);
        writeStaff(staffs);
    }

    public void editStaff(Staff staff) throws IOException, ClassNotFoundException {
        ArrayList<Staff> staffs = readStaff();
        for (int i=0;i<staffs.size();i++){
            Staff stf = staffs.get(i);

            if (stf.getID() == staff.getID()){
                staffs.set(i,staff);
                break;
            }
        }
        writeStaff(staffs);
    }

    public void removeStaff(int indexID) throws IOException, ClassNotFoundException {
        ArrayList<Staff> staffs = readStaff();
        for (int i=0; i< staffs.size();i++){
            Staff stf = staffs.get(i);
            if (stf.getID() == indexID){
                staffs.remove(i);
                break;
            }
        }
        writeStaff(staffs);
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
