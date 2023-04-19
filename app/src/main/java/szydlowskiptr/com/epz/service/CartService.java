package szydlowskiptr.com.epz.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.ResponseModel;

public interface CartService {

    @GET("basket/{userId}")
    Call<CartModel> getCart(@Path("userId") String id);

    @POST("addItem/{stockItemId}/{qty}/{userId}")
    Call<ResponseModel> addItemToCart(@Path("stockItemId") String stockItemId,
                                      @Path("qty") String qty,
                                      @Path("userId") String userId);
}
