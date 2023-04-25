package Controller;

import Model.Order.Cart;
import Model.Product.Product;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import Exception.OutofStockException;


import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CartControllerTest {
    private CartController cartController  = new CartController();
    private ProductController productController = new ProductController();
    private Cart cart;


    @Test
    public void setUp() {
        cart = new Cart();
        try {
            Product product1 = new Product("Product 1", 2, 100, "20/10/2023");
            Product product2 = new Product("Product 2", 1, 30, "05/03/2023");
            ArrayList<Product> products = new ArrayList<>();
            products.add(product1);
            products.add(product2);
            cart.setProductCart(products);
            cartController.writeCart(cart);
        } catch (IOException e) {
            fail("Exception!");
        }

    }

    @Test
    void checkQuantityProduct() {
        try {
            Cart readCart = cartController.readCart();
            assertEquals(2, readCart.getProductCart().size());
        } catch (IOException | ClassNotFoundException e) {
            fail("Exception not expected");
        }
    }
}