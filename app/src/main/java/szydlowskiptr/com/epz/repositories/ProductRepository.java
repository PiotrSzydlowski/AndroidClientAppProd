package szydlowskiptr.com.epz.repositories;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.activity.basket.BasketFragment;
import szydlowskiptr.com.epz.activity.basket.BasketFragmentWithItems;
import szydlowskiptr.com.epz.home.HomeFragment;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.product.DetailsProductActivity;
import szydlowskiptr.com.epz.product.ProductPerCategoryFragment;
import szydlowskiptr.com.epz.service.ProductService;

public class ProductRepository {

    Fragment fragment;
    Activity activity;
    String tag;
    List<Product> productHit = new ArrayList<>();
    List<Product> promoProductsArrayList = new ArrayList<>();
    List<Product> allProducts = new ArrayList<>();
    Product product;

    public ProductRepository(Fragment fragment, String tag) {
        this.fragment = fragment;
        this.tag = tag;
    }

    public ProductRepository(Activity activity, String tag) {
        this.activity = activity;
        this.tag = tag;
    }

    public void callApiGetHitProducts(String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.34:9193/prod/api/stocks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.getHitProducts(userId);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productHit = response.body();
                switch (tag) {
                    case "BASKET_FR":
                        ((BasketFragment) fragment).notifyOnResponseGetHitProductsFinished();
                        break;
                    case "HOME_FR":
                        ((HomeFragment) fragment).notifyOnResponseGetHitProductsFinished();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }

    public void callApiGetPromoProducts(String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.34:9193/prod/api/stocks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.getPromoProducts(userId);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                promoProductsArrayList = response.body();
                switch (tag) {
                    case "HOME_FR":
                        ((HomeFragment) fragment).notifyOnResponseGetPromoProductsFinished();
                        break;
                    case "BASKET_WITH_ITEMS_FRA_TAG":
                        ((BasketFragmentWithItems) fragment).notifyOnResponseGetPromoProductsFinished();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }

    public void callApiGetProductsByCategory(String prodByCatId, String magId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.34:9193/prod/api/stocks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.getProductsByCatId(prodByCatId, magId);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allProducts = response.body();
                    switch (tag) {
                        case "PRODUCT_PER_CAT_FR":
                            ((ProductPerCategoryFragment) fragment).notifyOnResponseGetProductByCatIdFinished();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }

    public void callApiGetProductsById(String productId, String magId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.34:9193/prod/api/stocks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<Product> call = productService.getProductById(productId, magId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();
                switch (tag) {
                    case "DETAIL_PRODUCT_ACT":
                        ((DetailsProductActivity) activity).notifyOnResponseGetProductByIdFinished();
                        break;
                }

        }

        @Override
        public void onFailure (Call < Product > call, Throwable t){
        }
    });
}

    public List<Product> getGetHitProducts() {
        return productHit;
    }

    public List<Product> getGetPromoProducts() {
        return promoProductsArrayList;
    }

    public List<Product> getProductByCatId() {
        return allProducts;
    }

    public Product getProductById() {
        return product;
    }
}
