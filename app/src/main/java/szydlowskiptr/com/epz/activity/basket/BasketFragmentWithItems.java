package szydlowskiptr.com.epz.activity.basket;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rollbar.android.Rollbar;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.databinding.FragmentBasketWithItemsBinding;
import szydlowskiptr.com.epz.home.HomeFragment;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Item;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.product.ProductAdapter;
import szydlowskiptr.com.epz.repositories.CartRepository;
import szydlowskiptr.com.epz.repositories.ProductRepository;

public class BasketFragmentWithItems extends Fragment {

    FragmentBasketWithItemsBinding binding;
    CartRepository cartRepository = new CartRepository(BasketFragmentWithItems.this, "BASKET_WITH_ITEMS_FRA_TAG");
    ProductRepository productRepository = new ProductRepository(BasketFragmentWithItems.this, "BASKET_WITH_ITEMS_FRA_TAG");
    CartModel cartByUser;
    ArrayList<Product> promoProductsArrayList = new ArrayList<>();
    List<Item> cartProductList = new ArrayList<>();
    ProductAdapter productAdapter;
    CartProductListAdapter cartProductListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBasketWithItemsBinding.inflate(inflater, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        PrefConfig.registerPref(getContext());
        callApiToGetCart();
        setCounter();
        setOrderSum();
        callApiGetPromoProducts();
        callApiToGetCart();
        clearBasket();
        goToCheckout();
        Rollbar.init(getContext());
        return binding.getRoot();
    }

    private void setProductCartRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.linearForCartProductList.getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerItemsList.setLayoutManager(linearLayoutManager);
        binding.recyclerItemsList.setItemAnimator(new DefaultItemAnimator());
        cartProductListAdapter = new CartProductListAdapter(getActivity(), cartProductList, cartByUser, BasketFragmentWithItems.this, "BASKET_WITH_ITEMS_FRA_TAG");
        binding.recyclerItemsList.setAdapter(cartProductListAdapter);
    }


    private void callApiGetPromoProducts() {
        productRepository.callApiGetPromoProducts(PrefConfig.loadMagIdFromPref(getContext()));
    }

    public void clearBasket() {
        binding.clearCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Wyczyść koszyk")
                        .setMessage("Czy napewno chcesz usunąć wszytskie pozycje z koszyka?")
                        .setCancelable(false)
                        .setPositiveButton("Wyczyść koszyk", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cartRepository.clearCart(PrefConfig.loadUserIdFromPref(getContext()));
                                HomeFragment homeFragment = new HomeFragment();
                                getParentFragmentManager().beginTransaction()
                                        .replace(R.id.container, homeFragment)
                                        .commit();
                            }
                        })
                        .setNegativeButton("Nie", null)
                        .show();
            }
        });
    }

    private void setPromoRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.linearForPromoRecyclerBasket.getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.promoRecyclerView.setLayoutManager(linearLayoutManager);
        binding.promoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(getActivity(), promoProductsArrayList, cartByUser, BasketFragmentWithItems.this, "BASKET_WITH_ITEMS_FRA_TAG");
        binding.promoRecyclerView.setAdapter(productAdapter);
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
        PrefConfig.saveItemTotalInPref(getContext(), String.valueOf(cartByUser.getItemTotal()));
        PrefConfig.saveBasketTotalInPref(getContext(), String.valueOf(cartByUser.getTotal()));
        PrefConfig.saveCartItemInPref(getContext(), String.valueOf(cartModel.getItems().size()));
        binding.numberOfProductInBasket.setText("Liczba produktów: " + cartByUser.getItems().size());
        binding.bagSumValue.setText(String.valueOf(cartModel.getBagCost()) + " zł");
        PrefConfig.saveActiveOrderInPref(getContext(), String.valueOf(cartByUser.isActiveOrder()));
        if (cartModel.isEmptyBasket()) {
            PrefConfig.saveEmptyBasketInPref(getContext(), "true");
            checkItemAmount();
        } else {
            PrefConfig.saveEmptyBasketInPref(getContext(), "false");
        }
        setCounter();
        setOrderSum();
    }

    private void checkItemAmount() {
        if (PrefConfig.loadItemTotalFromPref(getContext()).equals("0.0")) {
            HomeFragment homeFragment = new HomeFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.container, homeFragment)
                    .commit();
        }
    }

    public void addToCart(String stockItemId) {
        cartRepository.addToCart(stockItemId, PrefConfig.loadUserIdFromPref(getContext()));
    }

    public void removeFromCart(String stockItemId) {
        cartRepository.removeFromCart(stockItemId, PrefConfig.loadUserIdFromPref(getContext()));
    }

    private void setOrderSum() {
        String loadItemTotalFromPref = PrefConfig.loadItemTotalFromPref(getContext());
        binding.orderSumValue.setText(loadItemTotalFromPref + " zł");
    }

    private void setCounter() {
        String counter = PrefConfig.loadBasketTotalFromPref(getContext());
        binding.btnAdvancedSum.setText(counter + " zł");
        binding.paySumValue.setText(counter + " zł");
    }

    public void goToCheckout() {
        binding.btnGoToCheckoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }
}