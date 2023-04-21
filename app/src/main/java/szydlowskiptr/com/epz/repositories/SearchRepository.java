package szydlowskiptr.com.epz.repositories;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.service.ProductService;
import szydlowskiptr.com.epz.sliderSearch.SearchFragment;

public class SearchRepository {

    Fragment fragment;
    String tag;
    private List<Product> searchedProductArrayList = new ArrayList<>();

    public SearchRepository(Fragment fragment, String tag) {
        this.fragment = fragment;
        this.tag = tag;
    }

    public void callApiSearch(String magId, String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/stocks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.getProductsBySearch(magId, query);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    searchedProductArrayList = response.body();
                    if ("SEARCH_FR".equals(tag)) {
                        ((SearchFragment) fragment).notifyOnResponseGetSearchFinished();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }

    public List<Product> searchedProduct(){
        return searchedProductArrayList;
    }

}
