package szydlowskiptr.com.epz.activity;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Product;

public class GetList {

    public static ArrayList<Product> getAllProducts(){
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1L, "123456987", "Wawrzyniec",
                "pasta z ciecierzycą", null, null, true,
                null, null, null, null,
                R.drawable.product, false, 300));
        products.add(new Product(2L, "4123654789", "Crunchips",
                "fromage chipsy 140g", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        products.add(new Product(3L, "852123321", "Velvet",
                "ręcznik papierowy Turbo", null, null, true,
                null, null, null, null,
                R.drawable.product, false, 300));
        products.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        return products;
    }
}
