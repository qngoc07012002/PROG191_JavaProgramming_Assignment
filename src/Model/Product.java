package Model;

public class Product {
    protected String name;
    protected double price;
    protected int quantity;
    protected String exp;

    public Product(String name, double price, int quantity, String exp) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.exp = exp;
    }
}
