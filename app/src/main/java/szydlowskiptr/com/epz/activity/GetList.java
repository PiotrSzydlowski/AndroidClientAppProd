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
                1L, 2L, 2.68, false, true, false, false, true, 12, 3.68, ""));
        products.add(new Product(3L, "Ciskowianka", "1.5L woda gazowana",
                2L, 4L, 9.99, false, false, true, false, true, 12, 12.64, ""));
        products.add(new Product(5L, "Coca-Cola", "1.5L woda gazowana",
                3L, 6L, 0.99, false,false , true, false, true, 12, 1.12, ""));
        products.add(new Product(7L, "Pepsi", "1.5L woda gazowana",
                4L, 8L, 56.88, false, false, true, false, true, 12, 60.00, ""));
        return products;
    }

    public static ArrayList<CartDao> getCart() {
        ArrayList<CartDao> cart = new ArrayList<>();
        cart.add(new CartDao(13, 55));
        cart.add(new CartDao(14, 10));
        cart.add(new CartDao(16, 2));
        return cart;
    }
}
