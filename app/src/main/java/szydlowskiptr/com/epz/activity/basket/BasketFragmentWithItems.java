package szydlowskiptr.com.epz.activity.basket;

import static android.content.Context.MODE_PRIVATE;
import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rollbar.android.Rollbar;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.activity.loginRegister.LoginActivity;
import szydlowskiptr.com.epz.home.HomeFragment;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.product.ProductAdapter;
import szydlowskiptr.com.epz.repositories.CartRepository;
import szydlowskiptr.com.epz.repositories.ProductRepository;

public class BasketFragmentWithItems extends Fragment {

    CartRepository cartRepository = new CartRepository(BasketFragmentWithItems.this, "BASKET_WITH_ITEMS_FRA_TAG");
    SharedPreferences sp;
    HomeFragment homeFragment = new HomeFragment();
    ProductRepository productRepository = new ProductRepository(BasketFragmentWithItems.this, "BASKET_WITH_ITEMS_FRA_TAG");
    CartModel cartByUser;
    ArrayList<Product> promoProductsArrayList = new ArrayList<>();
    ProductAdapter productAdapter;
    RecyclerView promoRecyclerView;
    View promoView;
    Button totalBtn, clearCartBtn;
    TextView numberOfProductInBasket;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket_with_items, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        callApiToGetCart();
        setView(view);
        callApiGetPromoProducts();
        callApiToGetCart();
        setTotalCart();
        clearBasket();
        Rollbar.init(getContext());
        return view;
    }

    private void setTotalCart() {
        String basket_total = sp.getString("basket_total", null);
        totalBtn.setText(basket_total + " zł");
    }


    private void callApiGetPromoProducts() {
        productRepository.callApiGetPromoProducts(sp.getString("mag_id", null));
    }

    private void setView(View view) {
        promoView = view.findViewById(R.id.linear_for_promo_recycler_basket);
        promoRecyclerView = view.findViewById(R.id.promo_recycler_view);
        totalBtn = view.findViewById(R.id.btnAdvancedSum);
        numberOfProductInBasket = view.findViewById(R.id.numberOfProductInBasket);
        clearCartBtn = view.findViewById(R.id.clearCartBtn);
    }

    public void clearBasket() {
        clearCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Wprowadź nazwę użytkownika i hasło",
                        Toast.LENGTH_SHORT).show();
            }
        });

//        cartRepository.clearCart(sp.getString("user_id", null));
//        CartModel cartModel = cartRepository.getCartModel();
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("cartItem", String.valueOf(cartModel.getItems().size()));
//        editor.commit();
//        getParentFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
    }

    private void setPromoRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(promoView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        promoRecyclerView.setLayoutManager(linearLayoutManager);
        promoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(getActivity(), promoProductsArrayList, cartByUser, BasketFragmentWithItems.this, "BASKET_WITH_ITEMS_FRA_TAG");
        promoRecyclerView.setAdapter(productAdapter);
    }

    public void notifyOnResponseGetPromoProductsFinished() {
        List<Product> body = productRepository.getGetPromoProducts();
        promoProductsArrayList.removeAll(promoProductsArrayList);
        promoProductsArrayList.addAll(body);
        parseArrayPromoProducts();
    }

    private void parseArrayPromoProducts() {
        try {
            productAdapter = new ProductAdapter(getActivity(), promoProductsArrayList, cartByUser, BasketFragmentWithItems.this, "BASKET_WITH_ITEMS_FRA_TAG");
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        setPromoRecycler();
    }

    private void callApiToGetCart() {
        cartRepository.callApiToGetCart(sp.getString("user_id", null));
    }

    public void getCart() {
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        callApiToGetCart();
    }

    public void notifyOnResponseGetCartFinished() {
        CartModel cartModel = cartRepository.getCartModel();
        cartByUser = cartModel;
        setPromoRecycler();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("cartItem", String.valueOf(cartModel.getItems().size()));
        editor.commit();
        String total = String.valueOf(cartByUser.getTotal());
//        if (!(total == null)) {
//        total = String.valueOf(cartByUser.getTotal());
        editor.putString("basket_total", total);
        editor.putString("cartItem", String.valueOf(cartByUser.getItems().size()));
        editor.apply();
        numberOfProductInBasket.setText("Liczba produktów: " + cartByUser.getItems().size());
    }

    public void addToCart(String stockItemId) {
        cartRepository.addToCart(stockItemId, sp.getString("user_id", null));
    }

    public void removeFromCart(String stockItemId) {
        cartRepository.removeFromCart(stockItemId, sp.getString("user_id", null));
    }
}