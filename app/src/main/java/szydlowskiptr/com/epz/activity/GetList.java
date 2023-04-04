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
        return products;
    }

    public static ArrayList<CartDao> getCart() {
        ArrayList<CartDao> cart = new ArrayList<>();
        cart.add(new CartDao(103, 55));
        cart.add(new CartDao(104, 10));
        cart.add(new CartDao(160, 2));
        return cart;
    }
}
