package szydlowskiptr.com.epz.activity;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.model.CartModel;

public class Cart {

    static ArrayList<CartModel> cart = new ArrayList<>();

    public static List<CartModel> getCart() {

        return cart;
    }

    public static void addToCart(CartModel cartModel) {
        cart.add(cartModel);
    }

    public static void removeFromCart(CartModel cartModel) {
        cart.remove(cartModel);
    }
}
