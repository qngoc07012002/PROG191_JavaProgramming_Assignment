package Model.Order;

import java.util.ArrayList;
import java.util.List;

public class Order {
    protected String customerID;
    protected List<Cart> productsInCart = new ArrayList<Cart>();
    protected String dateTime;

    public Order(String customerID, List<Cart> productsInCart, String dateTime) {
        this.customerID = customerID;
        this.productsInCart = productsInCart;
        this.dateTime = dateTime;
    }
}
