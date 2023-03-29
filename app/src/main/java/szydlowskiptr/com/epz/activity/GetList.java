package szydlowskiptr.com.epz.activity;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.CartDao;
import szydlowskiptr.com.epz.model.Product;

public class GetList {

    public static ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Kropla Beskidu", "1.5L woda gazowana",
                12L, 15L, 2.68, false, false, true, false, true, 12, 3.68));
        products.add(new Product(1L, "Ciskowianka", "1.5L woda gazowana",
                13L, 15L, 2.68, false, false, true, false, true, 12, 3.68));
        products.add(new Product(1L, "Coca-Cola", "1.5L woda gazowana",
                14L, 15L, 2.68, false, false, true, false, true, 12, 3.68));
        products.add(new Product(1L, "Pepsi", "1.5L woda gazowana",
                15L, 15L, 2.68, false, false, true, false, true, 12, 3.68));
        return products;
    }

    public static ArrayList<CartDao> getCart() {
        ArrayList<CartDao> cart = new ArrayList<>();
        cart.add(new CartDao(1, 55));
        cart.add(new CartDao(3, 10));
        cart.add(new CartDao(4, 2));
        return cart;
    }
}
