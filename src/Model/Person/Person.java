package Model.Person;

import java.io.Serializable;

public abstract class Person implements Serializable {
    protected int ID =0;
    protected String name;
    protected String age;
    protected String sex;
    protected String email;
    protected String password;

    public Person(String name, String age, String sex, String email, String password) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.password = password;
    }

    public Person() {

    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}
