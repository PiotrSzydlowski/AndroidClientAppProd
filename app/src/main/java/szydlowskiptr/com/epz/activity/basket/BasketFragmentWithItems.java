package szydlowskiptr.com.epz.activity.basket;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rollbar.android.Rollbar;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Item;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.product.ProductAdapter;
import szydlowskiptr.com.epz.repositories.CartRepository;
import szydlowskiptr.com.epz.repositories.ProductRepository;

public class BasketFragmentWithItems extends Fragment {

    CartRepository cartRepository = new CartRepository(BasketFragmentWithItems.this, "BASKET_WITH_ITEMS_FRA_TAG");
    ProductRepository productRepository = new ProductRepository(BasketFragmentWithItems.this, "BASKET_WITH_ITEMS_FRA_TAG");
    CartModel cartByUser;
    ArrayList<Product> promoProductsArrayList = new ArrayList<>();
    List<Item> cartProductList = new ArrayList<>();
    ProductAdapter productAdapter;
    CartProductListAdapter cartProductListAdapter;
    RecyclerView promoRecyclerView, recycler_items_list;
    View promoView, linear_for_cart_product_list;
    Button totalBtn, clearCartBtn;
    TextView numberOfProductInBasket, pay_sum_value, order_sum_value;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket_with_items, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        PrefConfig.registerPref(getContext());
        callApiToGetCart();
        setView(view);
        setCounter();
        setOrderSum();
        callApiGetPromoProducts();
        callApiToGetCart();
        clearBasket();
        Rollbar.init(getContext());
        return view;
    }

    private void setProductCartRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(linear_for_cart_product_list.getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_items_list.setLayoutManager(linearLayoutManager);
        recycler_items_list.setItemAnimator(new DefaultItemAnimator());
        cartProductListAdapter = new CartProductListAdapter(getActivity(), cartProductList, cartByUser, BasketFragmentWithItems.this, "BASKET_WITH_ITEMS_FRA_TAG");
        recycler_items_list.setAdapter(cartProductListAdapter);
    }


    private void callApiGetPromoProducts() {
        productRepository.callApiGetPromoProducts(PrefConfig.loadMagIdFromPref(getContext()));
    }

    private void setView(View view) {
        promoView = view.findViewById(R.id.linear_for_promo_recycler_basket);
        promoRecyclerView = view.findViewById(R.id.promo_recycler_view);
        totalBtn = view.findViewById(R.id.btnAdvancedSum);
        numberOfProductInBasket = view.findViewById(R.id.numberOfProductInBasket);
        clearCartBtn = view.findViewById(R.id.clearCartBtn);
        linear_for_cart_product_list = view.findViewById(R.id.linear_for_cart_product_list);
        recycler_items_list = view.findViewById(R.id.recycler_items_list);
        pay_sum_value = view.findViewById(R.id.pay_sum_value);
        order_sum_value = view.findViewById(R.id.order_sum_value);
    }

    public void clearBasket() {
        clearCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Wyczyść koszyk")
                        .setMessage("Czy napewno chcesz usunąć wszytskie pozycje z koszyka?")
                        .setCancelable(false)
                        .setPositiveButton("Wyczyść koszyk", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("Nie", null)
                        .show();
            }
        });
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
        cartRepository.callApiToGetCart(PrefConfig.loadUserIdFromPref(getContext()));

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
        cartProductList = cartModel.getItems();
        setPromoRecycler();
        setProductCartRecycler();
        PrefConfig.saveItemTotalInPref(getContext(),String.valueOf(cartByUser.getItemTotal()));
        PrefConfig.saveBasketTotalInPref(getContext(),String.valueOf(cartByUser.getTotal()));
        PrefConfig.saveCartItemInPref(getContext(),String.valueOf(cartModel.getItems().size()));
        numberOfProductInBasket.setText("Liczba produktów: " + cartByUser.getItems().size());
        if (cartModel.isEmptyBasket()) {
            PrefConfig.saveEmptyBasketInPref(getContext(), "true");
        } else {
            PrefConfig.saveEmptyBasketInPref(getContext(), "false");
        }
        setCounter();
        setOrderSum();
    }

    public void addToCart(String stockItemId) {
        cartRepository.addToCart(stockItemId, PrefConfig.loadUserIdFromPref(getContext()));
    }

    public void removeFromCart(String stockItemId) {
        cartRepository.removeFromCart(stockItemId, PrefConfig.loadUserIdFromPref(getContext()));
    }

//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        if (key.equals(PrefConfig.BASKET_TOTAL_PREF)) {
//            setCounter();
//        }
//        if (key.equals(PrefConfig.ITEM_TOTAL_PREF)) {
//            setOrderSum();
//        }
//    }

    private void setOrderSum() {
        String loadItemTotalFromPref = PrefConfig.loadItemTotalFromPref(getContext());
        order_sum_value.setText(loadItemTotalFromPref + " zł");
    }

    private void setCounter() {
        String counter = PrefConfig.loadBasketTotalFromPref(getContext());
        totalBtn.setText(counter + " zł");
        pay_sum_value.setText(counter + " zł");
    }
}