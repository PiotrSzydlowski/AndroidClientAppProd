package szydlowskiptr.com.epz.activity;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.ProductModel;
import szydlowskiptr.com.epz.service.ProductService;

public class ProductPerCategoryFragment extends Fragment {

    ArrayList<ProductModel> allProductModels = new ArrayList<>();
    RecyclerView productsRecyclerView;
    View productView;
    ProductAdapter productAdapter;
    ImageView backArrowProductByCat;
    SharedPreferences sp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_per_category, container, false);

        allProductModels.removeAll(allProductModels);
        sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        setView(view);
        callApiGetProductsByCategory();
        clickOnBackArrowBtn();
        return view;
    }

    private void setView(View view) {
        productsRecyclerView = view.findViewById(R.id.recyclerViewProducts);
        productView = view.findViewById(R.id.linear_for_products_recycler);
        backArrowProductByCat = view.findViewById(R.id.backArrowProductByCat);
    }

    private void setProductRecycler() {
        productAdapter = new ProductAdapter(getActivity(), allProductModels);
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
        Call<List<ProductModel>> call = productService.getProductsByCatId(sp.getString("product_by_cat_id", null), sp.getString("mag_id", null));
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductModel> body = response.body();
                    allProductModels.addAll(body);
                    parseArrayProducts();
                }
            }
            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
            }
        });
    }

    private void parseArrayProducts() {
        try {
            productAdapter = new ProductAdapter(getActivity(), allProductModels);
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        setProductRecycler();
    }
}