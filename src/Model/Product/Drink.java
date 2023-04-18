package Model.Product;

import java.io.Serializable;

public class Drink extends Product implements Serializable {
    public Drink(String name, double price, int quantity, String exp) {
        super(name, price, quantity, exp);
    }
}
