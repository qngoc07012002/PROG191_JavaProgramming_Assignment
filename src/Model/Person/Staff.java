package Model.Person;

import Model.Person.Person;

import java.io.*;

public class Staff extends Person implements Serializable{
    protected String role;

    public Staff(){
        super();
    };

    public Staff( String name, String age, String sex, String email, String password, String role) {
        super(name, age, sex, email, password);
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
