package szydlowskiptr.com.epz.product;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rollbar.android.Rollbar;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.activity.category.CategoryFragment;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.repositories.CartRepository;
import szydlowskiptr.com.epz.repositories.ProductRepository;

public class ProductPerCategoryFragment extends Fragment {

    ArrayList<Product> allProducts = new ArrayList<>();
    RecyclerView productsRecyclerView;
    View productView;
    ProductAdapter productAdapter;
    ImageView backArrowProductByCat;
    CartModel cartByUser;
    CartRepository cartRepository = new CartRepository(ProductPerCategoryFragment.this, "PRODUCT_PER_CAT_FR");
    ProductRepository productRepository = new ProductRepository(ProductPerCategoryFragment.this, "PRODUCT_PER_CAT_FR");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_per_category, container, false);

        allProducts.removeAll(allProducts);
        PrefConfig.registerPref(getContext());
        callApiToGetCart();
        callApiGetProductsByCategory();
        setView(view);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clickOnBackArrowBtn();
        Rollbar.init(getContext());
        return view;
    }

    private void setView(View view) {
        productsRecyclerView = view.findViewById(R.id.recyclerViewProducts);
        productView = view.findViewById(R.id.linear_for_products_recycler);
        backArrowProductByCat = view.findViewById(R.id.backArrowProductByCat);
    }

    private void setProductRecycler() {
        productAdapter = new ProductAdapter(getActivity(), allProducts, cartByUser, ProductPerCategoryFragment.this, "PRODUCT_PER_CAT_FR");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        productsRecyclerView.setLayoutManager(gridLayoutManager);
        productsRecyclerView.setAdapter(productAdapter);
    }

    public void clickOnBackArrowBtn() {
        backArrowProductByCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment cat = new CategoryFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, cat);
                ft.commit();
            }
        });
    }

    private void callApiToGetCart() {
        cartRepository.callApiToGetCart(PrefConfig.loadUserIdFromPref(getContext()));
    }

    private void callApiGetProductsByCategory() {
        productRepository.callApiGetProductsByCategory(PrefConfig.loadProdByCatIdFromPref(getContext()), PrefConfig.loadMagIdFromPref(getContext()));
    }

    private void parseArrayProducts() {
        try {
            productAdapter = new ProductAdapter(getActivity(), allProducts, cartByUser, ProductPerCategoryFragment.this, "PRODUCT_PER_CAT_FR");
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        setProductRecycler();
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
        CartModel body = cartRepository.getCartModel();
        cartByUser = body;
        PrefConfig.saveCartItemInPref(getContext(), String.valueOf(body.getItems().size()));
        setProductRecycler();
    }

    public void notifyOnResponseGetProductByCatIdFinished() {
        List<Product> body = productRepository.getProductByCatId();
        allProducts.addAll(body);
        parseArrayProducts();
    }
}