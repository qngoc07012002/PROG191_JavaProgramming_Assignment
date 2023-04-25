package Controller;

import Model.Order.Cart;
import Model.Person.Customer;
import Model.Product.Product;

import Exception.OutofStockException;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class CartController {

    ProductController productController = new ProductController();
    public void writeCart(Cart cart) throws IOException {
        FileOutputStream fo = new FileOutputStream("src/Data/Cart.DAT");
        ObjectOutputStream oos = new ObjectOutputStream(fo);
        oos.writeObject(cart);
        oos.flush();
        oos.close();
        fo.close();
    }

    public Cart readCart() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream("src/Data/Cart.DAT");
        ObjectInputStream ois = new ObjectInputStream(fi);
        return (Cart) ois.readObject();
    }

    public void addProducttoCart(Product product) throws IOException, ClassNotFoundException {
        boolean check = false;
        product.setPrice(product.getQuantity()*product.getPrice());
        Cart cart = readCart();
        ArrayList<Product> products = cart.getProductCart();
        for (Product prd: products){
            if (prd.getID() == product.getID()){
                prd.setPrice(prd.getPrice()+product.getPrice());
                prd.setQuantity(prd.getQuantity()+product.getQuantity());
                check = true;
                break;
            }
        }
        if (!check) products.add(product);
        cart.setProductCart(products);
        writeCart(cart);
    }

    public void editCart(Product product) throws IOException, ClassNotFoundException, OutofStockException {
        Cart cart = readCart();
        if (checkStock(product)) {
            ArrayList<Product> products = cart.getProductCart();
            for (int i = 0; i < products.size(); i++) {
                Product prd = products.get(i);
                //JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),prd.getID()+" "+product.getID());
                if (prd.getID() == product.getID()) {
                    prd.setPrice((prd.getPrice() / prd.getQuantity()) * product.getQuantity());
                    prd.setQuantity(product.getQuantity());
                    products.set(i, prd);
                    cart.setProductCart(products);
                    //JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),Integer.toString(prd.getQuantity()));
                    writeCart(cart);
                }
            }
        } else throw new OutofStockException("Out of Stock");
    }
    public boolean checkStock(Product product) throws IOException, ClassNotFoundException, OutofStockException {
        boolean check = false;
        ArrayList<Product> products = productController.readProduct();
        for (int i=0; i<products.size();i++){
            Product prd = products.get(i);
            if (prd.getID() == product.getID()){
                if (prd.getQuantity()-product.getQuantity()>=0) {
                    check = true;
                    break;
                } else throw new OutofStockException("Out of Stock");
            }
        }
        return check;
    }

    public void removeProductInCart(int ID) throws IOException, ClassNotFoundException {
        Cart cart = readCart();
        ArrayList<Product> products = cart.getProductCart();
        for (int i=0; i< products.size();i++){
            Product prd = products.get(i);
            if (prd.getID() == ID){
                products.remove(i);
                cart.setProductCart(products);
                writeCart(cart);
                break;
            }
        }
    }
    public Cart checkOut() throws IOException, ClassNotFoundException {
        Cart cart = readCart();
        ArrayList<Product> productsInCart = cart.getProductCart();
        ArrayList<Product> products = productController.readProduct();
        double totalPrice = 0;
        for (Product prd : productsInCart){
            totalPrice = totalPrice + prd.getPrice();
            for (int i=0; i< products.size(); i++){
                Product product = products.get(i);
                if (prd.getID() == product.getID()){
                    product.setQuantity(product.getQuantity()-prd.getQuantity());
                    products.set(i,product);
                    productController.writeProduct(products);
                }
            }
        }
        cart.setTotalPrice(totalPrice);
        return cart;
    }

}
