package Model.Product;

import java.io.Serializable;

public abstract class Product implements Serializable {
    protected int ID;

    protected String name;
    protected double price;
    protected int quantity;
    protected String exp;

    protected String category;
    public Product(String name, double price, int quantity, String exp) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.exp = exp;
        this.category = getClass().getSimpleName();
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getExp() {
        return exp;
    }

    public String getCategory() {
        return category;
    }
}
