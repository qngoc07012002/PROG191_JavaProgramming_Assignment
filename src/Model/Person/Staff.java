package Model.Person;

import Model.Person.Person;

import java.io.*;

public class Staff extends Person implements Serializable{
    protected String role;

    public Staff(){
        super();
    };

    public Staff( String name, String age, String sex, String email, String password) {
        super(name, age, sex, email, password);
        role = "STAFF";
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
