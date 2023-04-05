package szydlowskiptr.com.epz.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import szydlowskiptr.com.epz.model.ProductModel;

/**
 * Created by Piotr Szydlowski on 29.03.2023
 */
public interface ProductService {

    @GET("promoProduct/{magId}")
    Call<List<ProductModel>> getPromoProducts(@Path("magId") String id);

    @GET("hitProduct/{magId}")
    Call<List<ProductModel>> getHitProducts(@Path("magId") String id);

    @GET("newProduct/{magId}")
    Call<List<ProductModel>> getNewProducts(@Path("magId")String mag_id);

    @GET("productByCat/{catId}/magId/{magId}")
    Call<List<ProductModel>> getProductsByCatId(@Path("catId")String catId, @Path("magId")String magId);

    @GET("productById/{prodId}/magId/{magId}")
    Call <ProductModel> getProductById(@Path("prodId")String prodId, @Path("magId")String magId);

    @GET("search/{magId}/{value}")
    Call <List<ProductModel>> getProductsBySearch(@Path("magId")String magId, @Path("value")String value);
}
