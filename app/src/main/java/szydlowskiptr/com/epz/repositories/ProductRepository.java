package szydlowskiptr.com.epz.repositories;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.activity.BasketFragment;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.service.ProductService;

public class ProductRepository {

    Fragment fragment;
    String tag;
    List<Product> productHit = new ArrayList<>();

    public ProductRepository(Fragment fragment, String tag) {
        this.fragment = fragment;
        this.tag = tag;
    }

    public void callApiGetHitProducts(String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/stocks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.getNewProducts(userId);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productHit = response.body();
                switch (tag) {
                    case "BASKET_FR":
                        ((BasketFragment) fragment).notifyOnResponseGetHitProductsFinished();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }

    public List<Product> getGetHitProducts() {
        return productHit;
    }
}
