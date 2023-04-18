package Model.Product;

import Model.Product.Product;

import java.io.Serializable;

public class Food extends Product implements Serializable {

    public Food(String name, double price, int quantity, String exp) {
        super(name, price, quantity, exp);
    }
}
