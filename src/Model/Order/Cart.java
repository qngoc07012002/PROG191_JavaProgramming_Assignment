package Model.Order;

import Model.Product.Product;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
    protected ArrayList<Product> productCart = new ArrayList<>();
    protected double totalPrice;

    public ArrayList<Product> getProductCart() {
        return productCart;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setProductCart(ArrayList<Product> productCart) {
        this.productCart = productCart;
    }


    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
