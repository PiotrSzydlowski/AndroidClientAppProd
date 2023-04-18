package szydlowskiptr.com.epz.activity;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.service.CartService;
import szydlowskiptr.com.epz.service.ProductService;

public class BasketFragment extends Fragment {

    Button startShoppingBtn;
    ArrayList<Product> allProducts = new ArrayList<>();
    View promoView;
    RecyclerView promoRecyclerView;
    ProductAdapter productAdapter;
    SharedPreferences sp;
    CartModel cartByUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        allProducts.removeAll(allProducts);
        sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        setView(view);
        clickStartShoppingBtn();
        callApiGetHitProducts();
        callApiToGetCart();
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
        productAdapter = new ProductAdapter(getActivity(), allProducts, cartByUser);
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/basket/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CartService cartService = retrofit.create(CartService.class);
        Call<CartModel> call = cartService.getCart("15");
        call.enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CartModel body = response.body();
                    cartByUser = body;
                }
            }
            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
            }
        });
    }

    private void callApiGetHitProducts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/stocks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.getNewProducts(sp.getString("mag_id", null));
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> body = response.body();
                    allProducts.addAll(body);
                    parseArrayNewProducts();
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }

    private void parseArrayNewProducts() {
        try {
            productAdapter = new ProductAdapter(getActivity(), allProducts, cartByUser);
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        setNewRecycler();
    }
}