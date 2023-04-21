package szydlowskiptr.com.epz.repositories;

import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.activity.BasketFragment;
import szydlowskiptr.com.epz.home.HomeFragment;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.ResponseModel;
import szydlowskiptr.com.epz.service.CartService;

public class CartRepository {

    CartModel cartByUser;
    Fragment fragment;
    String tag;

    public CartRepository(Fragment fragment, String tag) {
        this.fragment = fragment;
        this.tag = tag;
    }

    public void callApiToGetCart(String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/basket/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CartService cartService = retrofit.create(CartService.class);
        Call<CartModel> call = cartService.getCart(userId);
        call.enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CartModel body = response.body();
                    cartByUser = body;
                    switch (tag) {
                        case "BASKET_FR":
                            ((BasketFragment) fragment).notifyOnResponseGetCartFinished();
                            break;
                        case "HOME_FR":
                            ((HomeFragment) fragment).notifyOnResponseGetCartFinished();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
            }
        });
    }

    public void addToCart(String stockItemId, String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/basket/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CartService cartService = retrofit.create(CartService.class);
        Call<ResponseModel> call = cartService.addItemToCart(stockItemId, "1", userId);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
            }
        });
    }

    public void removeFromCart(String stockItemId, String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/basket/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CartService cartService = retrofit.create(CartService.class);
        Call<ResponseModel> call = cartService.removeItemFromCart(stockItemId, "1", userId);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
            }
        });
    }

    public CartModel getCartModel() {
        return cartByUser;
    }
}
