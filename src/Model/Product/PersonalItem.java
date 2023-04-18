package Model.Product;

import java.io.Serializable;

public class PersonalItem extends Product implements Serializable {

    public PersonalItem(String name, double price, int quantity, String exp) {
        super(name, price, quantity, exp);
    }

}
