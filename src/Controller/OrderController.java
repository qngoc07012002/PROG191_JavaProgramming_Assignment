package Controller;

import Model.Order.Cart;
import Model.Order.Order;
import Model.Person.Customer;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Exception.InvalidPriceProductException;
import Model.Product.Product;

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
        boolean kt = false; int i = 1;
        while (!kt){
            kt = true;
            for (Order ord : orders){
                if (ord.getID() == i){
                    kt = false;
                    break;
                }
            }
            if (kt) {
                order.setID(i);
            } else i++;
        }
        //order.setID(orders.size()+1);
        if (order.getTotalPrice()>=0) {
            orders.add(order);
            writeOrder(orders);
        } else throw new InvalidPriceProductException("Invalid Price");
    }

    public void editOrder(Order order) throws IOException, ClassNotFoundException {
        ArrayList<Order> orders = readOrder();
        for (int i=0; i< orders.size();i++){
            Order ord = orders.get(i);
            if (order.getID() == ord.getID()){
                orders.set(i,order);
                break;
            }
        }
        writeOrder(orders);
    }

    public void removeOrder(int ID) throws IOException, ClassNotFoundException {
        ArrayList<Order> orders = readOrder();
        for (int i = 0; i < orders.size(); i++) {
            Order ord = orders.get(i);
            if (ord.getID() == ID) {
                orders.remove(i);
                break;
            }
        }
        writeOrder(orders);
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

    public ArrayList<Order> findByPhoneNumber(String phoneNumber) throws IOException, ClassNotFoundException {
        ArrayList<Order> orders = readOrder();
        ArrayList<Order> resultList = new ArrayList<>();
        for (Order order : orders){
            Customer ctm = order.getCustomer();
            if (ctm.getPhoneNumber().contains(phoneNumber)){
                resultList.add(order);
            }
        }
        return resultList;
    }

    public Order returnOrderbyID(int ID) throws IOException, ClassNotFoundException {
        Order order = new Order();
        ArrayList<Order> orders = readOrder();
        for(Order ord : orders){
            if (ord.getID() == ID){
                order = ord;
                break;
            }
        }
        return order;
    }

    public ArrayList<Product> orderStatistics() throws IOException, ClassNotFoundException {
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Order> orders = readOrder();
        for (Order order : orders){
            Cart cart = order.getCart();
            ArrayList<Product> productInCart = cart.getProductCart();
            for (Product product : productInCart){
                boolean kt = false;
                for (int i=0; i< products.size();i++){
                    Product prd = products.get(i);
                    if (prd.getName().equals(product.getName())){
                        prd.setQuantity(prd.getQuantity()+product.getQuantity());
                        products.set(i,prd);
                        kt = true;
                        break;
                    }
                }
                if (!kt) products.add(product);
            }
        }

        for (int i=0; i< products.size() - 1;i++){
            for (int j=0; j< products.size()-i-1; j++){
                Product prdj = products.get(j);
                Product prdj1 = products.get(j+1);
                if (prdj.getQuantity() < prdj1.getQuantity() ){
                    Product temp = prdj;
                    products.set(j,prdj1);
                    products.set(j+1,temp);
                }
            }
        }

        return products;
    }

    public double totalRevenue() throws IOException, ClassNotFoundException {
        ArrayList<Order> orders = readOrder();
        double total = 0;
        for (Order order : orders){
            total = total + order.getTotalPrice();
        }
        return total;
    }
}
