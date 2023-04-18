package Controller;

import Model.Order.Cart;
import Model.Order.Order;
import Model.Person.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Exception.InvalidPriceProductException;
public class OrderController {
    public void writeOrder(ArrayList<Order> orders) throws IOException {
        FileOutputStream fo = new FileOutputStream("src/Data/Order.DAT");
        ObjectOutputStream oos = new ObjectOutputStream(fo);
        oos.writeObject(orders);
        oos.flush();
        oos.close();
        fo.close();
    }

    public ArrayList<Order> readOrder() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream("src/Data/Order.DAT");
        ObjectInputStream ois = new ObjectInputStream(fi);
        return (ArrayList<Order>) ois.readObject();
    }

    public void addOrder(Order order) throws IOException, ClassNotFoundException, InvalidPriceProductException {
        ArrayList<Order> orders = readOrder();
        order.setID(orders.size()+1);
        if (order.getTotalPrice()>=0) {
            orders.add(order);
            writeOrder(orders);
        } else throw new InvalidPriceProductException("Invalid Price");
    }

    public ArrayList<Order> orderListCustomer(Customer customer) throws IOException, ClassNotFoundException {
        ArrayList<Order> orders = readOrder();
        ArrayList<Order> resultList = new ArrayList<>();
        for (Order order : orders){
            Customer ctm = order.getCustomer();
            if (ctm.getID()==customer.getID()){
                resultList.add(order);
            }
        }
        return resultList;
    }


}
