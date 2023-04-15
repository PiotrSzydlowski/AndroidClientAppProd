package szydlowskiptr.com.epz.activity;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rollbar.android.Rollbar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.service.ProductService;

public class ProductPerCategoryFragment extends Fragment {

    ArrayList<Product> allProducts = new ArrayList<>();
    RecyclerView productsRecyclerView;
    View productView;
    ProductAdapter productAdapter;
    ImageView backArrowProductByCat;
    SharedPreferences sp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_per_category, container, false);

        allProducts.removeAll(allProducts);
        sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        setView(view);
        callApiGetProductsByCategory();
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
        productAdapter = new ProductAdapter(getActivity(), allProducts);
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

    private void callApiGetProductsByCategory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/stocks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.getProductsByCatId(sp.getString("product_by_cat_id", null), sp.getString("mag_id", null));
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> body = response.body();
                    allProducts.addAll(body);
                    parseArrayProducts();
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }

    private void parseArrayProducts() {
        try {
            productAdapter = new ProductAdapter(getActivity(), allProducts);
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        setProductRecycler();
    }
}