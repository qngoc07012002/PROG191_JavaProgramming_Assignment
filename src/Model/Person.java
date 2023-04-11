package Model;

public abstract class Person {
    protected String ID;
    protected String name;
    protected String age;
    protected String sex;

    public Person(String ID, String name, String age, String sex) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getID() {
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
}
