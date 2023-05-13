package szydlowskiptr.com.epz.service;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import szydlowskiptr.com.epz.model.CreateOrderModel;

/**
 * Created by Piotr Szydlowski on 13.05.2023
 */
public interface CreateOrder {

    @POST("createOrder/{customerId}")
    Call<CreateOrderModel> createOrder(@Path("customerId") String customerId);
}
