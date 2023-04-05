package szydlowskiptr.com.epz.activity;

import java.util.ArrayList;

import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.ProductModel;

public class GetList {

    public static ArrayList<ProductModel> getAllProducts() {
        ArrayList<ProductModel> productModels = new ArrayList<>();
        productModels.add(new ProductModel(1L, "Kropla Beskidu", "1.5L woda gazowana",
                1L, 2L, 2.68, false, true, false, false, true, 12, 3.68, ""));
        return productModels;
    }

    public static ArrayList<CartModel> getCart() {
        ArrayList<CartModel> cart = new ArrayList<>();
        cart.add(new CartModel(103, 55));
        cart.add(new CartModel(104, 10));
        cart.add(new CartModel(160, 2));
        return cart;
    }
}
