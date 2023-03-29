package szydlowskiptr.com.epz.activity;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.model.CartDao;

public class Cart {

    static ArrayList<CartDao> cart = new ArrayList<>();

    public static List<CartDao> getCart() {

        return cart;
    }

    public static void addToCart(CartDao cartDao) {
        cart.add(cartDao);
    }

    public static void removeFromCart(CartDao cartDao) {
        cart.remove(cartDao);
    }
}
