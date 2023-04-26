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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rollbar.android.Rollbar;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.Helper.Tag;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.home.HomeActivity;
import szydlowskiptr.com.epz.home.HomeFragment;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.product.ProductAdapter;
import szydlowskiptr.com.epz.repositories.CartRepository;
import szydlowskiptr.com.epz.repositories.ProductRepository;

public class BasketFragment extends Fragment {

    Button startShoppingBtn;
    ArrayList<Product> allProducts = new ArrayList<>();
    View promoView;
    RecyclerView promoRecyclerView;
    ProductAdapter productAdapter;
    SharedPreferences sp;
    CartModel cartByUser;
    final String tag = Tag.BASKET_FR.name();
    CartRepository cartRepository = new CartRepository(BasketFragment.this, tag);
    ProductRepository productRepository = new ProductRepository(BasketFragment.this, "BASKET_FR");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        allProducts.removeAll(allProducts);
        sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        callApiToGetCart();
        setView(view);
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickStartShoppingBtn();
        callApiGetHitProducts();
        Rollbar.init(getContext());
        return view;
    }

    private void setView(View view) {
        startShoppingBtn = view.findViewById(R.id.start_shopping_btn_cart);
        promoView = view.findViewById(R.id.linear_for_promo_recycler);
        promoRecyclerView = view.findViewById(R.id.promo_recycler_view);
    }

    private void setNewRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(promoView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        promoRecyclerView.setLayoutManager(linearLayoutManager);
        promoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(getActivity(), allProducts, cartByUser, BasketFragment.this, tag);
        promoRecyclerView.setAdapter(productAdapter);
    }

    private void clickStartShoppingBtn() {
        startShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment home = new HomeFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, home);
                ft.commit();
            }
        });
    }

    private void callApiToGetCart() {
        cartRepository.callApiToGetCart(sp.getString("user_id", null));
    }

    private void callApiGetHitProducts() {
        productRepository.callApiGetHitProducts(sp.getString("mag_id", null));
    }

    private void parseArrayNewProducts() {
        try {
            productAdapter = new ProductAdapter(getActivity(), allProducts, cartByUser, BasketFragment.this, tag);
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        setNewRecycler();
    }

    public void addToCart(String stockItemId) {
        cartRepository.addToCart(stockItemId, sp.getString("user_id", null));
    }

    public void getCart() {
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        callApiToGetCart();
    }

    public void removeFromCart(String stockItemId) {
        cartRepository.removeFromCart(stockItemId, sp.getString("user_id", null));
    }

    public void notifyOnResponseGetCartFinished() {
        CartModel cartModel = cartRepository.getCartModel();
        cartByUser = cartModel;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("cartItem", String.valueOf(cartModel.getItems().size()));
        editor.putString("basket_total", String.valueOf(cartModel.getTotal()));
        editor.commit();
        setNewRecycler();
    }

    public void notifyOnResponseGetHitProductsFinished() {
        List<Product> body = productRepository.getGetHitProducts();
        allProducts.addAll(body);
        parseArrayNewProducts();
    }
}