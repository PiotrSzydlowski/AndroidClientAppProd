package szydlowskiptr.com.epz.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import szydlowskiptr.com.epz.model.AddressModel;
import szydlowskiptr.com.epz.model.CartModel;

public interface CartService {

    @GET("basket/{userId}")
    Call<CartModel> getCart(@Path("userId") String id);
}
