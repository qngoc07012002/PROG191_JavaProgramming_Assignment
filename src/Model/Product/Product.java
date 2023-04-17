package Model.Product;

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
}
