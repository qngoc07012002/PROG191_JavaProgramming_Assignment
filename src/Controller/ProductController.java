package Controller;

import Model.Order.Order;
import Model.Person.Customer;
import Model.Product.Product;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exception.InvalidExpProductException;
import Exception.InvalidQuantityProductException;
import Exception.InvalidPriceProductException;

public class ProductController {

    public void writeProduct(ArrayList<Product> products) throws IOException {
        FileOutputStream fo = new FileOutputStream("src/Data/Product.DAT");
        ObjectOutputStream oos = new ObjectOutputStream(fo);
        oos.writeObject(products);
        oos.flush();
        oos.close();
        fo.close();

    }

    public ArrayList<Product> readProduct() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream("src/Data/Product.DAT");
        ObjectInputStream ois = new ObjectInputStream(fi);
        return (ArrayList<Product>) ois.readObject();
    }

    public void addProduct(Product product) throws IOException, ClassNotFoundException, InvalidPriceProductException, InvalidQuantityProductException, InvalidExpProductException {
        ArrayList<Product> products = readProduct();
        boolean kt = false; int i = 1;
        while (!kt){
            kt = true;
            for (Product prd : products){
                if (prd.getID() == i){
                    kt = false;
                    break;
                }
            }
            if (kt) {
                product.setID(i);
            } else i++;
        }
        //product.setID(products.size()+1);
        if (checkValidPrice(product.getPrice())){
            if (checkValidQuantity(product.getQuantity())){
                if (checkValidExp(product.getExp())){
                    products.add(product);
                    writeProduct(products);
                } else throw new InvalidExpProductException("Invalid Exp");
            } else throw new InvalidQuantityProductException("Invalid Quantity");
        } else throw new InvalidPriceProductException("Invalid Price");
    }

    public void editProduct(Product product) throws IOException, ClassNotFoundException, InvalidExpProductException, InvalidQuantityProductException, InvalidPriceProductException {
        ArrayList<Product> products = readProduct();
        if (checkValidPrice(product.getPrice())){
            if (checkValidQuantity(product.getQuantity())){
                if (checkValidExp(product.getExp())){
                    for (int i=0; i<products.size();i++){
                        Product prd = products.get(i);
                        if (prd.getID() == product.getID()){
                            products.set(i,product);
                            break;
                        }
                    }
                }else throw new InvalidExpProductException("Invalid Exp");
            } else throw new InvalidQuantityProductException("Invalid Quantity");
        } else throw new InvalidPriceProductException("Invalid Price");
    }

    public void removeProduct(int indexID) throws IOException, ClassNotFoundException {
        ArrayList<Product> products = readProduct();
        for (int i=0; i< products.size();i++){
            Product prd = products.get(i);
            if (prd.getID() == indexID){
                products.remove(i);
                break;
            }
        }
        writeProduct(products);
    }

    public Product returnProductbyID(int ID) throws IOException, ClassNotFoundException {
        Product product = new Product();
        ArrayList<Product> products = readProduct();
        for(Product prd : products){
            if (prd.getID() == ID){
                product = prd;
            }
        }
        return product;
    }

    public ArrayList<Product> findByName(String name) throws IOException, ClassNotFoundException {
        ArrayList<Product> products = readProduct();
        ArrayList<Product> resultList = new ArrayList<>();
        for (Product prd : products) {
            if (prd.getName().contains(name)) {
                resultList.add(prd);
            }
        }
        return resultList;
    }

    public ArrayList<Product> findByCategory(String name) throws IOException, ClassNotFoundException {
        ArrayList<Product> products = readProduct();
        if (name.equals("All")) return products;
        else {
            ArrayList<Product> resultList = new ArrayList<>();
            for (Product prd : products) {
                if (prd.getCategory().equals(name)) {
                    resultList.add(prd);
                }
            }
            return resultList;
        }
    }

    public ArrayList<Product> findByExpProduct() throws IOException, ClassNotFoundException {
        ArrayList<Product> products = readProduct();
        ArrayList<Product> resultList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        for (Product prd : products){
           String expProduct = prd.getExp();
           String[] expProductList = expProduct.split("/");
            //System.out.println(day+" "+month+" "+year+"-"+expProductList[0]+" "+expProductList[1]+" "+expProductList[2]);

           if (Integer.parseInt(expProductList[2])<year){
               resultList.add(prd);

           } else if (Integer.parseInt(expProductList[2])==year && Integer.parseInt(expProductList[1])<month){
               resultList.add(prd);

           } else if (Integer.parseInt(expProductList[1])==month && Integer.parseInt(expProductList[0])<day){
               resultList.add(prd);

            }
        }
        return resultList;
    }

    public boolean checkValidPrice(double price){
        return price >= 0;
    }

    public boolean checkValidQuantity(int quantity){
        return quantity >=0;
    }

    public boolean checkValidExp(String exp){
        boolean check = false;
        var regex ="^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/((19|20)[0-9]{2})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(exp);
        if (matcher.matches()) {
            check = true;
        }
        return check;
    }
}
