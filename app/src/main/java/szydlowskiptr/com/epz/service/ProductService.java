package szydlowskiptr.com.epz.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import szydlowskiptr.com.epz.model.Product;

/**
 * Created by Piotr Szydlowski on 29.03.2023
 */
public interface ProductService {

    @GET("promoProduct/{magId}")
    Call<List<Product>> getPromoProducts(@Path("magId") String id);

    @GET("hitProduct/{magId}")
    Call<List<Product>> getHitProducts(@Path("magId") String id);

    @GET("newProduct/{magId}")
    Call<List<Product>> getNewProducts(@Path("magId")String mag_id);
}
