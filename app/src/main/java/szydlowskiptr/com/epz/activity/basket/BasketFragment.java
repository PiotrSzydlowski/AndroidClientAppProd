package szydlowskiptr.com.epz.activity.basket;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rollbar.android.Rollbar;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.Helper.Tag;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.databinding.FragmentBasketBinding;
import szydlowskiptr.com.epz.home.HomeFragment;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.product.ProductAdapter;
import szydlowskiptr.com.epz.repositories.CartRepository;
import szydlowskiptr.com.epz.repositories.ProductRepository;

public class BasketFragment extends Fragment {

    FragmentBasketBinding binding;
    ArrayList<Product> allProducts = new ArrayList<>();
    ProductAdapter productAdapter;
    CartModel cartByUser;
    final String tag = Tag.BASKET_FR.name();
    CartRepository cartRepository = new CartRepository(BasketFragment.this, tag);
    ProductRepository productRepository = new ProductRepository(BasketFragment.this, "BASKET_FR");
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBasketBinding.inflate(inflater, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        allProducts.removeAll(allProducts);
        PrefConfig.registerPref(getContext());
        callApiToGetCart();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickStartShoppingBtn();
        callApiGetHitProducts();
        Rollbar.init(getContext());
        return binding.getRoot();
    }

    private void setNewRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.linearForPromoRecycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.promoRecyclerView.setLayoutManager(linearLayoutManager);
        binding.promoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(getActivity(), allProducts, cartByUser, BasketFragment.this, tag);
        binding.promoRecyclerView.setAdapter(productAdapter);
    }

    private void clickStartShoppingBtn() {
        binding.startShoppingBtnCart.setOnClickListener(new View.OnClickListener() {
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
        cartRepository.callApiToGetCart(PrefConfig.loadUserIdFromPref(getContext()));
    }

    private void callApiGetHitProducts() {
        productRepository.callApiGetHitProducts(PrefConfig.loadMagIdFromPref(getContext()));
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
        cartRepository.addToCart(stockItemId, PrefConfig.loadUserIdFromPref(getContext()));
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
        cartRepository.removeFromCart(stockItemId, PrefConfig.loadUserIdFromPref(getContext()));
    }

    public void notifyOnResponseGetCartFinished() {
        CartModel cartModel = cartRepository.getCartModel();
        cartByUser = cartModel;
        PrefConfig.saveActiveOrderInPref(getContext(), String.valueOf(cartByUser.isActiveOrder()));
        if (cartModel.isEmptyBasket()) {
            PrefConfig.saveEmptyBasketInPref(getContext(), "true");
        } else {
            PrefConfig.saveEmptyBasketInPref(getContext(), "false");
        }
        PrefConfig.saveBasketTotalInPref(getContext(), String.valueOf(cartModel.getTotal()));
        PrefConfig.saveCartItemInPref(getContext(), String.valueOf(cartModel.getItems().size()));
        setNewRecycler();
    }

    public void notifyOnResponseGetHitProductsFinished() {
        List<Product> body = productRepository.getGetHitProducts();
        allProducts.addAll(body);
        parseArrayNewProducts();
    }
}